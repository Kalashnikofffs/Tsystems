package ru.kalashnikofffs.eCare.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kalashnikofffs.eCare.model.Client;
import ru.kalashnikofffs.eCare.model.Contract;
import ru.kalashnikofffs.eCare.service.ClientService;
import ru.kalashnikofffs.eCare.service.ContractService;
import ru.kalashnikofffs.eCare.utility.PageName;
import ru.kalashnikofffs.eCare.validator.ClientValidator;
import ru.kalashnikofffs.eCare.ECareException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"operator", "role"})
public class OperatorController {
    private static final Logger logger = Logger.getLogger(OperatorController.class);

    private final ClientService clientService;
    private final ContractService contractService;
    private final ClientValidator clientValidator;

    public OperatorController(ClientService clientService,
                              ContractService contractService,
                              ClientValidator clientValidator) {

        this.clientService = clientService;
        this.contractService = contractService;
        this.clientValidator = clientValidator;
    }

    @PostMapping(value = "/operator/viewAllClients")
    public String viewAllClients(Model model, HttpServletRequest req) {
        List<Client> clientsList = clientService.getAllClients();
        model.addAttribute("clientsList", clientsList);

        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.CLIENTS.toString());

        logger.info("Operator went to view dashboard page.");
        return "operator/clients";
    }

    @PostMapping(value = "/operator/viewClient")
    public String viewClient(Model model, HttpServletRequest request) {
        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.CLIENTS.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.VIEW_CLIENT.toString());

        try {
            Long clientId = Long.parseLong(request.getParameter("clientId"));
            Client client = clientService.getClientById(clientId);
            logger.info("Operator went to profile page of client: " + client + ".");
            model.addAttribute("client", client);

            List<Contract> contractsList = contractService.getAllContractsForClient(clientId);
            model.addAttribute("contractsList", contractsList);
            return "operator/viewClient";
        } catch (ECareException ecx) {
            model.addAttribute("errormessage", ecx.getMessage());
            return "operator/viewClient";
        }
    }

    @PostMapping(value = "/operator/searchClientByNumber")
    public String searchClientByNumber(Model model, HttpServletRequest request) {
        List<Client> clientsList = null;
        try {
            Long number = Long.parseLong(request.getParameter("number"));
            logger.info("Operator searching of client by number " + number + ".");
            Client client = clientService.findClientByNumber(number);
            model.addAttribute("client", client);

            List<Contract> contractsList = contractService.getAllContractsForClient(client.getId());
            model.addAttribute("contractsList", contractsList);
            model.addAttribute("successmessage", "Client " + client.getName() + " found and loaded from database.");

            List<String> breadcrumb = new ArrayList<>();
            breadcrumb.add(PageName.HOME.toString());
            breadcrumb.add(PageName.CLIENTS.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.VIEW_CLIENT.toString());

            logger.info("Operator went to client page.");
            return "operator/viewClient";
        } catch (ECareException ecx) {
            List<String> breadcrumb = new ArrayList<>();
            breadcrumb.add(PageName.HOME.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.CLIENTS.toString());

            clientsList = clientService.getAllClients();
            model.addAttribute("clientsList", clientsList);
            model.addAttribute("errormessage", ecx.getMessage());
            return "operator/clients";
        }
    }

    @PostMapping(value = "/operator/deleteClient")
    public String deleteClient(Model model, HttpServletRequest req) {
        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.CLIENTS.toString());

        Long clientId = Long.valueOf(req.getParameter("clientId"));
        List<Client> clientsList = null;
        try {
            clientService.delete(clientId);
            logger.info("Operator delete are client with id:" + clientId + " from database.");
            clientsList = clientService.getAllClients();
            model.addAttribute("successmessage", "Client with id: " + clientId + " deleted from database.");
            model.addAttribute("clientsList", clientsList);
            logger.info("Operator went to view all clients page.");
            return "operator/clients";
        } catch (ECareException ecx) {
            clientsList = clientService.getAllClients();
            model.addAttribute("clientsList", clientsList);
            model.addAttribute("errormessage", ecx.getMessage());
            return "operator/clients";
        }
    }

    @PostMapping(value = "/operator/addAmountToBalance")
    public String addAmountToBalance(HttpServletRequest request, Model model) {
        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.CLIENTS.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.VIEW_CLIENT.toString());

        Long clientId = Long.valueOf(request.getParameter("clientId"));
        try {
            Client client = clientService.getClientById(clientId);
            int amount = Integer.parseInt(request.getParameter("amount"));
            client.addAmountToBalance(amount);
            clientService.edit(client);
            model.addAttribute("client", client);

            List<Contract> contractsList = contractService.getAllContractsForClient(clientId);
            model.addAttribute("contractsList", contractsList);

            model.addAttribute("successmessage", "Amount " + amount + " added to balance of client " + client.getFullName() + ".");
            logger.info("User added amount to balance of client " + client + ".");
            return "operator/viewClient";
        } catch (ECareException ecx) {
            Client client = clientService.getClientById(clientId);
            model.addAttribute("client", client);
            List<Contract> contractsList = contractService.getAllContractsForClient(clientId);
            model.addAttribute("contractsList", contractsList);

            model.addAttribute("errormessage", ecx.getMessage());
            return "operator/viewClient";
        }
    }

    @PostMapping(value = "/operator/editProfile")
    public String editProfile(Model model, HttpServletRequest request) {
        logger.info("Operator went to edit profile page.");
        Long clientId = Long.valueOf(request.getParameter("clientId"));
        Client client = clientService.getClientById(clientId);

        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.CLIENTS.toString());
        breadcrumb.add(PageName.VIEW_CLIENT.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.EDIT_PROFILE.toString());

        model.addAttribute("editClient", client);
        return "operator/editProfile";
    }


    @PostMapping(value = "/operator/updateProfile")
    public String updateProfile(@ModelAttribute("editClient") Client client, Model model, HttpServletRequest request, BindingResult bindingResult) {

        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.CLIENTS.toString());


        try {
            clientValidator.validate(client, bindingResult);

            if (bindingResult.hasErrors()) {
                return "operator/editProfile";
            }

            clientService.edit(client);
            logger.info("User " + client + " update profile.");

            model.addAttribute("client", client);
            List<Contract> contractsList = contractService.getAllContractsForClient(client.getId());
            model.addAttribute("contractsList", contractsList);

            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.VIEW_CLIENT.toString());

            model.addAttribute("successmessage", "Personal info of client " + client.getFullName() + " updated.");
            return "operator/viewClient";
        } catch (ECareException ecx) {
            breadcrumb.add(PageName.VIEW_CLIENT.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.EDIT_PROFILE.toString());

            model.addAttribute("editClient", client);
            model.addAttribute("errormessage", ecx.getMessage());
            return "operator/editProfile";
        }
    }
}
