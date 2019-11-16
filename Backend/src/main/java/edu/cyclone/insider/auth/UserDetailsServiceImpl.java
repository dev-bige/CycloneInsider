package edu.cyclone.insider.auth;

import edu.cyclone.insider.models.InsiderUser;
import edu.cyclone.insider.repos.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * A service for the authentication functionality to find a user by a username
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UsersRepository usersRepository;

    @Autowired
    public UserDetailsServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        InsiderUser insiderUser = usersRepository.findUserByUsernameNotNull(username);
        if (insiderUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(insiderUser.getUsername(), insiderUser.getPassword(), Collections.emptyList());
    }
}
