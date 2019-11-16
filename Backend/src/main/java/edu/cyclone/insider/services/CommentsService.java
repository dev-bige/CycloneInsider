package edu.cyclone.insider.services;

import edu.cyclone.insider.models.Comment;
import edu.cyclone.insider.repos.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentsService {
    private UserStateService userStateService;
    private CommentsRepository commentsRepository;

    @Autowired
    public CommentsService(UserStateService userStateService, CommentsRepository commentsRepository) {
        this.userStateService = userStateService;
        this.commentsRepository = commentsRepository;
    }

    public List<Comment> getCommentsByUser() {
        return getCommentsByUser(userStateService.getCurrentUser().getUuid());
    }

    public List<Comment> getCommentsByUser(UUID userId) {
        return commentsRepository.findCommentsByUser(userId);
    }
}
