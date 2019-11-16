package edu.cyclone.insider.services;

import edu.cyclone.insider.models.InsiderUser;
import edu.cyclone.insider.models.UserLevel;
import edu.cyclone.insider.repos.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserStateService {
    private UserService userService;
    private UsersRepository usersRepository;

    @Autowired
    public UserStateService(UserService userService, UsersRepository usersRepository) {
        this.userService = userService;
        this.usersRepository = usersRepository;
    }

    /**
     * @return the current user that is requesting
     */
    public final InsiderUser getCurrentUser() {
        return userService.findByUsername(getCurrentUsername());
    }

    /**
     * @return the current username that is requesting
     */
    public final String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public UserLevel getUserLevel() {
        return getCurrentUser().getUserLevel();
    }

    public boolean hasAdminPrivileges() {
        return getCurrentUser().getUserLevel() == UserLevel.ADMIN;
    }

    public boolean hasProfessorPrivileges() {
        return getCurrentUser().getUserLevel() == UserLevel.PROFESSOR;
    }
}
