package edu.cyclone.insider.controllers.comments;
import edu.cyclone.insider.controllers.BaseController;
import edu.cyclone.insider.controllers.comments.models.CommentCreateRequestModel;
import edu.cyclone.insider.controllers.post.models.PostCreateRequestModel;
import edu.cyclone.insider.models.Comments;
import edu.cyclone.insider.models.Post;
import edu.cyclone.insider.repos.CommentsRepository;
import edu.cyclone.insider.repos.PostRepository;
import edu.cyclone.insider.repos.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("Comments")
public class CommentsController extends BaseController  {
    private final CommentsRepository commentsRepository;
    private PostRepository postRepository;

    @Autowired
    public CommentsController(UsersRepository usersRepository, CommentsRepository commentsRepository, PostRepository postRepository) {
        super(usersRepository);
        this.commentsRepository = commentsRepository;
        this.postRepository = postRepository;
    }


    @RequestMapping(value = "allComments", method = RequestMethod.GET)
    public List<Post> getAllComments()  {
        return commentsRepository.findAll();
    }
    @RequestMapping(value = "{uuid}", method = RequestMethod.POST)
    public void postFrontPagePost(@PathVariable("uuid") UUID postUuid, @RequestBody CommentCreateRequestModel request) {
        Optional<Post> post = postRepository.findById(postUuid);
        if(!post.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }


        Comments comment = new Comments();
        comment.setComment(request.comment);
        comment.setUser(getCurrentUser());
        comment.setPost(post.get());

commentsRepository.save(comment);
    }


}
