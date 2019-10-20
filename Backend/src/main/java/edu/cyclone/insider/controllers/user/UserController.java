package edu.cyclone.insider.controllers.user;

import edu.cyclone.insider.controllers.BaseController;
import edu.cyclone.insider.controllers.user.models.SignUpRequestModel;
import edu.cyclone.insider.models.FavPost;
import edu.cyclone.insider.models.InsiderUser;
import edu.cyclone.insider.models.RoomMembership;
import edu.cyclone.insider.repos.FavPostRepository;
import edu.cyclone.insider.repos.RoomMembershipRepository;
import edu.cyclone.insider.repos.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("users")
public class UserController extends BaseController {

    private PasswordEncoder passwordEncoder;
    private RoomMembershipRepository roomMembershipRepository;
    private FavPostRepository favPostRepository;

    @Autowired
    public UserController(UsersRepository usersRepository, PasswordEncoder passwordEncoder, RoomMembershipRepository roomMembershipRepository, FavPostRepository favPostRepository) {
        super(usersRepository);
        this.passwordEncoder = passwordEncoder;
        this.roomMembershipRepository = roomMembershipRepository;
        this.favPostRepository = favPostRepository;
    }

    @RequestMapping(value = "memberships", method = RequestMethod.GET)
    public List<RoomMembership> getUserMemberships() {
        return roomMembershipRepository.findUserMemberships(getCurrentUser().getUuid());
    }


    @RequestMapping(value = "current/favorite-posts", method = RequestMethod.GET)
    public Optional<FavPost> getFavPosts() {
        return favPostRepository.findFavByUser(getCurrentUser().getUuid());

    }

        @RequestMapping(value = "current", method = RequestMethod.GET)
    public InsiderUser current() {
        return getCurrentUser();
    }

        @RequestMapping(value = "sign-up", method = RequestMethod.POST)
    public void signUp(@RequestBody SignUpRequestModel request) {
        InsiderUser userByUsername = usersRepository.findUserByUsername(request.username);

        if (userByUsername != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        InsiderUser newUser = new InsiderUser();
        newUser.setFirstName(request.firstName);
        newUser.setLastName(request.lastName);
        newUser.setPassword(passwordEncoder.encode(request.password));
        newUser.setUsername(request.username);
        usersRepository.save(newUser);
    }
}
