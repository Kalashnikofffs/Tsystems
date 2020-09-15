package ru.kalashnikofffs.eCare.service;

import ru.kalashnikofffs.eCare.model.Client;
import ru.kalashnikofffs.eCare.model.Contract;
import ru.kalashnikofffs.eCare.model.Option;
import ru.kalashnikofffs.eCare.model.Tariff;

import java.util.List;

/**
 * Interface of service level for contract entity.
 * It is an intermediate level between controllers and contract DAO.
 */
public interface ContractService {

    /**
     * This method provides saving of contracts in the database.
     *
     * @param contract contract entity to be saved.
     */
    void add(Contract contract);

    /**
     * This method provides updating of contracts in the database.
     * @param contract contract entity to be updated.
     */
    void edit(Contract contract);

    /**
     * This method provides deleting of contract from the database.
     *
     * @param id contract id for deleting that contract from the database.
     */
    void delete(Long id);

    /**
     * This method provides loading of contracts from the database.
     *
     * @param id contract id for search that contract in the database.
     * @return loaded contract entity.
     */
    Contract getContractById(Long id);

    /**
     * This method provides receiving of all contracts from the database.
     *
     * @return list of received contracts.
     */
    List<Contract> getAllContracts();

    /**
     * This method provides finding of contracts by telephone number in the database.
     *
     * @param number contract number for search that contract in the database.
     * @return found contract entity.
     */
    Contract findContractByNumber(Long number);

    /**
     * This method provides receiving of all contracts for client from the database.
     *
     * @param id client id for searching of all contracts for this client.
     * @return list of received contracts.
     */
    List<Contract> getAllContractsForClient(Long id);

    /**
     * This method provides deleting of all contracts from the database.
     */
    void deleteAllContracts();

    /**
     * This method provides deleting of all contracts for client from the database.
     *
     * @param id client id for deleting of all contracts for this client.
     */
    void deleteAllContractsForClient(Long id);

    /**
     * This method provides receiving number of all contracts from the database.
     *
     * @return number of contracts in the database.
     */
    Long getNumberOfContracts();

    /**
     * This method provides receiving of info about block by client status of contract.
     *
     * @param contract contract entity for receiving block status.
     * @return true if contract blocked by client or false if contract not blocked by client.
     */
    boolean isBlockedByClient(Contract contract);

    /**
     * This method provides receiving of info about block by operator status of contract.
     *
     * @param contract contract entity for receiving block status.
     * @return true if contract blocked by operator or false if contract not blocked by operator.
     */
    boolean isBlockedByOperator(Contract contract);

    /**
     * This method provides blocking of contract by client.
     *
     * @param contract contract entity that must be blocked by client.
     */
    void blockByClient(Contract contract);

    /**
     * This method provides unblocking of contract by client.
     *
     * @param contract contract entity that must be unblocked by client.
     */
    void unblockByClient(Contract contract);

    /**
     * This method provides blocking of contract by operator.
     *
     * @param contract contract entity that must be blocked by operator.
     */
    void blockByOperator(Contract contract);

    /**
     * This method provides unblocking of contract by operator.
     *
     * @param contract contract entity that must be unblocked by operator.
     */
    void unblockByOperator(Contract contract);

    /**
     * This method provides connecting of option in contract.
     *
     * @param client client entity in which must be enabled option.
     * @param contract contract entity in which must be enabled option.
     * @param option option entity which must be enabled.
     * @return contract entity with enabled option.
     */
    Contract enableOption(Client client, Contract contract, Option option);

    /**
     * This method provides disabling of option in contract
     *
     * @param contract contract entity in which must be disabled option.
     * @param option option entity which must be disabled.
     * @return contract entity with disabled option.
     */
    Contract disableOption(Contract contract, Option option);

    /**
     * This method provides setting of tariff in contract
     *
     * @param client client entity in which must be set option.
     * @param contract contract entity in which must be set option.
     * @param tariff tariff entity which must be set in contract
     * @param chosenOptionsArray list of chosen options for tariff
     * @return contract entity with established tariff and options.
     */
    Contract setTariff(Client client, Contract contract, Tariff tariff, String[] chosenOptionsArray);

    /**
     * This method provides setting of the default tariff in new contract.
     *
     * @param client client entity in which must be set default tariff.
     * @param contract contract entity (new contract) in which must be set default tariff.
     * @return contract with default tariff.
     */
    Contract setDefaultTariff(Client client, Contract contract);

    /**
     * This method provides searching of contract in database by telephone number.
     *
     * @param number contract number.
     * @return true if contract with input number exist, or false if contract not exist.
     */
    Boolean isExistNumber(Long number);
}
