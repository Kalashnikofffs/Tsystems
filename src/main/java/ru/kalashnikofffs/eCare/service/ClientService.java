package ru.kalashnikofffs.eCare.service;

import org.springframework.validation.BindingResult;
import ru.kalashnikofffs.eCare.model.Client;

import java.util.List;

/**
 * Interface of service level for client entity.
 * It is an intermediate level between controllers and client DAO.
 */
public interface ClientService {





    /**
     * This method provides saving of client in the database.
     * @param client client entity to be saved.
     */
    void add(Client client);

    /**
     * This method provides updating of client in the database.
     * @param client client entity to be updated.
     */
    void edit(Client client);

    /**
     * This method provides loading of client from the database.
     * @param id client id for search that client in the database.
     * @return loaded client entity.
     */
    Client getClientById(Long id);

    /**
     * This method provides deleting of client from the database.
     * @param id client id for deleting that client from the database.
     */
    void delete(Long id);

    /**
     * This method provides receiving of all clients from the database.
     *
     * @return list of received clients.
     */
    List<Client> getAllClients();

    /**
     * This method provides deleting of all clients from the database.
     */
    void deleteAllClients();

    /**
     * This method provides receiving number of all clients from the database.
     *
     * @return number of clients in the database.
     */
    Long getNumberOfClients();

    /**
     * This method provides finding of client by telephone number in the database.
     *
     * @param number telephone number of client for search that client in the database.
     * @return found client entity.
     */
    Client findClientByNumber(Long number);

    /**
     * This method provides finding of client by email in the database.
     * @param email email of client for search that client in the database.
     * @return found client entity.
     */

    Client findClientByEmail (String email);

    /**
     * This method provides searching of client in database by client email.
     *
     * @param email client email.
     * @return true if client with input email exist, or false if client not exist.
     */
    Boolean existEmail(String email);




    Boolean validate ( Client client, BindingResult bindingResult);
}
