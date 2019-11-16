package edu.cyclone.insider.services;

import edu.cyclone.insider.models.FavPost;
import edu.cyclone.insider.models.Post;
import edu.cyclone.insider.repos.FavPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FavoritePostService {
    private UserStateService userStateService;
    private FavPostRepository favPostRepository;

    @Autowired
    public FavoritePostService(UserStateService userStateService, FavPostRepository favPostRepository) {
        this.userStateService = userStateService;
        this.favPostRepository = favPostRepository;
    }

    public List<Post> getFavPosts() {
        return getFavPosts(userStateService.getCurrentUser().getUuid());
    }

    public List<Post> getFavPosts(UUID userId) {
        return favPostRepository
                .findFavByUser(userId)
                .stream()
                .map(FavPost::getPost)
                .collect(Collectors.toList());
    }
}
