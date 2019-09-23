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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}



