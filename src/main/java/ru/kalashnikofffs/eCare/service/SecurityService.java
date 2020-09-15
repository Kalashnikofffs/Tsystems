package ru.kalashnikofffs.eCare.service;

/**
 * Interface of service level for option entity.
 * It is an intermediate level between controllers and option DAO.
 */
public interface SecurityService {

    /**
     * This method provides autoLogin for new client.
     * @param username email of new client.
     * @param password password of new client.
     */
    void autoLogin(String username, String password);
    String getRoleFromAuthorities ();
}
