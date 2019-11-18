package edu.cyclone.insider.controllers.admin;

import edu.cyclone.insider.models.InsiderUser;
import edu.cyclone.insider.services.AdminService;
import edu.cyclone.insider.services.UserService;
import edu.cyclone.insider.services.UserStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("admin")
public class AdminController {
    private UserService userService;
    private AdminService adminService;
    private UserStateService userStateService;

    @Autowired
    public AdminController(UserService userService,
                           AdminService adminService,
                           UserStateService userStateService) {
        this.userService = userService;
        this.adminService = adminService;
        this.userStateService = userStateService;
    }


    //Will be adding more funct. for permissions
    @RequestMapping(value = "users/{userUuid}/elevate-to-admin", method = RequestMethod.PUT)
    public InsiderUser setUserToAdmin(@PathVariable("userUuid") UUID userUuid) {
        if (userStateService.hasAdminPrivileges()){
        return adminService.elevateUserToAdmin(userUuid);
    }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "professors/{userUuid}/verify", method = RequestMethod.POST)
    public InsiderUser setUserToProfessor(@PathVariable("userUuid") UUID userUuid) {
        if (userStateService.hasAdminPrivileges()) {
            return adminService.validateProfessor(userUuid);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "professors/pending", method = RequestMethod.GET)
    public List<InsiderUser> getAllPendingProfs() {
        if (userStateService.hasAdminPrivileges()) {
            return userService.getPendingProfessors();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
