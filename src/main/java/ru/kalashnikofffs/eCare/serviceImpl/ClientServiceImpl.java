package ru.kalashnikofffs.eCare.serviceImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.kalashnikofffs.eCare.dao.ClientDAO;
import ru.kalashnikofffs.eCare.dao.RoleDAO;
import ru.kalashnikofffs.eCare.model.Client;
import ru.kalashnikofffs.eCare.model.Role;
import ru.kalashnikofffs.eCare.ECareException;
import ru.kalashnikofffs.eCare.service.ClientService;
import ru.kalashnikofffs.eCare.validator.RegistrationValidator;

import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {

    private static Logger logger = Logger.getLogger(ClientService.class);

    private ClientDAO clientDAO;

    @Autowired
    public void setClientDAO(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    private RoleDAO roleDAO;

    @Autowired
    public void setRoleDAO(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RegistrationValidator registrationValidator;

    @Override
    @Transactional
    public void add(Client client) throws ECareException {
        logger.info("Add client " + client + " in DB.");
        try {
            client.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));
            Set<Role> roles = new HashSet<>();
            roles.add(roleDAO.getById(1L));
            client.setRoles(roles);
            clientDAO.add(client);
            logger.info("Client " + client + " added in DB.");
        } catch (Exception ex) {
            ECareException eCareException = new ECareException("Failed to add client " + client + " in DB.", ex);
            logger.warn(eCareException.getMessage(), ex);
            throw eCareException;
        }
    }

    public Boolean validate(Client client, BindingResult bindingResult) {
        registrationValidator.validate(client, bindingResult);

        if (bindingResult.hasErrors()) {
            return false;
        }
        else return true;
    }


    @Override
    @Transactional
    public void edit(Client client) throws ECareException {
        logger.info("Update client " + client + " in DB.");
        try {
            clientDAO.edit(client);
            logger.info("Client " + client + " updated in DB.");
        } catch (Exception ex) {
            ECareException eCareException = new ECareException("Failed to update client " + client + " in DB.", ex);
            logger.warn(eCareException.getMessage(), ex);
            throw eCareException;
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws ECareException {
        logger.info("Delete client with id: " + id + " from DB.");
        Client client = clientDAO.getClientById(id);
        if (client == null) {
            ECareException eCareException = new ECareException("Client with id = " + id + " not exist.");
            logger.warn(eCareException.getMessage(), eCareException);
            throw eCareException;
        }
        clientDAO.delete(client);
        logger.info("Client " + client + " deleted from DB.");
        clientDAO.delete(client);
    }

    @Override
    @Transactional
    public List<Client> getAllClients() throws ECareException {
        logger.info("Get all clients from DB.");
        List<Client> clients = clientDAO.getAllClients();
        // If DAO returns null method will throws an ECareException.
        if (clients == null) {
            ECareException eCareException = new ECareException("Failed to get all clients from DB.");
            logger.error(eCareException.getMessage(), eCareException);
            throw eCareException;
        }
        logger.info("All clients obtained from DB.");
        // Else method returns list of client entities
        return clients;
    }

    @Override
    @Transactional
    public void deleteAllClients() {
        logger.info("Delete all clients from DB.");
        clientDAO.deleteAllClients();
        logger.info("All clients deleted from DB.");
    }

    @Override
    @Transactional
    public Long getNumberOfClients() {
        logger.info("Get number of clients in DB.");
        Long number = clientDAO.getSize();
        logger.info(number + " clients obtained from DB.");
        return number;
    }

    @Override
    @Transactional
    public Client getClientById(Long id) throws ECareException {
        logger.info("Get client with id: " + id + " from DB.");
        Client client = clientDAO.getClientById(id);
        //If DAO returns null method will throws an ECareException
        if (client == null) {
            ECareException ecx = new ECareException("Client with id = " + id + " not found in DB.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        logger.info("Client " + client + " got from DB.");
        //else method returns client entity
        return client;
    }

    @Override
    @Transactional
    public Client findClientByNumber(Long number) throws ECareException {
        logger.info("Find client with telephone number: " + number + " in DB.");
        Client client = null;
        try {
            // Search of client in the database by DAO method.
            client = clientDAO.findClientByNumber(number);
            // If client does not exist in database, block "catch" catches the NoResultException and
            // throws an ECareException.
        } catch (NoResultException nrx) {
            ECareException ecx = new ECareException("Client with number: " + number + " not found.", nrx);
            logger.warn(ecx.getMessage(), nrx);
            throw ecx;
        }
        logger.info("Client " + client + " found and loaded from DB.");
        return client;
    }

    @Override
    @Transactional
    public Client findClientByEmail(String email) throws ECareException {
        logger.info("Find client with Email: " + email + " in DB.");
        Client client = null;
        try {
            // Search of client in the database by DAO method.
            client = clientDAO.findClientByEmail(email);
            // If client does not exist in database, block "catch" catches the NoResultException and
            // throws an ECareException.
        } catch (NoResultException nrx) {
            ECareException ecx = new ECareException("Client with Email: " + email + " not found.", nrx);
            logger.warn(ecx.getMessage(), nrx);
            throw ecx;
        }
        logger.info("Client " + client + " found and loaded from DB.");
        return client;
    }

    @Override
    @Transactional
    public Boolean existEmail(String email) {
        logger.info("Find client with email: " + email + " in DB.");
        Client cl = null;
        try {
            // Search of client in the database by DAO method.
            cl = clientDAO.findClientByEmail(email);
            // If client does not exist in database, block try catches the NoResultException and
            // return false.
        } catch (NoResultException nrx) {
            logger.warn("Client with email: " + email + " does not exist.");
            return false;
        }
        logger.info("Client " + cl + " found in DB.");
        // Else, if client exist and loaded, method returns true.
        return true;
    }
}
