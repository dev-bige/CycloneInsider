package edu.cyclone.insider.controllers.comments;

import edu.cyclone.insider.controllers.comments.models.CommentCreateRequestModel;
import edu.cyclone.insider.models.Comment;
import edu.cyclone.insider.services.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("posts/{postUuid}/comments")
public class CommentsController {
    private final CommentsService commentsService;

    @Autowired
    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    /**
     * Get all the comments for a post
     *
     * @param postUuid the post uuid
     * @return a list of comments under the postUuid
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Comment> getAllComments(@PathVariable("postUuid") UUID postUuid) {
        return commentsService.getCommentsForPost(postUuid);
    }

    /**
     * Create a comment for a post
     *
     * @param postUuid the post uuid that you want to comment to
     * @return  request  the comment body model
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    private Comment commentToPost(@PathVariable("postUuid") UUID postUuid, @RequestBody CommentCreateRequestModel request) {
        return commentsService.createComment(request, postUuid);
    }

    /**
     * edit a comment that user created on post
     * @param commentUuid
     * @return updated comment
     */
    @RequestMapping(value = "{commentUuid}", method = RequestMethod.PUT)
    public Comment editComment(@PathVariable("commentUuid") UUID commentUuid, @RequestBody CommentCreateRequestModel request) {
        return commentsService.editComment(commentUuid, request);
    }

    /**
     * delete a comment that user created on post
     * @param commentUuid
     * @return updated comment
     */
    @RequestMapping(value = "{commentUuid}", method = RequestMethod.DELETE)
    public void deleteComment(@PathVariable("commentUuid") UUID commentUuid) {
        commentsService.deleteComment(commentUuid);
    }


}
