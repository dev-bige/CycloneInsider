/*
 *@author Andrew Dort
 *
 *
 */
package edu.cyclone.insider.controllers.post;

import edu.cyclone.insider.controllers.post.models.PostCreateRequestModel;
import edu.cyclone.insider.models.FavPost;
import edu.cyclone.insider.models.Post;
import edu.cyclone.insider.services.FavoritePostService;
import edu.cyclone.insider.services.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("posts")
public class PostController {
    private PostsService postsService;
    private FavoritePostService favoritePostService;

    @Autowired
    public PostController(PostsService postsService,
                          FavoritePostService favoritePostService) {
        this.postsService = postsService;
        this.favoritePostService = favoritePostService;
    }

    /**
     * @return a list of the front page posts
     */
    @RequestMapping(value = "front-page", method = RequestMethod.GET)
    public List<Post> getFrontPagePosts() {
        return postsService.getFrontPagePosts();
    }

    /**
     * Create a post for the front page
     *
     * @param request the post creation body
     * @return the post that was created
     */
    @RequestMapping(value = "front-page", method = RequestMethod.POST)
    public Post postFrontPagePost(@RequestBody PostCreateRequestModel request) {
        return postsService.createPost(request, null);
    }


    /**
     * Get post by uuid
     *
     * @param postUuid the post uuid
     * @return the post
     */
    @RequestMapping(value = "{postUuid}", method = RequestMethod.GET)
    public Post getPost(@PathVariable("postUuid") UUID postUuid) {
        return postsService.getPostById(postUuid);
    }


    /**
     * Delete a post. Only the original creator can delete the post
     *
     * @param postUuid the post uuid
     * @return the post that was deleted
     */
    @RequestMapping(value = "{postUuid}", method = RequestMethod.DELETE)
    public void deletePost(@PathVariable("postUuid") UUID postUuid) {
        postsService.deletePost(postUuid);
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
        return postsService.editPost(postUuid, request);
    }

    /**
     * Create a favorite post
     *
     * @param postUuid the post you want to favorite
     * @return the {@link FavPost} relationship model
     */
    @RequestMapping(value = "{postUuid}/favorite", method = RequestMethod.POST)
    public FavPost favorite_Post(@PathVariable("postUuid") UUID postUuid) {
        return favoritePostService.favoritePost(postUuid);
    }

    @RequestMapping(value = "{postUuid}/favorite", method = RequestMethod.DELETE)
    public void deleteFavoritePost(@PathVariable("postUuid") UUID postUuid) {
        favoritePostService.deleteFavoritePost(postUuid);
    }
}




