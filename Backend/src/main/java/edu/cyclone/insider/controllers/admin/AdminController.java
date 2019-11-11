package edu.cyclone.insider.controllers.admin;

import edu.cyclone.insider.controllers.BaseController;
import edu.cyclone.insider.models.InsiderUser;
import edu.cyclone.insider.models.Room;
import edu.cyclone.insider.models.RoomMembership;
import edu.cyclone.insider.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("admin")
public class AdminController extends BaseController {
    private final RoomRepository roomRepository;
    private final CommentsRepository commentsRepository;
    private final PostRepository postRepository;
    private final RoomMembershipRepository roomMembershipRepository;

    @Autowired
    public AdminController(UsersRepository usersRepository,RoomMembershipRepository roomMembershipRepository, RoomRepository roomRepository, CommentsRepository commentsRepository, PostRepository postRepository) {
        super(usersRepository);
        this.commentsRepository = commentsRepository;
        this.postRepository = postRepository;
        this.roomRepository = roomRepository;
        this.roomMembershipRepository=roomMembershipRepository;

    }


    //Will be adding more funct. for permissions
    @RequestMapping(value = "{userUuid}/acceptAdminReq", method = RequestMethod.GET)
    public InsiderUser setUserToAdmin(@RequestParam("roomUuid") UUID roomUuid, @PathVariable("userUuid") UUID userUuid) {
        Optional<RoomMembership> membership = roomMembershipRepository.findMembership(getCurrentUser().getUuid(), roomUuid);
        Optional<InsiderUser> user = usersRepository.findById(userUuid);
        Optional<Room> room = roomRepository.findById(roomUuid);

        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Room roomReq = room.get();
        InsiderUser userReq = user.get();

        //checks to see if user is a admin yet and that they are a member of the room they will become admin in
        if (userReq.getAdmin() == Boolean.FALSE && membership.isPresent()) {
            userReq.setAdmin(Boolean.TRUE);
        }
        return userReq;
    }


}
