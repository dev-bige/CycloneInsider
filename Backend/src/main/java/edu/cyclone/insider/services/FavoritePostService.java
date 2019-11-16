package edu.cyclone.insider.services;

import edu.cyclone.insider.models.FavPost;
import edu.cyclone.insider.models.Post;
import edu.cyclone.insider.models.RoomMembership;
import edu.cyclone.insider.repos.FavPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FavoritePostService {
    private UserStateService userStateService;
    private FavPostRepository favPostRepository;
    private RoomMembershipService roomMembershipService;
    private PostsService postsService;

    @Autowired
    public FavoritePostService(UserStateService userStateService,
                               FavPostRepository favPostRepository,
                               RoomMembershipService roomMembershipService,
                               PostsService postsService) {
        this.userStateService = userStateService;
        this.favPostRepository = favPostRepository;
        this.roomMembershipService = roomMembershipService;
        this.postsService = postsService;
    }

    public List<FavPost> getFavPosts() {
        return getFavPosts(userStateService.getCurrentUser().getUuid());
    }

    public List<FavPost> getFavPosts(UUID userId) {
        return favPostRepository.findFavByUser(userId);
    }

    public FavPost favoritePost(UUID postId) {
        Optional<FavPost> maybeFavPost = this.favPostRepository.findFavPost(userStateService.getCurrentUser().getUuid(), postId);
        if (maybeFavPost.isPresent()) {
            return maybeFavPost.get();
        }

        Post post = postsService.getPostById(postId);
        //Throws exception if we aren't part of this room
        roomMembershipService.getMembership(post.getRoom().getUuid());


        FavPost favPost = new FavPost();
        favPost.setPost(post);
        favPost.setUser(userStateService.getCurrentUser());
        favPost.setDate(new Date());
        favPost = favPostRepository.save(favPost);
        return favPost;
    }

    public void deleteFavoritePost(UUID postId) {
        Optional<FavPost> maybeFavPost = this.favPostRepository.findFavPost(userStateService.getCurrentUser().getUuid(), postId);
        maybeFavPost.ifPresent(favPost -> favPostRepository.delete(favPost));
    }
}
