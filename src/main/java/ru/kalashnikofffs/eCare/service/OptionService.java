package ru.kalashnikofffs.eCare.service;

import ru.kalashnikofffs.eCare.model.Option;

import java.util.List;

/**
 * Interface of service level for option entity.
 * It is an intermediate level between controllers and option DAO.
 */
public interface OptionService {

    /**
     * This method provides saving of option in the database.
     *
     * @param option option entity to be saved.
     */
    void add(Option option);

    /**
     * This method provides updating of option in the database.
     * @param option option entity to be updated.
     */
    void edit(Option option);

    /**
     * This method provides loading of option from the database.
     *
     * @param id option id for search that option in the database.
     * @return loaded option entity.
     */
    Option getOptionById(Long id);

    /**
     * This method provides deleting of option from the database.
     *
     * @param id option id for deleting that option from the database.
     */
    void delete(Long id);

    /**
     * This method provides receiving number of all options from the database.
     *
     * @return number of options in the database.
     */
    Long getNumberOfOptions();

    /**
     * This method provides receiving of all options for tariff from the database.
     *
     * @param id tariff id for searching of all options for this tariff.
     * @return list of received options.
     */
    List<Option> getAllOptionsForTariff(Long id);

    /**
     * This method provides deleting of all options for tariff from the database.
     *
     * @param id tariff id for deleting of all options for this tariff.
     */
    void deleteAllOptionsForTariff(Long id);

    /**
     * This method sets dependent option for current option.
     *
     * @param currentOption current option entity.
     * @param dependentOption option entity which must be sets as dependent for current option.
     * @return current option entity with dependent option in list.
     */
    Option setDependentOption(Option currentOption, Option dependentOption);

    /**
     * This method removes dependent option for current option.
     *
     * @param currentOption current option entity.
     * @param dependentOption option entity which must be removed from list of dependent options for current option.
     * @return current option entity without dependent option in list.
     */
    Option deleteDependentOption(Option currentOption, Option dependentOption);

    /**
     * This method removes all dependent options for current option.
     *
     * @param currentOption current option entity.
     */
    void clearDependentOptions(Option currentOption);

    /**
     * This method sets incompatible option for current option.
     *
     * @param currentOption current option entity.
     * @param incompatibleOption option entity which must be sets as incompatible for current option.
     * @return current option entity with incompatible option in list.
     */
    Option setIncompatibleOption(Option currentOption, Option incompatibleOption);

    /**
     * This method removes incompatible option for current option.
     *
     * @param currentOption current option entity.
     * @param incompatibleOption option entity which must be removed from list of incompatible options for current option.
     * @return current option entity without incompatible option in list.
     */
    Option deleteIncompatibleOption(Option currentOption, Option incompatibleOption);

    /**
     * This method removes all incompatible options for current option.
     *
     * @param currentOption current option entity.
     */
    void clearIncompatibleOptions(Option currentOption);

    /**
     * This method creates new option for tariff with set of dependent and incompatible options if they exists.
     *
     * @param option Option entity.
     * @param dependentOptionsArray Array of dependent options, can be null.
     * @param incompatibleOptionsArray Array of incompatible options, can be null.
     * @return created option.
     */
    Option createDependencies(Option option, String[] dependentOptionsArray, String[] incompatibleOptionsArray);
}
