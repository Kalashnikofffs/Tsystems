package ru.kalashnikofffs.eCare.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kalashnikofffs.eCare.model.Option;
import ru.kalashnikofffs.eCare.model.Tariff;
import ru.kalashnikofffs.eCare.service.OptionService;
import ru.kalashnikofffs.eCare.service.TariffService;
import ru.kalashnikofffs.eCare.utility.PageName;
import ru.kalashnikofffs.eCare.ECareException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TariffController {

    private static final Logger logger = Logger.getLogger(TariffController.class);

    private final TariffService tariffService;
    private final OptionService optionService;


    public TariffController(TariffService tariffService,
                            OptionService optionService) {

        this.tariffService = tariffService;
        this.optionService = optionService;
    }

    @PostMapping(value = "/operator/viewAllTariffs")
    public String viewAllTariffs(Model model, HttpServletRequest req) {
        List<Tariff> tariffsList = tariffService.getAllTariffs();
        model.addAttribute("tariffsList", tariffsList);
        logger.info("User (operator) went to view all tariffs page.");

        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.TARIFFS.toString());

        return "operator/tariffs";
    }

    @PostMapping(value = "/operator/viewTariff")
    public String viewTariff(Model model, HttpServletRequest req) {
        Long tariffId = Long.valueOf(req.getParameter("tariffId"));
        Tariff tariff = tariffService.getTariffById(tariffId);
        model.addAttribute("tariff", tariff);
        List<Option> optionsList = optionService.getAllOptionsForTariff(tariffId);
        model.addAttribute("optionsList", optionsList);
        logger.info("User (operator) went to view tariff page.");

        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.TARIFFS.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.VIEW_TARIFF.toString());

        return "operator/viewTariff";
    }

    @PostMapping(value = "/operator/deleteTariff")
    public String deleteTariff(Model model, HttpServletRequest req) {
        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.TARIFFS.toString());

        Long tariffId = Long.valueOf(req.getParameter("tariffId"));
        List<Tariff> tariffs = null;
        try {
            tariffService.deleteTariff(tariffId);
            logger.info("Tariff with id: " + tariffId + " deleted from database.");
            tariffs = tariffService.getAllTariffs();
            model.addAttribute("tariffsList", tariffs);
            model.addAttribute("successmessage", "Tariff with id: " + tariffId + " deleted from database.");
            logger.info("User went to all tariffs page.");
            return "operator/tariffs";
        } catch (ECareException ecx) {
            tariffs = tariffService.getAllTariffs();
            model.addAttribute("tariffs", tariffs);
            model.addAttribute("errormessage", ecx.getMessage());
            return "operator/tariffsList";
        }
    }

    @PostMapping(value = "operator/newTariff")
    public String newTariff(Model model, HttpServletRequest req) {
        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.TARIFFS.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.NEW_TARIFF.toString());

        model.addAttribute("newTariff", new Tariff());
        logger.info("User (operator) went to create new tariff page.");
        return "operator/createTariff";
    }

    @PostMapping(value = "/operator/createTariff")
    public String createTariff(@ModelAttribute("newTariff") Tariff tariff, Model model, HttpServletRequest req) {
        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());

        try {
            tariffService.add(tariff);
            model.addAttribute("successmessage", "Tariff " + tariff.getTitle() + " created and saved in database.");
            logger.info("New tariff " + tariff + " created.");
            List<Tariff> tariffsList = tariffService.getAllTariffs();
            model.addAttribute("tariffsList", tariffsList);

            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.TARIFFS.toString());

            return "operator/tariffs";
        } catch (ECareException ecx) {
            breadcrumb.add(PageName.TARIFFS.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.NEW_TARIFF.toString());
            model.addAttribute("errormessage", ecx.getMessage());
            return "operator/createTariff";
        }
    }

    @GetMapping(value ="/rest/tariffs" , produces = "application/json")
    @ResponseBody
    public String getTariffsOnRest () throws JsonProcessingException {
        ObjectMapper  mapper = new ObjectMapper();

    return mapper.writeValueAsString(tariffService.getAllTariffs());
    }

}
