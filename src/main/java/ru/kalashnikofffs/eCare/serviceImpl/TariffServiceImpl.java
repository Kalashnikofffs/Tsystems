package ru.kalashnikofffs.eCare.serviceImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kalashnikofffs.eCare.dao.TariffDAO;
import ru.kalashnikofffs.eCare.model.Tariff;
import ru.kalashnikofffs.eCare.ECareException;
import ru.kalashnikofffs.eCare.service.TariffService;

import java.util.List;

@Service
public class TariffServiceImpl implements TariffService {

    private static Logger logger = Logger.getLogger(TariffService.class);

    private TariffDAO tariffDAO;

    @Autowired
    public void setTariffDAO(TariffDAO tariffDAO) {
        this.tariffDAO = tariffDAO;
    }

    @Override
    @Transactional
    public List<Tariff> getAllTariffs() {
        logger.info("Get all tariffs from DB.");
        List<Tariff> tariffs = tariffDAO.getAllTariffs();
        //If DAO returns null method will throws an ECareException.
        if (tariffs == null) {
            ECareException ecx = new ECareException("Failed to get all tariffs from DB.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        logger.info("All tariffs obtained from DB.");
        // Else method returns list of tariff entities.
        return tariffs;
    }

    @Override
    @Transactional
    public void add(Tariff tariff) throws ECareException {
        logger.info("Add tariff " + tariff + " in DB.");
        try {
            tariffDAO.add(tariff);
            logger.info("Tariff " + tariff + " added in DB.");
        } catch (Exception ex) {
            ECareException eCareException = new ECareException("Failed to add tariff " + tariff + " in DB.", ex);
            logger.warn(eCareException.getMessage(), ex);
            throw eCareException;
        }
    }

    @Override
    @Transactional
    public void edit(Tariff tariff) throws ECareException {
        logger.info("Update tariff " + tariff + " in DB.");
        try {
            tariffDAO.edit(tariff);
            logger.info("Tariff " + tariff + " updated in DB.");
        } catch (Exception ex) {
            ECareException eCareException = new ECareException("Failed to update tariff " + tariff + " in DB.", ex);
            logger.warn(eCareException.getMessage(), ex);
            throw eCareException;
        }
    }

    @Override
    @Transactional
    public Tariff getTariffById(Long id) throws ECareException {
        logger.info("Load tariff with id: " + id + " from DB.");
        Tariff tariff = tariffDAO.getTariffById(id);
        //If DAO returns null method will throws an ECareException.
        if (tariff == null) {
            ECareException ecx = new ECareException("Tariff with id = " + id + " not found.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        logger.info("Tariff " + tariff + " loaded from DB.");
        //Else method returns tariff entity.
        return tariff;
    }

    @Override
    @Transactional
    public void deleteTariff(Long id) throws ECareException {
        logger.info("Delete tariff with id: " + id + " from DB.");
        Tariff tariff = tariffDAO.getTariffById(id);
        //If DAO returns null method will throws an ECareException.
        if (tariff == null) {
            ECareException ecx = new ECareException("Tariff with id = " + id + " not exist.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        // Else tariff will be deleted from the database.
        tariffDAO.delete(tariff);
        logger.info("Tariff " + tariff + " deleted from DB.");
    }

    @Override
    @Transactional
    public void deleteAllTariffs() {
        logger.info("Delete all tariffs from DB.");
        tariffDAO.deleteAllTariffs();
        logger.info("All tariffs deleted from DB.");
    }

    @Override
    @Transactional
    public Long getNumberOfTariffs() {
        logger.info("Get number of tariffs in DB.");
        Long number = tariffDAO.getSize();
        logger.info(number + " of tariffs obtained fromDB.");
        return number;
    }
}
