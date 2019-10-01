/*
 *@author Andrew Dort
 *
 *
 */
package edu.cyclone.insider.controllers.post;

import edu.cyclone.insider.controllers.BaseController;
import edu.cyclone.insider.controllers.post.models.PostCreateRequestModel;
import edu.cyclone.insider.models.Post;
import edu.cyclone.insider.repos.PostRepository;
import edu.cyclone.insider.repos.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("posts")
public class PostController extends BaseController {
    private PostRepository postRepository;

    @Autowired
    public PostController(PostRepository postRepository, UsersRepository usersRepository) {
        super(usersRepository);
        this.postRepository = postRepository;
    }

    @RequestMapping(value = "front-page", method = RequestMethod.GET)
    public List<Post> getFrontPagePosts() {
        return postRepository.getFrontPagePosts();
    }

    @RequestMapping(value = "front-page", method = RequestMethod.POST)
    public void postFrontPagePost(@RequestBody PostCreateRequestModel request) {
        Post post = new Post();
        post.setContent(request.content);
        post.setRoom(null);
        post.setUser(usersRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        post.setTags(request.tags);
        post.setTitle(request.title);
        postRepository.save(post);
    }

    @RequestMapping(value = "room/{roomUuid}/post", method = RequestMethod.POST)
    public Post postToRoom(@PathVariable("roomUuid") UUID roomUuid, @RequestBody PostCreateRequestModel model) {
        Post postRoom = new Post();
        postRoom.setRoom(null);//make null if not working
        postRoom.setContent(model.content);
        postRoom = postRepository.save(postRoom);
        return postRoom;
    }
}



