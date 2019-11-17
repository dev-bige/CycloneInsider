package edu.cyclone.insider.controllers.user;

import edu.cyclone.insider.controllers.user.models.SignUpRequestModel;
import edu.cyclone.insider.models.*;
import edu.cyclone.insider.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(value = "memberships", method = RequestMethod.GET)
    public List<RoomMembership> getUserMemberships() {
        return roomMembershipService.getMemberships();
    }

    @RequestMapping(value = "memberships/pending", method = RequestMethod.GET)
    public List<RoomMembership> getPendingMemberships() {
        return roomMembershipService.getPendingMemberships();
    }

    @RequestMapping(value = "current/favorite-posts", method = RequestMethod.GET)
    public List<FavPost> getFavPosts() {
        return favoritePostService.getFavPosts();
    }

    @RequestMapping(value = "current/users-comments", method = RequestMethod.GET)
    public List<Comment> getMyComments() {
        return commentsService.getCommentsByUser();
    }

    @RequestMapping(value = "current/users-posts", method = RequestMethod.GET)
    public List<Post> getMyPosts() {
        return postsService.getPostsByUser();
    }

    @RequestMapping(value = "{username}/profile", method = RequestMethod.GET)
    public InsiderUser getUser(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @RequestMapping(value = "current", method = RequestMethod.GET)
    public InsiderUser current() {
        return userStateService.getCurrentUser();
    }

    @RequestMapping(value = "sign-up", method = RequestMethod.POST)
    public InsiderUser signUp(@RequestBody SignUpRequestModel request) {
        return userService.signUp(request);
    }
}






