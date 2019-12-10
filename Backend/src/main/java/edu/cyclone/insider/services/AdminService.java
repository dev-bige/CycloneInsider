package edu.cyclone.insider.services;

import edu.cyclone.insider.models.InsiderUser;
import edu.cyclone.insider.models.UserLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AdminService {
    private UserStateService userStateService;
    private UserService userService;

    @Autowired
    public AdminService(UserStateService userStateService,
                        UserService userService) {
        this.userStateService = userStateService;
        this.userService = userService;
    }

    public InsiderUser elevateUserToAdmin(UUID userId) {
        if (userStateService.hasAdminPrivileges()) {
            InsiderUser user = userService.getByUUID(userId);
            user.setUserLevel(UserLevel.ADMIN);
            return userService.save(user);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    public InsiderUser validateProfessor(UUID userId) {
        if (userStateService.hasAdminPrivileges()) {
            InsiderUser user = userService.getByUUID(userId);
            if (user.getProfPending()) {
                user.setProfPending(false);
                user.setUserLevel(UserLevel.PROFESSOR);
                return userService.save(user);
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user is not waiting on validation of being a professor");
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    public void deleteUser(UUID userUuid) {
        if (this.userStateService.hasAdminPrivileges()) {
            userService.deleteUser(userUuid);
            return;
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
