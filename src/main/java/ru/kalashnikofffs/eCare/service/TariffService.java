package ru.kalashnikofffs.eCare.service;

import ru.kalashnikofffs.eCare.model.Tariff;

import java.util.List;

/**
 * Interface of service level for tariff entity.
 * It is an intermediate level between controllers and tariff DAO.
 */
public interface TariffService {
    /**
     * This method provides saving of tariff in the database.
     *
     * @param tariff tariff entity to be saved.
     */
    void add(Tariff tariff);

    /**
     * This method provides updating of tariff in the database.
     *
     * @param tariff tariff entity to be updated.
     */
    void edit(Tariff tariff);

    /**
     * This method provides loading of tariff from the database.
     *
     * @param id tariff id for search that tariff in the database.
     * @return loaded tariff entity.
     */
    Tariff getTariffById(Long id);

    /**
     * This method provides receiving of all options from the database.
     *
     * @return list of received tariffs.
     */
    List<Tariff> getAllTariffs();

    /**
     * This method provides deleting of tariff from the database.
     *
     * @param id tariff id for deleting that tariff from the database.
     */
    void deleteTariff(Long id);

    /**
     * This method provides deleting of all tariffs from the database.
     */
    void deleteAllTariffs();

    /**
     * This method provides receiving number of all tariffs from the database.
     *
     * @return number of tariffs in the database.
     */
    Long getNumberOfTariffs();
}
