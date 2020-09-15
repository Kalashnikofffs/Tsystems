package ru.kalashnikofffs.eCare.dao;

import ru.kalashnikofffs.eCare.model.Client;

import java.util.List;

public interface ClientDAO {
    void add(Client client);
    void edit(Client client);
    Client getClientById(Long id);
    void delete(Client client);
    List<Client> getAllClients();
    void deleteAllClients();
    Long getSize();
    Client findClientByNumber(Long number);
    Client findClientByEmail (String email);
    Client findClientByLoginAndPassword(String login, String password);
}
