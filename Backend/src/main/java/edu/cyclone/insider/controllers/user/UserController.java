package edu.cyclone.insider.controllers.user;

import edu.cyclone.insider.controllers.user.models.SignUpRequestModel;
import edu.cyclone.insider.models.*;
import edu.cyclone.insider.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("users")
public class UserController {
    private UserService userService;
    private UserStateService userStateService;
    private RoomMembershipService roomMembershipService;
    private FavoritePostService favoritePostService;
    private PostsService postsService;
    private CommentsService commentsService;

    @Autowired
    public UserController(UserService userService,
                          UserStateService userStateService,
                          RoomMembershipService roomMembershipService,
                          FavoritePostService favoritePostService,
                          PostsService postsService,
                          CommentsService commentsService) {
        this.userService = userService;
        this.userStateService = userStateService;
        this.roomMembershipService = roomMembershipService;
        this.favoritePostService = favoritePostService;
        this.postsService = postsService;
        this.commentsService = commentsService;
    }
    /**
     * get user membershipts
     *
     * @return memberships
     */
    @RequestMapping(value = "memberships", method = RequestMethod.GET)
    public List<RoomMembership> getUserMemberships() {
        return roomMembershipService.getMemberships();
    }
    /**
     * get pending memberships to a room
     * @return list of pending memberships
     */
    @RequestMapping(value = "memberships/pending", method = RequestMethod.GET)
    public List<RoomMembership> getPendingMemberships() {
        return roomMembershipService.getPendingMemberships();
    }
    /**
     * favorite posts
     * @return all posts that the user has saved in favorites
     */
    @RequestMapping(value = "current/favorite-posts", method = RequestMethod.GET)
    public List<FavPost> getFavPosts() {
        return favoritePostService.getFavPosts();
    }
    /**
     * get comments by user
     * @return comments by current logged in user
     */
    @RequestMapping(value = "current/users-comments", method = RequestMethod.GET)
    public List<Comment> getMyComments() {
        return commentsService.getCommentsByUser();
    }
    /**
     *get posts
     * @return all posts by logged in user
     */
    @RequestMapping(value = "current/users-posts", method = RequestMethod.GET)
    public List<Post> getMyPosts() {
        return postsService.getPostsByUser();
    }
    /**
     * get user
     * @param username
     * @return user
     */
    @RequestMapping(value = "{username}/profile", method = RequestMethod.GET)
    public InsiderUser getUser(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }
    /**
     * get the current user
     * @return the user that is logged in currently
     */
    @RequestMapping(value = "current", method = RequestMethod.GET)
    public InsiderUser current() {
        return userStateService.getCurrentUser();
    }
    /**
     * sign up
     * @return signed up user request
     */
    @RequestMapping(value = "sign-up", method = RequestMethod.POST)
    public InsiderUser signUp(@RequestBody SignUpRequestModel request) {
        return userService.signUp(request);
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public List<InsiderUser> getAllUsers(@RequestParam(value = "roomId", required = false) UUID roomId) {
        if(roomId != null) {
            return roomMembershipService.findAllUsersInRoom(roomId);
        } else {
            return userService.getAllUsers();
        }
    }
}






