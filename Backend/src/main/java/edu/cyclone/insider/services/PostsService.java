package edu.cyclone.insider.services;

import edu.cyclone.insider.controllers.post.models.PostCreateRequestModel;
import edu.cyclone.insider.models.Post;
import edu.cyclone.insider.models.Room;
import edu.cyclone.insider.repos.PostRepository;
import jdk.internal.jline.internal.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostsService {
    private UserStateService userStateService;
    private PostRepository postRepository;
    private RoomService roomService;
    private RoomMembershipService roomMembershipService;

    @Autowired
    public PostsService(UserStateService userStateService,
                        PostRepository postRepository,
                        RoomService roomService,
                        RoomMembershipService roomMembershipService) {
        this.userStateService = userStateService;
        this.postRepository = postRepository;
        this.roomService = roomService;
        this.roomMembershipService = roomMembershipService;
    }

    public List<Post> getPostsByUser() {
        return getPostsByUser(userStateService.getCurrentUser().getUuid());
    }

    public List<Post> getPostsByUser(UUID userUuid) {
        return postRepository.findPostsByUser(userUuid);
    }

    public List<Post> getPostsByRoom(UUID roomId) {
        return postRepository.getPostsByRoom(roomId);
    }

    public Post createPost(@RequestBody PostCreateRequestModel request, @Nullable UUID roomUUid) {
        Optional<Room> byId = null;
        if (roomUUid != null) {
            byId = roomService.getByUUIDOptional(roomUUid);
            if (!byId.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
        Post post = new Post();
        post.setContent(request.content);
        post.setRoom(roomUUid == null ? null : byId.get());
        post.setUser(userStateService.getCurrentUser());
        post.setTags(request.tags);
        post.setTitle(request.title);
        post.setDate(new Date());
        post = postRepository.save(post);
        return post;
    }
}
