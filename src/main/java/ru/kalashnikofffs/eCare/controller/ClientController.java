package ru.kalashnikofffs.eCare.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kalashnikofffs.eCare.ECareException;
import ru.kalashnikofffs.eCare.model.Client;
import ru.kalashnikofffs.eCare.service.ClientService;
import ru.kalashnikofffs.eCare.service.SecurityService;
import ru.kalashnikofffs.eCare.utility.PageName;
import ru.kalashnikofffs.eCare.validator.ClientValidator;
import ru.kalashnikofffs.eCare.validator.RegistrationValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"client", "role"})
public class ClientController {

    private static final Logger logger = Logger.getLogger(ClientController.class);

    @Autowired
    private JmsTemplate jmsTemplate;
    private final ClientService clientService;
    private final SecurityService securityService;
    private final RegistrationValidator registrationValidator;
    private final ClientValidator clientValidator;


    @Autowired
    public ClientController(ClientService clientService,
                            SecurityService securityService,
                            RegistrationValidator registrationValidator,
                            ClientValidator clientValidator) {

        this.clientService = clientService;
        this.securityService = securityService;
        this.registrationValidator = registrationValidator;
        this.clientValidator = clientValidator;
    }


    @GetMapping(value = "/registration")
    public String registration(Model model) {
        model.addAttribute("isLoginOrRegistrationPage", true);
        model.addAttribute("newClient", new Client());
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String registration(@ModelAttribute("newClient") Client client, Model model, HttpSession httpSession, BindingResult bindingResult) {

        if (!clientService.validate(client, bindingResult)) {
            model.addAttribute("isLoginOrRegistrationPage", true);
            return "registration";
        }
        clientService.add(client);
        securityService.autoLogin(client.getEmail(), client.getConfirmPassword());
        String role = securityService.getRoleFromAuthorities();

        httpSession.setAttribute("role", role);
        httpSession.setAttribute("client", client);

        return "redirect:/welcome";
    }

    @GetMapping(value = "/login")//это нормально?
    public String login(Model model, String error, String logout) {

        if (error != null) {
            model.addAttribute("errormessage", "Name or password is incorrect.");
        }

        if (logout != null) {
            model.addAttribute("successmessage", "Logged out successfully.");
        }

        model.addAttribute("isLoginOrRegistrationPage", true);
        return "login";
    }

    //    @GetMapping(value = {"/", "/welcome"})
//    public String welcome(Model model, HttpSession httpSession) {
//        String role = securityService.getRoleFromAuthorities();
//        httpSession.setAttribute("role", role);
//        try {
//            assert role != null;
//            if (role.equals(Role.ROLE_USER.toString())) {
//                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//                Client client = clientService.findClientByEmail(userDetails.getUsername());
//                httpSession.setAttribute("client", client);
//                model.addAttribute("isLoginPage", false);
//                logger.info("User(client): " + client.getEmail() + " login in application.");
//                return "welcome";
//            } else if (role.equals(Role.ROLE_ADMIN.toString())) {
//                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//                Client operator = clientService.findClientByEmail(userDetails.getUsername());
//                httpSession.setAttribute("operator", operator);
//                logger.info("User(operator): " + operator.getEmail() + " login in application.");
//                return "welcome";
//            } else {
//                return "login";
//            }
//        } catch (ECareException ecx) {
//            return "login";
//        }
//    }
    @GetMapping(value = {"/", "/welcome"})
    public String welcome(Model model, HttpSession httpSession) {
        String role = securityService.getRoleFromAuthorities();
        httpSession.setAttribute("role", role);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client client = clientService.findClientByEmail(userDetails.getUsername());
        httpSession.setAttribute("client" , client);


        return "welcome";
    }

    @GetMapping(value = "index")
    public String index(){
        return "index";
    }

    @PostMapping(value = "/client/profile")
    public String profile(Model model, Client client) {

        logger.info("User" + client.getEmail() + "went to profile page.");

        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.PROFILE.toString());

        return "client/profile";
    }

    @PostMapping(value = "/client/editProfile")
    public String editProfile(Model model, @ModelAttribute Client client) {
        logger.info("User went to edit profile page.");

        List<String> breadcrumb = new ArrayList<>(); //??
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.PROFILE.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.EDIT_PROFILE.toString());

        model.addAttribute("editClient", client);
        return "client/editProfile";
    }

    @PostMapping(value = "/client/updateProfile")
    public String updateProfile(@ModelAttribute Client client, @ModelAttribute("editClient") Client editClient,
                                Model model, HttpServletRequest request, BindingResult bindingResult) {

        List<String> breadcrumb = new ArrayList<>();

        try {
            clientValidator.validate(editClient, bindingResult);

            if (bindingResult.hasErrors()) {
                return "client/editProfile";
            }

            client.setName(request.getParameter("name"));
            client.setSurname(request.getParameter("surname"));
            client.setPassport(Long.valueOf(request.getParameter("passport")));
            client.setAddress(request.getParameter("address"));
            client.setBirthDate(Date.valueOf(request.getParameter("birthDate")));
            clientService.edit(client);
            logger.info("User " + client + " update profile.");

            breadcrumb.add(PageName.HOME.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.PROFILE.toString());

            return "client/profile";
        } catch (Exception ecx) {

            breadcrumb.add(PageName.HOME.toString());
            breadcrumb.add(PageName.PROFILE.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.EDIT_PROFILE.toString());

            model.addAttribute("editClient", editClient);
            model.addAttribute("errormessage", ecx.getMessage());
            return "client/editProfile";
        }
    }

    @RequestMapping(value = "/client/addAmountToBalance", method = RequestMethod.POST)
    public String addAmountToBalance(@ModelAttribute Client client, HttpServletRequest request, Model model) {
        try {
            int amount = Integer.parseInt(request.getParameter("amount"));
            client.addAmountToBalance(amount);
            clientService.edit(client);

            List<String> breadcrumb = new ArrayList<>();
            breadcrumb.add(PageName.HOME.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.PROFILE.toString());

            model.addAttribute("successmessage", "Amount " + amount + " added to balance of client " + client.getFullName() + ".");
            logger.info("User added amount to balance of client " + client + ".");
            return "client/profile";
        } catch (ECareException ecx) {

            List<String> breadcrumb = new ArrayList<>();
            breadcrumb.add(PageName.HOME.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.PROFILE.toString());

            model.addAttribute("errormessage", ecx.getMessage());
            return "client/profile";
        }
    }
}

//TODO Single Responsebility +  open close  Ж контроллеры не должны знать о логике, слоях дао и секьюрити Ж
