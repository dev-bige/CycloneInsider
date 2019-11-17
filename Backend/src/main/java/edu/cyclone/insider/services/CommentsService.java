package edu.cyclone.insider.services;

import edu.cyclone.insider.controllers.comments.models.CommentCreateRequestModel;
import edu.cyclone.insider.models.Comment;
import edu.cyclone.insider.models.Post;
import edu.cyclone.insider.repos.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentsService {
    private UserStateService userStateService;
    private PostsService postsService;
    private CommentsRepository commentsRepository;

    @Autowired
    public CommentsService(UserStateService userStateService,
                           PostsService postsService,
                           CommentsRepository commentsRepository) {
        this.userStateService = userStateService;
        this.postsService = postsService;
        this.commentsRepository = commentsRepository;
    }

    public Comment getCommentById(UUID commentId) {
        Optional<Comment> comment = commentsRepository.findById(commentId);
        if (comment.isPresent()) {
            return comment.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


    public List<Comment> getCommentsByUser() {
        return getCommentsByUser(userStateService.getCurrentUser().getUuid());
    }

    public List<Comment> getCommentsByUser(UUID userId) {
        return commentsRepository.findCommentsByUser(userId);
    }

    public List<Comment> getCommentsForPost(UUID postId) {
        postsService.getPostById(postId);
        return commentsRepository.getCommentsByPost(postId);
    }

    public Comment createComment(CommentCreateRequestModel request, UUID postUuid) {
        Post post = postsService.getPostById(postUuid);

        Comment comment = new Comment();
        comment.setComment(request.comment);
        comment.setUser(userStateService.getCurrentUser());
        comment.setPost(post);
        comment.setDate(new Date());

        commentsRepository.save(comment);
        return comment;
    }

    public Comment editComment(UUID commentUuid, CommentCreateRequestModel request) {
        Comment comment = getCommentById(commentUuid);
        comment.setComment(request.comment);
        comment.setDate(new Date());
        comment = commentsRepository.save(comment);
        return comment;
    }

}
