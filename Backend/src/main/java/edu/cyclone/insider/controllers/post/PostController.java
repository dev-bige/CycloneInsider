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
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    public Post postFrontPagePost(@RequestBody PostCreateRequestModel request) {
        return createPost(request);
    }

    @RequestMapping(value = "{roomUuid}", method = RequestMethod.POST)
    public Post postToRoom(@PathVariable("roomUuid") UUID roomUuid, @RequestBody PostCreateRequestModel request) {
        return createPost(request);
    }

    private Post createPost(@RequestBody PostCreateRequestModel request) {
        Post post = new Post();
        post.setContent(request.content);
        post.setRoom(null);
        post.setUser(getCurrentUser());
        post.setTags(request.tags);
        post.setTitle(request.title);
        post.setDate(new Date());
        post = postRepository.save(post);
        return post;
    }

    @RequestMapping(value = "{roomUuid}", method = RequestMethod.GET)
    public List<Post> getRoomPosts(@PathVariable("roomUuid") UUID roomUuid) {
        return postRepository.getPostsByRoom(roomUuid);
    }
}



