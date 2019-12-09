package edu.cyclone.insider.controllers.admin;

import edu.cyclone.insider.models.InsiderUser;
import edu.cyclone.insider.services.AdminService;
import edu.cyclone.insider.services.RoomMembershipService;
import edu.cyclone.insider.services.UserService;
import edu.cyclone.insider.services.UserStateService;
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
    private UserStateService userStateService;
    private RoomMembershipService roomMembershipService;

    @Autowired
    public AdminController(UserService userService,
                           AdminService adminService,
                           UserStateService userStateService) {
        this.userService = userService;
        this.adminService = adminService;
        this.userStateService = userStateService;
        this.roomMembershipService= roomMembershipService;
    }

    /**
     * Sets a user to Admin level
     * @param userUuid
     * @return updated service with specified user now as Admin
     */
    @RequestMapping(value = "users/{userUuid}/elevate-to-admin", method = RequestMethod.PUT)
    public InsiderUser setUserToAdmin(@PathVariable("userUuid") UUID userUuid) {
        return adminService.elevateUserToAdmin(userUuid);
    }
    /**
     * Sets a user to Professor level
     * @param userUuid
     * @return user now is validated and set to professor level
     */
    @RequestMapping(value = "professors/{userUuid}/verify", method = RequestMethod.POST)
    public InsiderUser setUserToProfessor(@PathVariable("userUuid") UUID userUuid) {

        return adminService.validateProfessor(userUuid);


    }
    /**
     * get pending professors
     * @return list of all pending professors on application
     */
    @RequestMapping(value = "professors/pending", method = RequestMethod.GET)
    public List<InsiderUser> getAllPendingProfs() {
        return userService.getPendingProfessors();
    }


    @RequestMapping(value = "{userUuid}/{roomUuid}/kick", method = RequestMethod.DELETE)
    public void kickUser(@PathVariable("userUuid") UUID userUuid,@PathVariable("roomUuid") UUID room_uuid) {
        roomMembershipService.banUserFromRoom(userUuid,room_uuid);
    }
}



