package ru.kalashnikofffs.eCare.serviceImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kalashnikofffs.eCare.dao.ContractDAO;
import ru.kalashnikofffs.eCare.model.Client;
import ru.kalashnikofffs.eCare.model.Contract;
import ru.kalashnikofffs.eCare.model.Option;
import ru.kalashnikofffs.eCare.model.Tariff;
import ru.kalashnikofffs.eCare.ECareException;
import ru.kalashnikofffs.eCare.service.ClientService;
import ru.kalashnikofffs.eCare.service.ContractService;
import ru.kalashnikofffs.eCare.service.OptionService;
import ru.kalashnikofffs.eCare.service.TariffService;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {

    private static Logger logger = Logger.getLogger(ContractService.class);

    private ContractDAO contractDAO;
    @Autowired
    public void setContractDAO(ContractDAO contractDAO) {
        this.contractDAO = contractDAO;
    }

    private ClientService clientService;
    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    private TariffService tariffService;
    @Autowired
    public void setTariffService(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    private OptionService optionService;
    @Autowired
    public void setOptionService(OptionService optionService) {
        this.optionService = optionService;
    }

    @Override
    @Transactional
    public List<Contract> getAllContracts() throws ECareException {
        logger.info("Get all contracts from DB.");
        List<Contract> contracts = contractDAO.getAllContracts();
        // If DAO returns null method will throws an ECareException
        if (contracts == null) {
            ECareException ecx = new ECareException("Failed to get all contracts from DB.");
            logger.error(ecx.getMessage(), ecx);
            throw ecx;
        }
        logger.info("All contracts obtained from DB.");
        // Else method returns list of contract entities.
        return contracts;
    }

    @Override
    @Transactional
    public void add(Contract contract) throws ECareException {
        logger.info("Save/update contract " + contract + " in DB.");
        try {
            contractDAO.add(contract);
            logger.info("Contract " + contract + " added in DB.");
        } catch (Exception ex) {
            ECareException ecx = new ECareException("Failed to add contract " + contract + " in DB.");
            logger.error(ecx.getMessage(), ecx);
            throw ecx;
        }
    }

    @Override
    @Transactional
    public void edit(Contract contract) throws ECareException {
        logger.info("Update contract " + contract + " in DB.");
        try {
            contractDAO.edit(contract);
            logger.info("Contract " + contract + " updated in DB.");
        } catch (Exception ex) {
            ECareException eCareException = new ECareException("Failed to update contract " + contract + " in DB.", ex);
            logger.warn(eCareException.getMessage(), ex);
            throw eCareException;
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws ECareException {
        logger.info("Delete contract with id: " + id + " from DB.");
        Contract contract = contractDAO.getContractById(id);
        // If DAO returns null method will throws an ECareException.
        if(contract == null) {
            ECareException eCareException = new ECareException("Contract with id = " + id + " not exist.");
            logger.warn(eCareException.getMessage(), eCareException);
            throw eCareException;
        }
        // Else client will be deleted from the database.
        contractDAO.delete(contract);
        logger.info("Contract " + contract + " deleted from DB.");
    }

    @Override
    @Transactional
    public Contract getContractById(Long id) throws ECareException {
        logger.info("Load contract with id: " + id + " from DB.");
        Contract contract = contractDAO.getContractById(id);
        //If DAO returns null method will throws an ECareException
        if (contract == null) {
            ECareException ecx = new ECareException("Contract with id = " + id + " not found in DB.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        logger.info("Contract " + contract + " loaded from DB.");
        //else method returns contract entity
        return contract;
    }

    @Override
    @Transactional
    public Contract findContractByNumber(Long number) throws ECareException {
        logger.info("Find contract by telephone number: " + number + " in DB.");
        Contract contract = null;
        try {
            // Search of contract in the database by DAO method.
            contract = contractDAO.findContractByNumber(number);
            // If contract does not exist in database, block try catches the NoResultException and
            // throws an ECareException.
        } catch (NoResultException nrx) {
            ECareException ecx = new ECareException("Contract with number: " + number + " not found.", nrx);
            logger.warn(ecx.getMessage(), nrx);
            throw ecx;
        }
        logger.info("Contract " + contract + " found and loaded from DB.");
        return contract;
    }

    @Override
    @Transactional
    public List<Contract> getAllContractsForClient(Long id) {
        logger.info("Get all contracts from DB for client with id: " + id + ".");
        List<Contract> contracts = contractDAO.getAllContractsForClient(id);
        //If DAO returns null method will throws an ECareException
        if (contracts == null) {
            ECareException ecx = new ECareException("Failed to get all contracts from DB.");
            logger.error(ecx.getMessage(), ecx);
            throw ecx;
        }
        logger.info("All contracts for client id: " + id + " obtained from DB.");
        // Else method returns list of contract entities
        return contracts;
    }

    @Override
    @Transactional
    public void deleteAllContracts() {
        logger.info("Delete all contracts from DB.");
        contractDAO.deleteAllContracts();
        logger.info("All contracts deleted from DB.");
    }

    @Override
    @Transactional
    public void deleteAllContractsForClient(Long id) {
        logger.info("Delete all contracts from DB for client with id: " + id + ".");
        contractDAO.deleteAllContractsForClient(id);
        logger.info("All contracts for client id: " + id + " deleted from DB.");
    }

    @Override
    @Transactional
    public Long getNumberOfContracts() {
        logger.info("Get number of contracts in DB.");
        Long number = contractDAO.getSize();
        logger.info(number + "of contracts obtained from DB.");
        return number;
    }

    @Override
    @Transactional
    public boolean isBlockedByClient(Contract contract) {
        logger.info("Get info about blocking of contract by client.");
        return contract.isBlockedByClient();
    }

    @Override
    @Transactional
    public boolean isBlockedByOperator(Contract contract) {
        logger.info("Get info about blocking of contract by operator.");
        return contract.isBlockedByClient();
    }

    @Override
    @Transactional
    public void blockByClient(Contract contract) throws ECareException {
        logger.info("Block of contract by client.");
        //If contract not blocked by operator.
        if(!contract.isBlockedByOperator()) {
            //If contract not blocked by client already.
            if(!contract.isBlockedByClient()) {
                contract.setBlockedByClient(true);
//                Client client = contract.getClient();
//                client.getContracts().add(contract);
                edit(contract);
                logger.info("Contract " + contract + " is blocked by client.");
            }
            else {
                ECareException ecx = new ECareException("Contract " + contract + " is already blocked by client.");
                logger.warn(ecx.getMessage(), ecx);
                throw ecx;
            }
        }
        else {
            ECareException ecx = new ECareException("Contract " + contract + " is blocked by operator.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
    }

    @Override
    @Transactional
    public void unblockByClient(Contract contract) throws ECareException {
        logger.info("Unblock of contract by client.");
        //If contract not blocked by operator.
        if(!contract.isBlockedByOperator()) {
            //If contract blocked by client.
            if(contract.isBlockedByClient()) {
                contract.setBlockedByClient(false);
                // update
                edit(contract);
                logger.info("Contract " + contract + " is unblocked by client.");
            }
            else {
                ECareException ecx = new ECareException("Contract " + contract + " is already unblocked by client.");
                logger.warn(ecx.getMessage(), ecx);
                throw ecx;
            }
        }
        else {
            ECareException ecx = new ECareException("Contract " + contract + " is blocked by operator.");
            logger.info(ecx.getMessage(), ecx);
            throw ecx;
        }
    }

    @Override
    @Transactional
    public void blockByOperator(Contract contract) throws ECareException {
        logger.info("Block of contract by operator.");
        //If contract not blocked by operator already.
        if(!contract.isBlockedByOperator()) {
            contract.setBlockedByOperator(true);
            Client client = contract.getClient();
            client.getContracts().add(contract);
            clientService.edit(client);
            logger.info("Contract " + contract + " is blocked by operator.");
        }
        else {
            ECareException ecx = new ECareException("Contract " + contract + " is already blocked by operator.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
    }

    @Override
    @Transactional
    public void unblockByOperator(Contract contract) throws ECareException {
        logger.info("Unblock of contract by operator.");
        //If contract blocked by operator.
        if(contract.isBlockedByOperator()) {
            contract.setBlockedByOperator(false);
            Client client = contract.getClient();
            client.getContracts().add(contract);
            clientService.edit(client);
            logger.info("Contract " + contract + " is unblocked by operator.");
        }
        else {
            ECareException ecx = new ECareException("Contract " + contract + " is already unblocked by operator.");
            logger.warn(ecx.getMessage());
            throw ecx;
        }
    }

    @Override
    @Transactional
    public Contract enableOption(Client client, Contract contract, Option option) throws ECareException {
        logger.info("Enable option id: " + option.getId() + " in contract id: " + contract.getId() + ".");
        // Set current enabled options for contract in separated array list.
        List<Option> currentOptions = new ArrayList<>(contract.getOptions());
        //Check for incompatibility
        if(contract.getOptions().size() != 0) {
            for(Option o: contract.getOptions()) {
                // If chosen option are incompatible with any of enabled options for contract tariff -> ecx.
                if(o.getIncompatibleOptions().contains(option)) {
                    ECareException ecx = new ECareException("Option " + option.getTitle() + " is incompatible with option "
                            + o.getTitle() + " in contract " + contract.getNumber() + ".");
                    logger.warn(ecx.getMessage(), ecx);
                    throw ecx;
                }
            }
        }
        // If chosen option not enabled yet for contract's tariff.
        if(!contract.getOptions().contains(option)) {
            contract.addOption(option);
            // If chosen option not been enabled for contract tariff in the previous time -> withdrawal for client.
            if(!currentOptions.contains(option)) {
                logger.info("Withdrawing of option id: " + option.getId() + " cost of connection: " +option.getPrice()
                        + " from balance of client id: " + client.getId() + ".");
                client.setBalance(client.getBalance() - option.getCostOfConnection());
                logger.info("Withdrawing completed. Client balance: " + client.getBalance() + ".");
            }
            logger.info("Option id: " + option.getId() + " enabled in contract id: " + contract.getId() + ".");
            for(Option dependentOption: option.getDependentOptions()) {
                // For every dependent option from chosen option: if contract not contains already that dependent option - > add.
                if(!contract.getOptions().contains(dependentOption)) {
                    contract.addOption(dependentOption);
                    // For every dependent option from chosen option: if dependent option not been enabled for contract
                    // tariff in the previous time -> withdrawal for client.
                    if(!currentOptions.contains(dependentOption)) {
                        logger.info("Withdrawing of dependent option id: " + dependentOption.getId() + " cost of connection "
                                + dependentOption.getPrice() + " from balance of client id: " + client.getId() + ".");
                        client.setBalance(client.getBalance() - dependentOption.getCostOfConnection());
                        logger.info("Withdrawing completed. Client balance: " + client.getBalance() + ".");
                    }
                    logger.info("Dependent option id: " + dependentOption.getId() + " enabled in contract id: " + contract.getId() + ".");
                }
            }
        }
        else {
            ECareException ecx = new ECareException("Option " + option.getTitle() + " is already enabled in contract " + contract.getNumber() + ".");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        // Updating of client.
        clientService.edit(client);
        return contract;
    }

    @Override
    @Transactional
    public Contract disableOption(Contract contract, Option option) throws ECareException {
        logger.info("Disable option id: " + option.getId() + " in contract id: " + contract.getId() + ".");
        // If contract contains such option -> remove.
        if(contract.getOptions().contains(option)) {
            contract.deleteOption(option);
            logger.info("Option id: " + option.getId() + " disabled in contract id: " + contract.getId() + ".");
            for(Option dependentOption: option.getDependentOptions()) {
                // For every dependent option from chosen option: if contract contains such option -> remove it.
                if(contract.getOptions().contains(dependentOption)) {
                    contract.deleteOption(dependentOption);
                    logger.info("Dependent option id: " + dependentOption.getId() + " disabled in contract id: " + contract.getId() + ".");
                }
            }
        }
        else {
            ECareException ecx = new ECareException("Option " + option.getId() + " is not enabled yet in contract " + contract.getId() + ".");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        return contract;
    }

    @Override
    @Transactional
    public Contract setTariff(Client client, Contract contract, Tariff tariff, String[] chosenOptionsArray) {
        logger.info("Set tariff: " + tariff.getId() + " in contract id: " + contract.getId() + ".");
        // Get current tariff from contract.
        Tariff currentTariff = contract.getTariff();
        // Get dependent client entity for contract.
        contract.setTariff(tariff);
        // If chosen tariff not been enabled in contract in the previous time -> withdrawal for client.
        if(!tariff.equals(currentTariff)) {
            logger.info("Withdrawing of tariff id: " + tariff.getId() + " price " + tariff.getPrice() + " from balance of client id: " + client.getId() + ".");
            client.setBalance(client.getBalance() - tariff.getPrice());
            logger.info("Withdrawing completed. Client balance: " + client.getBalance() + ".");
        }
        // Updating of client.
        clientService.edit(client);
        logger.info("Tariff: " + tariff.getId() + " is set in contract id: " + contract.getId() + ".");
        // Clear old options in contract and set new options
        contract.getOptions().clear();
        if(chosenOptionsArray != null) {
            Long optionId;
            Option option = null;
            for(String stringId: chosenOptionsArray) {
                optionId = Long.parseLong(stringId);
                option = optionService.getOptionById(optionId);
                contract = enableOption(client, contract, option);
            }
        }
        //Updating of contract in DB.
        edit(contract);
        return contract;
    }

    @Override
    @Transactional
    public Contract setDefaultTariff(Client client, Contract contract) {
        Tariff tariff = tariffService.getTariffById(12L);
        String[] optionId = {"53"};
        contract = setTariff(client, contract, tariff, optionId);
        return contract;
    }

    @Override
    @Transactional
    public Boolean isExistNumber(Long number) {
        logger.info("Find contract with number: " + number + " in DB.");
        Contract contract = null;
        try {
            // Search of client in the database by DAO method.
            contract = contractDAO.findContractByNumber(number);
            // If client does not exist in database, block try catches the NoResultException and
            // return false.
        } catch(NoResultException nrx) {
            logger.warn("Contract with number: " + number + " does not exist.");
            return false;
        }
        logger.info("Contract " + contract + " found in DB.");
        // Else, if client exist and loaded, method returns true.
        return true;
    }
}
