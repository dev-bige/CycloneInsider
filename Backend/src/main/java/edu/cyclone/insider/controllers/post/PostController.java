/*
 *@author Andrew Dort
 *
 *
 */
package edu.cyclone.insider.controllers.post;

import edu.cyclone.insider.controllers.BaseController;
import edu.cyclone.insider.controllers.post.models.PostCreateRequestModel;
import edu.cyclone.insider.models.FavPost;
import edu.cyclone.insider.models.Post;
import edu.cyclone.insider.models.Room;
import edu.cyclone.insider.repos.PostRepository;
import edu.cyclone.insider.repos.RoomRepository;
import edu.cyclone.insider.repos.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("posts")
public class PostController extends BaseController {
    private PostRepository postRepository;
    private RoomRepository roomRepository;

    @Autowired
    public PostController(PostRepository postRepository, UsersRepository usersRepository, RoomRepository roomRepository) {
        super(usersRepository);
        this.postRepository = postRepository;
        this.roomRepository = roomRepository;
    }

    @RequestMapping(value = "front-page", method = RequestMethod.GET)
    public List<Post> getFrontPagePosts() {
        return postRepository.getFrontPagePosts();
    }

    @RequestMapping(value = "front-page", method = RequestMethod.POST)
    public Post postFrontPagePost(@RequestBody PostCreateRequestModel request) {
        return createPost(request, null);
    }



    @RequestMapping(value = "{postUuid}", method = RequestMethod.GET)
    public Post getPost(@PathVariable("postUuid") UUID postUuid) {
        Optional<Post> post = postRepository.findById(postUuid);
        if (!post.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return post.get();
    }


    @RequestMapping(value = "{favPost/{postUuid}", method = RequestMethod.POST)
    public FavPost favorite_Post(@PathVariable("postUuid") UUID postUuid) {
        Optional<Post> post = postRepository.findById(postUuid);
        if (!post.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        FavPost favPost = new FavPost();
        favPost.setUser(getCurrentUser());
        favPost.setPost(post.get());


        return favPost;
    }

    @RequestMapping(value = "favPost/{postUuid}", method = RequestMethod.GET)
    public List<Post> getFavPosts(@PathVariable("postUuid") UUID postUuid) {
        return postRepository.getPostsByRoom(postUuid);
    }


    private Post createPost(@RequestBody PostCreateRequestModel request, UUID roomUUid) {
        Optional<Room> byId = roomRepository.findById(roomUUid);
        if (!byId.isPresent() && roomUUid != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Post post = new Post();
        post.setContent(request.content);
        post.setRoom(roomUUid == null ? null : byId.get());
        post.setUser(getCurrentUser());
        post.setTags(request.tags);
        post.setTitle(request.title);
        post.setDate(new Date());
        post = postRepository.save(post);
        return post;
    }
}



