package edu.cyclone.insider.services;

import edu.cyclone.insider.controllers.post.models.PostCreateRequestModel;
import edu.cyclone.insider.models.Post;
import edu.cyclone.insider.models.Room;
import edu.cyclone.insider.repos.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
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
        return postRepository.getPostsByUser(userUuid);
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

    public Post getPostById(UUID postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            return post.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public Post editPost(UUID postUuid, PostCreateRequestModel request) {
        Optional<Post> post = postRepository.findById(postUuid);
        if (!post.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Post editPost = post.get();
        editPost.setContent(request.content);
        editPost.setTitle(request.title);
        editPost.setDate(new Date());

        editPost = postRepository.save(editPost);

        return editPost;
    }

    public void deletePost(UUID postUuid) {
        Post post = getPostById(postUuid);

        //Can delete a post if you are admin, creator of the room, or moderator of the room
        boolean canDelete = userStateService.hasAdminPrivileges() || post.getUser().getUuid().equals(userStateService.getCurrentUser().getUuid());
        if (post.getRoom() != null) {
            canDelete = canDelete || roomMembershipService.hasCreatorPrivileges(post.getRoom().getUuid()) || roomMembershipService.hasModeratorPrivileges(post.getRoom().getUuid());
        }

        if (canDelete) {
            postRepository.delete(post);
            return;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    public List<Post> getFrontPagePosts() {
        return postRepository.getFrontPagePosts();
    }
}
