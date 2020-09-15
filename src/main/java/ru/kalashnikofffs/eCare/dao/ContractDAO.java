package ru.kalashnikofffs.eCare.dao;

import ru.kalashnikofffs.eCare.model.Contract;

import java.util.List;

public interface ContractDAO {
    void add(Contract contract);
    void edit(Contract contract);
    Contract getContractById(Long id);
    void delete(Contract contract);
    List<Contract> getAllContracts();
    void deleteAllContracts();
    Long getSize();
    Contract findContractByNumber(Long number);
    List<Contract> getAllContractsForClient(Long id);
    void deleteAllContractsForClient(Long id);
}
