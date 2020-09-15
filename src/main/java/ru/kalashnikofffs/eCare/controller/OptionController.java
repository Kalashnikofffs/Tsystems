package ru.kalashnikofffs.eCare.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
public class OptionController {

    private static final Logger logger = Logger.getLogger(OptionController.class);

    private final OptionService optionService;
    private final TariffService tariffService;


    public OptionController(OptionService optionService,
                            TariffService tariffService) {

        this.optionService = optionService;
        this.tariffService = tariffService;
    }

    @PostMapping(value = "/operator/newOption")
    public String newOption(Model model, HttpServletRequest req) {
        Long tariffId = Long.valueOf(req.getParameter("tariffId"));
        Tariff tariff = tariffService.getTariffById(tariffId);
        model.addAttribute("tariff", tariff);
        model.addAttribute("newOption", new Option());
        logger.info("User (operator) went to create new option page.");

        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.TARIFFS.toString());
        breadcrumb.add(PageName.VIEW_TARIFF.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.NEW_OPTION.toString());

        return "operator/createOption";
    }

    @PostMapping(value = "/operator/createOption")
    public String createOption(@ModelAttribute("newOption") Option option, Model model, HttpServletRequest req) {
        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.TARIFFS.toString());
        breadcrumb.add(PageName.VIEW_TARIFF.toString());

        Long tariffId = Long.valueOf(req.getParameter("tariffId"));
        Tariff tariff = null;
        try {
            tariff = tariffService.getTariffById(tariffId);
            option.setTariff(tariff);
            optionService.add(option);
            tariff.addOption(option);
            tariffService.edit(tariff);
            model.addAttribute("option", option);
            model.addAttribute("tariff", tariff);
            logger.info("New option " + option + " has created.");
            model.addAttribute("successmessage", "Option " + option.getTitle() + " created and saved in database.");

            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.VIEW_OPTION.toString());

            return "operator/viewOption";
        } catch (ECareException ecx) {
            tariff = tariffService.getTariffById(Long.valueOf(req.getParameter("tariffId")));
            model.addAttribute("tariff", tariff);
            model.addAttribute("errormessage", ecx.getMessage());

            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.NEW_OPTION.toString());

            return "operator/createOption";
        }
    }

    @PostMapping(value = "/operator/viewOption")
    public String viewOption(Model model, HttpServletRequest req) {
        Long optionId = Long.valueOf(req.getParameter("optionId"));
        Option option = optionService.getOptionById(optionId);
        model.addAttribute("option", option);
        Long tariffId = Long.valueOf(req.getParameter("tariffId"));
        Tariff tariff = tariffService.getTariffById(tariffId);
        model.addAttribute("tariff", tariff);
        logger.info("User (operator) went to view option page.");

        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.TARIFFS.toString());
        breadcrumb.add(PageName.VIEW_TARIFF.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.VIEW_OPTION.toString());

        return "operator/viewOption";
    }

    @PostMapping(value = "/operator/editOption")
    public String editOption(Model model, HttpServletRequest req) {
        Long optionId = Long.valueOf(req.getParameter("optionId"));
        Option option = optionService.getOptionById(optionId);
        model.addAttribute("option", option);
        Long tariffId = Long.valueOf(req.getParameter("tariffId"));
        Tariff tariff = tariffService.getTariffById(tariffId);
        model.addAttribute("tariff", tariff);
        List<Option> optionsList = optionService.getAllOptionsForTariff(tariffId);
        model.addAttribute("optionsList", optionsList);
        logger.info("User went to edit option page.");

        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.TARIFFS.toString());
        breadcrumb.add(PageName.VIEW_TARIFF.toString());
        breadcrumb.add(PageName.VIEW_OPTION.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.EDIT_OPTION.toString());

        return "operator/editOption";
    }

    @PostMapping(value = "/operator/updateOption")
    public String updateOption(Model model, HttpServletRequest req) {
        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.TARIFFS.toString());
        breadcrumb.add(PageName.VIEW_TARIFF.toString());

        String[] dependentOptionsArray = req.getParameterValues("dependentOptions");
        String[] incompatibleOptionsArray = req.getParameterValues("incompatibleOptions");
        Long tariffId = Long.valueOf(req.getParameter("tariffId"));
        Long optionId = Long.valueOf(req.getParameter("optionId"));
        Option option = null;
        Tariff tariff = null;
        try {
            option = optionService.getOptionById(optionId);
            option = optionService.createDependencies(option, dependentOptionsArray, incompatibleOptionsArray);
            tariff = tariffService.getTariffById(tariffId);
            model.addAttribute("tariff", tariff);
            model.addAttribute("option", option);
            model.addAttribute("successmessage", "Settings for option " + option.getTitle() + " updated.");
            logger.info("Option " + option + " has been updated.");

            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.VIEW_OPTION.toString());

            return "operator/viewOption";
        } catch (ECareException ecx) {
            option = optionService.getOptionById(optionId);
            model.addAttribute("option", option);
            tariff = tariffService.getTariffById(tariffId);
            model.addAttribute("tariff", tariff);
            model.addAttribute("errormessage", ecx.getMessage());

            breadcrumb.add(PageName.VIEW_OPTION.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.EDIT_OPTION.toString());

            return "operator/editOption";
        }
    }

    @PostMapping(value = "operator/deleteOption")
    public String deleteOption(Model model, HttpServletRequest req) {
        Tariff tariff = null;
        try {
            tariff = tariffService.getTariffById(Long.valueOf(req.getParameter("tariffId")));
            tariff.getOptions().remove(optionService.getOptionById(Long.valueOf(req.getParameter("optionId"))));
            tariffService.edit(tariff);
            Long optionId = Long.valueOf(req.getParameter("optionId"));
            optionService.delete(optionId);
            logger.info("Option with id: " + optionId + " has been deleted from database.");
            model.addAttribute("tariff", tariff);
            model.addAttribute("successmessage", "Option with id: " + optionId + " has been deleted from database.");
            logger.info("User went to view tariff page.");
            List<Option> optionsList = optionService.getAllOptionsForTariff(tariff.getId());
            model.addAttribute("optionsList", optionsList);

            List<String> breadcrumb = new ArrayList<>();
            breadcrumb.add(PageName.HOME.toString());
            breadcrumb.add(PageName.TARIFFS.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.VIEW_TARIFF.toString());

            return "operator/viewTariff";
        } catch (ECareException ecx) {
            tariff = tariffService.getTariffById(Long.valueOf(req.getParameter("tariffId")));
            req.setAttribute("tariff", tariff);
            req.setAttribute("errormessage", ecx.getMessage());
            return "operator/viewTariff";
        }
    }

    @PostMapping(value = "operator/deleteDependentOption")
    public String removeDependentOption(Model model, HttpServletRequest req) {
        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.TARIFFS.toString());
        breadcrumb.add(PageName.VIEW_TARIFF.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.VIEW_OPTION.toString());

        Option option = null;
        Option dependentOption = null;
        Tariff tariff = null;
        try {
            option = optionService.getOptionById(Long.valueOf(req.getParameter("optionId")));
            dependentOption = optionService.getOptionById(Long.valueOf(req.getParameter("dependentOptionId")));
            option = optionService.deleteDependentOption(option, dependentOption);
            optionService.deleteDependentOption(dependentOption, option);
            model.addAttribute("option", option);
            tariff = tariffService.getTariffById(Long.valueOf(req.getParameter("tariffId")));
            model.addAttribute("tariff", tariff);
            model.addAttribute("successmessage", "Option " + dependentOption.getTitle() + " is no longer dependent to option " + option.getTitle() + ".");
            return "operator/viewOption";
        } catch (ECareException ecx) {
            option = optionService.getOptionById(Long.valueOf(req.getParameter("optionId")));
            model.addAttribute("option", option);
            tariff = tariffService.getTariffById(Long.valueOf(req.getParameter("tariffId")));
            model.addAttribute("tariff", tariff);
            model.addAttribute("errormessage", ecx.getMessage());
            return "operator/viewOption";
        }
    }

    @PostMapping(value = "operator/deleteAllDependentOptions")
    public String removeAllDependentOptions(Model model, HttpServletRequest req) {
        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.TARIFFS.toString());
        breadcrumb.add(PageName.VIEW_TARIFF.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.VIEW_OPTION.toString());

        Option option = null;
        Tariff tariff = null;
        try {
            option = optionService.getOptionById(Long.valueOf(req.getParameter("optionId")));
            optionService.clearDependentOptions(option);
            optionService.edit(option);
            model.addAttribute("option", option);
            tariff = tariffService.getTariffById(Long.valueOf(req.getParameter("tariffId")));
            model.addAttribute("tariff", tariff);
            model.addAttribute("successmessage", "All dependent options removed from option " + option.getTitle() + ".");
            return "operator/viewOption";
        } catch (ECareException ecx) {
            option = optionService.getOptionById(Long.valueOf(req.getParameter("optionId")));
            model.addAttribute("option", option);
            tariff = tariffService.getTariffById(Long.valueOf(req.getParameter("tariffId")));
            model.addAttribute("tariff", tariff);
            model.addAttribute("errormessage", ecx.getMessage());
            return "operator/viewOption";
        }
    }

    @PostMapping(value = "operator/deleteIncompatibleOption")
    public String removeIncompatibleOption(Model model, HttpServletRequest req) {
        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.TARIFFS.toString());
        breadcrumb.add(PageName.VIEW_TARIFF.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.VIEW_OPTION.toString());

        Option option = null;
        Option incompatibleOption = null;
        Tariff tariff = null;
        try {
            option = optionService.getOptionById(Long.valueOf(req.getParameter("optionId")));
            incompatibleOption = optionService.getOptionById(Long.valueOf(req.getParameter("incompatibleOptionId")));
            option = optionService.deleteIncompatibleOption(option, incompatibleOption);
            optionService.deleteIncompatibleOption(incompatibleOption, option);
            model.addAttribute("option", option);
            tariff = tariffService.getTariffById(Long.valueOf(req.getParameter("tariffId")));
            model.addAttribute("tariff", tariff);
            model.addAttribute("successmessage", "Option " + incompatibleOption.getTitle() + " is no longer incompatible with option " + option.getTitle() + ".");
            return "operator/viewOption";
        } catch (ECareException ecx) {
            option = optionService.getOptionById(Long.valueOf(req.getParameter("optionId")));
            model.addAttribute("option", option);
            tariff = tariffService.getTariffById(Long.valueOf(req.getParameter("tariffId")));
            model.addAttribute("tariff", tariff);
            model.addAttribute("errormessage", ecx.getMessage());
            return "operator/viewOption";
        }
    }

    @PostMapping(value = "operator/deleteAllIncompatibleOptions")
    public String removeAllIncompatibleOptions(Model model, HttpServletRequest req) {
        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.TARIFFS.toString());
        breadcrumb.add(PageName.VIEW_TARIFF.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.VIEW_OPTION.toString());

        Option option = null;
        Tariff tariff = null;
        try {
            option = optionService.getOptionById(Long.valueOf(req.getParameter("optionId")));
            optionService.clearIncompatibleOptions(option);
            optionService.edit(option);
            model.addAttribute("option", option);
            tariff = tariffService.getTariffById(Long.valueOf(req.getParameter("tariffId")));
            model.addAttribute("tariff", tariff);
            model.addAttribute("successmessage", "All incompatible options removed from option " + option.getTitle() + ".");
            return "operator/viewOption";
        } catch (ECareException ecx) {
            option = optionService.getOptionById(Long.valueOf(req.getParameter("id")));
            model.addAttribute("option", option);
            tariff = tariffService.getTariffById(Long.valueOf(req.getParameter("tariffId")));
            model.addAttribute("tariff", tariff);
            model.addAttribute("errormessage", ecx.getMessage());
            return "operator/viewOption";
        }
    }

}
