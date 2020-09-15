package ru.kalashnikofffs.eCare.serviceImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kalashnikofffs.eCare.dao.OptionDAO;
import ru.kalashnikofffs.eCare.dao.TariffDAO;
import ru.kalashnikofffs.eCare.model.Option;
import ru.kalashnikofffs.eCare.ECareException;
import ru.kalashnikofffs.eCare.service.OptionService;

import java.util.*;

@Service
public class OptionServiceImpl implements OptionService {

    private static Logger logger = Logger.getLogger(OptionService.class);

    private OptionDAO optionDAO;

    @Autowired
    public void setOptionDAO(OptionDAO optionDAO) {
        this.optionDAO = optionDAO;
    }

    private TariffDAO tariffDAO;
    @Autowired
    public void setTariffDAO(TariffDAO tariffDAO) {
        this.tariffDAO = tariffDAO;
    }

    @Override
    @Transactional
    public void add(Option option) throws ECareException {
        logger.info("Add option " + option + " in DB.");
        try {
            optionDAO.add(option);
            logger.info("Option " + option + " added in DB.");
        } catch (Exception ex) {
            ECareException eCareException = new ECareException("Failed to add option " + option + " in DB.", ex);
            logger.warn(eCareException.getMessage(), ex);
            throw eCareException;
        }
    }

    @Override
    @Transactional
    public void edit(Option option) throws ECareException {
        logger.info("Update option " + option + " in DB.");
        try {
            optionDAO.edit(option);
            logger.info("Option " + option + " updated in DB.");
        } catch (Exception ex) {
            ECareException eCareException = new ECareException("Failed to update option " + option + " in DB.", ex);
            logger.warn(eCareException.getMessage(), ex);
            throw eCareException;
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws ECareException {
        logger.info("Delete option with id: " + id + " from DB.");
        Option option = optionDAO.getOptionById(id);
        //If DAO returns null method will throws an ECareException
        if (option == null) {
            ECareException ecx = new ECareException("Option with id = " + id + " not exist.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        // Else option will be deleted from the database.
        optionDAO.delete(option);
        logger.info("Option " + option + " deleted from DB.");
    }

    @Override
    @Transactional
    public Option getOptionById(Long id) throws ECareException {
        logger.info("Load option with id: " + id + " from DB.");
        Option option = optionDAO.getOptionById(id);
        //If DAO returns null method will throws an ECareException
        if (option == null) {
            ECareException ecx = new ECareException("Option with id = " + id + " not found in DB.");
            logger.warn(String.valueOf(ecx), ecx);
            throw ecx;
        }
        logger.info("Option " + option + " loaded from DB.");
        //else method returns option entity
        return option;
    }

    @Override
    @Transactional
    public Long getNumberOfOptions() {
        logger.info("Get number of options in DB.");
        Long number = optionDAO.getSize();
        logger.info(number + "of options obtained from DB.");
        return number;
    }

    @Override
    @Transactional
    public List<Option> getAllOptionsForTariff(Long id) throws ECareException {
        logger.info("Get all options from DB for tariff with id: " + id + ".");
        List<Option> options = optionDAO.getAllOptionsForTariff(id);
        //If DAO returns null method will throws an ECareException
        if (options == null) {
            ECareException ecx = new ECareException("Failed to get all options from DB for tariff id: " + id + ".");
            logger.error(ecx.getMessage(), ecx);
            throw ecx;
        }
        logger.info("All options for tariff id: " + id + " obtained from DB.");
        // Else method returns list of option entities
        return options;
    }

    @Override
    @Transactional
    public void deleteAllOptionsForTariff(Long id) {
        logger.info("Delete all options from DB for tariff with id: " + id + ".");
        optionDAO.deleteAllOptionsForTariff(id);
        logger.info("All options for tariff id: " + id + " deleted from DB.");
    }

    @Override
    @Transactional
    public Option setDependentOption(Option currentOption, Option dependentOption) throws ECareException{
        logger.info("Set dependency of option id: " + currentOption.getId() + " with option id: " + dependentOption.getId() + ".");
        // If current option not incompatible for chosen option.
        if(!currentOption.getIncompatibleOptions().contains(dependentOption)) {
            // If current option not linked with chosen option by dependency or current option is not chosen option.
            if(!currentOption.getDependentOptions().contains(dependentOption) && !currentOption.equals(dependentOption)) {
                currentOption.addDependentOption(dependentOption);
                logger.info("Option id: " + dependentOption.getId() + " is now dependent for option id: " + currentOption.getId() + ".");
//                edit(currentOption);
            }
        }
        else {
            ECareException ecx = new ECareException("Chosen options are incompatible.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        return currentOption;
    }

    @Override
    @Transactional
    public Option deleteDependentOption(Option currentOption, Option dependentOption) throws ECareException {
        logger.info("Remove dependency of option id: " + currentOption.getId() + " with option id: " + dependentOption.getId() + ".");
        // If current option linked with chosen option by dependency.
        if(currentOption.getDependentOptions().contains(dependentOption)) {
            currentOption.deleteDependentOption(dependentOption);
            logger.info("Option id: " + dependentOption.getId() + " is now independent from option id: " + currentOption.getId() + ".");
            edit(currentOption);
        }
        else {
            ECareException ecx = new ECareException("Option " + currentOption.getId() + " not contains such dependence.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        return currentOption;
    }

    @Override
    @Transactional
    public void clearDependentOptions(Option currentOption) {
        logger.info("Remove all dependent options from option id: " + currentOption.getId() + ".");
        Set<Option> options = currentOption.getDependentOptions();
        Collections.synchronizedSet(options);
        for (Option o : options) {
            // For every dependent option for current option: remove dependency.
            deleteDependentOption(o, currentOption);
            edit(o);
        }
        // Remove all dependent options for current option.
        currentOption.getDependentOptions().clear();
        logger.info("All dependent options removed from option id: " + currentOption.getId() + ".");
    }

    @Override
    @Transactional
    public Option setIncompatibleOption(Option currentOption, Option incompatibleOption) throws ECareException {
        logger.info("Set incompatibility of option id: " + currentOption.getId() + " with option id: " + incompatibleOption.getId() + ".");
        // If current option not dependent for chosen option.
        if(!currentOption.getDependentOptions().contains(incompatibleOption)) {
            // If current option not linked with chosen option by incompatibility or current option is not chosen option.
            if(!currentOption.getIncompatibleOptions().contains(incompatibleOption) && !currentOption.equals(incompatibleOption)) {
                currentOption.addIncompatibleOption(incompatibleOption);
                logger.info("Option id: " + currentOption.getId() + " is now incompatible with option id: " + incompatibleOption.getId() + ".");
//                edit(currentOption);
            }
        }
        else {
            ECareException ecx = new ECareException("Chosen options are dependent.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        return currentOption;
    }

    @Override
    @Transactional
    public Option deleteIncompatibleOption(Option currentOption, Option incompatibleOption) throws ECareException {
        logger.info("Remove incompatibility of option id: " + currentOption.getId() + " with option id: " + incompatibleOption.getId() + ".");
        // If current option linked with chosen option by incompatibility.
        if(currentOption.getIncompatibleOptions().contains(incompatibleOption)) {
            currentOption.deleteIncompatibleOption(incompatibleOption);
            logger.info("Option id: " + currentOption.getId() + " is not incompatible now with option id: " + incompatibleOption.getId() + ".");
            edit(currentOption);
        }
        else {
            ECareException ecx = new ECareException("Option " + currentOption.getId() + " not contains such incompatibility.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        return currentOption;
    }

    @Override
    @Transactional
    public void clearIncompatibleOptions(Option currentOption) {
        logger.info("Remove all incompatible options from option id: " + currentOption.getId() + ".");
        Set<Option> options = currentOption.getIncompatibleOptions();
        Collections.synchronizedSet(options);
        for (Option o : options) {
            // For every incompatible option for current option: remove incompatibility.
            deleteIncompatibleOption(o, currentOption);
            edit(o);
        }
        // Remove all incompatible options for current option.
        currentOption.getIncompatibleOptions().clear();
        logger.info("All incompatible options removed from option id: " + currentOption.getId() + ".");
    }

    @Override
    @Transactional
    public Option createDependencies(Option option, String[] dependentOptionsArray, String[] incompatibleOptionsArray) {
        //Set dependent options if exists for current option.
        Long dependentOptionId = 0L;
        Option dependentOption = null;
        if(dependentOptionsArray != null) {
            for(String stringId: dependentOptionsArray) {
                dependentOptionId = Long.parseLong(stringId);
                dependentOption = getOptionById(dependentOptionId);
                setDependentOption(option, dependentOption);
                setDependentOption(dependentOption, option);
            }
        }
        //Set incompatible options if exists for current option.
        Long incompatibleOptionId = 0L;
        Option incompatibleOption = null;
        if(incompatibleOptionsArray != null) {
            for (String stringId : incompatibleOptionsArray) {
                incompatibleOptionId = Long.parseLong(stringId);
                incompatibleOption = getOptionById(incompatibleOptionId);
                setIncompatibleOption(option, incompatibleOption);
                setIncompatibleOption(incompatibleOption, option);
            }
        }
        //Updating of option in DB.
//        edit(option);
        return option;
    }
}
