package ru.kalashnikofffs.eCare.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kalashnikofffs.eCare.dao.ClientDAO;
import ru.kalashnikofffs.eCare.model.Client;
import ru.kalashnikofffs.eCare.model.Role;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private ClientDAO clientDAO;

    @Autowired
    public void setClientDAO(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @Override
    @Transactional(readOnly = true)
    // предоставляются разрешения для данного пользователя при входе
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = clientDAO.findClientByEmail(email);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (Role role : client.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getTitle()));
        }

        return new org.springframework.security.core.userdetails.User(client.getEmail(), client.getPassword(), grantedAuthorities);
    }
}
