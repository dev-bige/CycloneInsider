package edu.cyclone.insider.services;

import edu.cyclone.insider.models.Post;
import edu.cyclone.insider.repos.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostsService {
    private UserStateService userStateService;
    private PostRepository postRepository;

    @Autowired
    public PostsService(UserStateService userStateService, PostRepository postRepository) {
        this.userStateService = userStateService;
        this.postRepository = postRepository;
    }
    public List<Post> getPostsByUser() {
        return getPostsByUser(userStateService.getCurrentUser().getUuid());
    }

    public List<Post> getPostsByUser(UUID userUuid) {
        return postRepository.findPostsByUser(userUuid);
    }
}
