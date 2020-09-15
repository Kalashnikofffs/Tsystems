package ru.kalashnikofffs.eCare.dao;

import ru.kalashnikofffs.eCare.model.Option;

import java.util.List;

public interface OptionDAO {
    void add(Option option);
    void edit(Option option);
    Option getOptionById(Long id);
    void delete(Option option);
    List<Option> getAllOptions();
    void deleteAllOptions();
    Long getSize();
    List<Option> getAllOptionsForTariff(Long id);
    void deleteAllOptionsForTariff(Long id);
    Option findOptionByTitleAndTariffId(String title, Long id);
}
