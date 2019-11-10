package edu.cyclone.insider.controllers.comments;

import edu.cyclone.insider.controllers.BaseController;
import edu.cyclone.insider.controllers.comments.models.CommentCreateRequestModel;
import edu.cyclone.insider.models.Comment;
import edu.cyclone.insider.models.Post;
import edu.cyclone.insider.repos.CommentsRepository;
import edu.cyclone.insider.repos.PostRepository;
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
@RequestMapping("posts/{postUuid}/comments")
public class CommentsController extends BaseController {
    private final CommentsRepository commentsRepository;
    private PostRepository postRepository;

    @Autowired
    public CommentsController(UsersRepository usersRepository, CommentsRepository commentsRepository, PostRepository postRepository) {
        super(usersRepository);
        this.commentsRepository = commentsRepository;
        this.postRepository = postRepository;
    }

    /**
     * Get all the comments for a post
     * @param postUuid the post uuid
     * @return a list of comments under the postUuid
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Comment> getAllComments(@PathVariable("postUuid") UUID postUuid) {
        return commentsRepository.getCommentsByPost(postUuid);
    }

    /**
     * Create a comment for a post
     * @param postUuid the post uuid that you want to comment to
     * @param request the comment body model
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    private Comment commentToPost(@PathVariable("postUuid") UUID postUuid, @RequestBody CommentCreateRequestModel request) {
        Optional<Post> post = postRepository.findById(postUuid);
        if (!post.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Comment comment = new Comment();
        comment.setComment(request.comment);
        comment.setUser(getCurrentUser());
        comment.setPost(post.get());
        comment.setDate(new Date());

        commentsRepository.save(comment);
        return comment;
    }


    @RequestMapping(value = "{commentUuid}/editComment", method = RequestMethod.PUT)
    public Comment edit_Comment(@PathVariable("commentUuid") UUID commentUuid, @RequestBody CommentCreateRequestModel request) {
        Optional<Comment> comment= commentsRepository.findById(commentUuid);
        if (!comment.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Comment editComment = comment.get();
        editComment.setComment(request.comment);


        editComment .setDate(new Date());

        editComment  = commentsRepository.save(editComment );

        return editComment ;
    }



}
