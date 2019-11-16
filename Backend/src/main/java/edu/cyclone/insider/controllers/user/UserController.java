package edu.cyclone.insider.controllers.user;
import edu.cyclone.insider.models.UserLevel;
import edu.cyclone.insider.controllers.BaseController;
import edu.cyclone.insider.controllers.user.models.SignUpRequestModel;
import edu.cyclone.insider.models.*;
import edu.cyclone.insider.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController()
@RequestMapping("users")
public class UserController extends BaseController {

    private PasswordEncoder passwordEncoder;
    private RoomMembershipRepository roomMembershipRepository;
    private FavPostRepository favPostRepository;
    private CommentsRepository commentsRepository;
    private PostRepository postRepository;
    private UserLevel userLevel;

    @Autowired
    public UserController(UsersRepository usersRepository, PasswordEncoder passwordEncoder, RoomMembershipRepository roomMembershipRepository, FavPostRepository favPostRepository, CommentsRepository commentsRepository, PostRepository postRepository) {
        super(usersRepository);
        this.passwordEncoder = passwordEncoder;
        this.roomMembershipRepository = roomMembershipRepository;
        this.favPostRepository = favPostRepository;
        this.commentsRepository = commentsRepository;
        this.postRepository = postRepository;
    }

    @RequestMapping(value = "memberships", method = RequestMethod.GET)
    public List<RoomMembership> getUserMemberships() {
        return roomMembershipRepository.findUserMemberships(getCurrentUser().getUuid());
    }

    @RequestMapping(value = "memberships/pending", method = RequestMethod.GET)
    public List<RoomMembership> getPendingMemberships() {
        return roomMembershipRepository.findPendingUserMemberships(getCurrentUser().getUuid());
    }

    @RequestMapping(value = "current/favorite-posts", method = RequestMethod.GET)
    public List<FavPost> getFavPosts() {
        return favPostRepository.findFavByUser(getCurrentUser().getUuid());
    }

    @RequestMapping(value = "current/users-comments", method = RequestMethod.GET)
    public List<Comment> getMyComments() {
        return commentsRepository.findCommentsByUser(getCurrentUser().getUuid());
    }

    @RequestMapping(value = "current/users-posts", method = RequestMethod.GET)
    public List<Post> getMyPosts() {
        return postRepository.findPostsByUser(getCurrentUser().getUuid());
    }


    @RequestMapping(value = "{username}/profile", method = RequestMethod.GET)
    public InsiderUser getUser(@PathVariable("username") String username) {
        InsiderUser userByUsername = usersRepository.findUserByUsername(username);
        if (userByUsername == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return userByUsername;
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
        if(newUser.getProfPending()) {
            userLevel = (UserLevel.USER);
        }
        usersRepository.save(newUser);


    }








    }






