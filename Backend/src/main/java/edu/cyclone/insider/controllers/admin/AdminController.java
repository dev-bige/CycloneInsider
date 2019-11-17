package edu.cyclone.insider.controllers.admin;

import edu.cyclone.insider.models.InsiderUser;
import edu.cyclone.insider.services.AdminService;
import edu.cyclone.insider.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("admin")
public class AdminController {
    private UserService userService;
    private AdminService adminService;

    @Autowired
    public AdminController(UserService userService,
                           AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }


    //Will be adding more funct. for permissions
    @RequestMapping(value = "users/{userUuid}/elevate-to-admin", method = RequestMethod.PUT)
    public InsiderUser setUserToAdmin(@PathVariable("userUuid") UUID userUuid) {
        return adminService.elevateUserToAdmin(userUuid);
    }

    @RequestMapping(value = "professors/{userUuid}/verify", method = RequestMethod.POST)
    public InsiderUser setUserToProfessor(@PathVariable("userUuid") UUID userUuid) {
        return adminService.validateProfessor(userUuid);
    }


    @RequestMapping(value = "professors/pending", method = RequestMethod.GET)
    public List<InsiderUser> getAllPendingProfs() {
        return userService.getPendingProfessors();
    }
}
