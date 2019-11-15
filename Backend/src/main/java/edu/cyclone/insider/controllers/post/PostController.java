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
import edu.cyclone.insider.repos.FavPostRepository;
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
    private FavPostRepository favPostRepository;

    @Autowired
    public PostController(PostRepository postRepository, UsersRepository usersRepository, RoomRepository roomRepository, FavPostRepository favPostRepository) {
        super(usersRepository);
        this.postRepository = postRepository;
        this.roomRepository = roomRepository;
        this.favPostRepository = favPostRepository;
    }

    /**
     * @return a list of the front page posts
     */
    @RequestMapping(value = "front-page", method = RequestMethod.GET)
    public List<Post> getFrontPagePosts() {
        return postRepository.getFrontPagePosts();
    }

    /**
     * Create a post for the front page
     *
     * @param request the post creation body
     * @return the post that was created
     */
    @RequestMapping(value = "front-page", method = RequestMethod.POST)
    public Post postFrontPagePost(@RequestBody PostCreateRequestModel request) {
        return createPost(request, null);
    }


    /**
     * Get post by uuid
     *
     * @param postUuid the post uuid
     * @return the post
     */
    @RequestMapping(value = "{postUuid}", method = RequestMethod.GET)
    public Post getPost(@PathVariable("postUuid") UUID postUuid) {
        Optional<Post> post = postRepository.findById(postUuid);
        if (!post.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return post.get();
    }


    /**
     * Delete a post. Only the original creator can delete the post
     *
     * @param postUuid the post uuid
     * @return the post that was deleted
     */
    @RequestMapping(value = "{postUuid}", method = RequestMethod.DELETE)
    public Post deletePost(@PathVariable("postUuid") UUID postUuid) {
        Optional<Post> post = postRepository.findById(postUuid);
        if (!post.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!post.get().getUser().getUuid().toString()
                .equals(getCurrentUser().getUuid().toString())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        postRepository.delete(post.get());
        return post.get();
    }

    /**
     * Edit a post
     *
     * @param postUuid the post uuid you want to edit
     * @param request  the update model
     * @return the post that was modified
     */
    @RequestMapping(value = "{postUuid}/editPost", method = RequestMethod.PUT)
    public Post edit_Post(@PathVariable("postUuid") UUID postUuid, @RequestBody PostCreateRequestModel request) {
        Optional<Post> post = postRepository.findById(postUuid);
        if (!post.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Post editPost = post.get();
        editPost.setContent(request.content);


        editPost.setDate(new Date());

        editPost = postRepository.save(editPost);

        return editPost;
    }


    /**
     * Create a favorite post
     *
     * @param postUuid the post you want to favorite
     * @return the {@link FavPost} relationship model
     */
    @RequestMapping(value = "{postUuid}/favorite", method = RequestMethod.POST)
    public FavPost favorite_Post(@PathVariable("postUuid") UUID postUuid) {
        Optional<FavPost> maybeFavPost = this.favPostRepository.findFavPost(getCurrentUser().getUuid(), postUuid);
        if (maybeFavPost.isPresent()) {
            return maybeFavPost.get();
        }

        Optional<Post> post = postRepository.findById(postUuid);
        if (!post.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        FavPost favPost = new FavPost();
        favPost.setPost(post.get());
        favPost.setUser(getCurrentUser());
        favPost.setDate(new Date());
        favPost = favPostRepository.save(favPost);
        return favPost;
    }

    @RequestMapping(value = "{postUuid}/favorite", method = RequestMethod.DELETE)
    public void deleteFavoritePost(@PathVariable("postUuid") UUID postUuid) {
        Optional<FavPost> maybeFavPost = this.favPostRepository.findFavPost(getCurrentUser().getUuid(), postUuid);
        maybeFavPost.ifPresent(favPost -> favPostRepository.delete(favPost));
    }

    private Post createPost(PostCreateRequestModel request, UUID roomUUid) {
        Optional<Room> byId = null;
        if (roomUUid != null) {
            byId = roomRepository.findById(roomUUid);
            if (!byId.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
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




