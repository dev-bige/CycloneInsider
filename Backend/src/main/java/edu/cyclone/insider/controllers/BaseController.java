package edu.cyclone.insider.controllers;

import edu.cyclone.insider.models.InsiderUser;
import edu.cyclone.insider.repos.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseController {
    protected UsersRepository usersRepository;

    @Autowired
    public BaseController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * @return the current user that is requesting
     */
    public final InsiderUser getCurrentUser() {
        return usersRepository.findUserByUsername(getCurrentUsername());
    }

    /**
     * @return the current username that is requesting
     */
    public final String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


}
