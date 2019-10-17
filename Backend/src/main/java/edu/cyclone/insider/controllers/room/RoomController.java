package edu.cyclone.insider.controllers.room;

import edu.cyclone.insider.controllers.BaseController;
import edu.cyclone.insider.controllers.post.models.PostCreateRequestModel;
import edu.cyclone.insider.controllers.room.models.CreateRoomRequestModel;
import edu.cyclone.insider.models.Post;
import edu.cyclone.insider.models.Room;
import edu.cyclone.insider.models.RoomMembership;
import edu.cyclone.insider.repos.PostRepository;
import edu.cyclone.insider.repos.RoomMembershipRepository;
import edu.cyclone.insider.repos.RoomRepository;
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
@RequestMapping("rooms")
public class RoomController extends BaseController {
    private final RoomRepository roomRepository;
    private final RoomMembershipRepository roomMembershipRepository;
    private final PostRepository postRepository;

    @Autowired
    public RoomController(UsersRepository usersRepository, RoomRepository roomRepository, RoomMembershipRepository roomMembershipRepository, PostRepository postRepository) {
        super(usersRepository);
        this.roomRepository = roomRepository;
        this.roomMembershipRepository = roomMembershipRepository;
        this.postRepository = postRepository;
    }


    @RequestMapping(value = "{uuid}/join", method = RequestMethod.POST)
    public RoomMembership joinRoom(@PathVariable("uuid") UUID room_uuid) {
        Optional<Room> byId = roomRepository.findById(room_uuid);
        if (!byId.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //Check if membership already exists
        RoomMembership membership = roomMembershipRepository.findMembership(getCurrentUser().getUuid(), room_uuid);
        if (membership != null) {
            //Return current membership if already exists...
            return membership;
        }

        RoomMembership roomMembership = new RoomMembership();
        roomMembership.setRoom(byId.get());
        roomMembership.setUser(getCurrentUser());
        roomMembership = roomMembershipRepository.save(roomMembership);
        return roomMembership;
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @RequestMapping(value = "{uuid}", method = RequestMethod.GET)
    public Room getRoom(@PathVariable("uuid") UUID uuid) {
        Optional<Room> byId = roomRepository.findById(uuid);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "{roomUuid}/posts", method = RequestMethod.POST)
    public Post postToRoom(@PathVariable("roomUuid") UUID roomUuid, @RequestBody PostCreateRequestModel request) {
        return createPost(request, roomUuid);
    }

    @RequestMapping(value = "{roomUuid}/posts", method = RequestMethod.GET)
    public List<Post> getRoomPosts(@PathVariable("roomUuid") UUID roomUuid) {
        return postRepository.getPostsByRoom(roomUuid);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Room createRoom(@RequestBody CreateRoomRequestModel model) {
        Room room = new Room();
        room.setName(model.name);
        room.setDescription(model.description);
        room = roomRepository.save(room);
        return room;
    }

    private Post createPost(@RequestBody PostCreateRequestModel request, UUID roomUUid) {
        Optional<Room> byId = null;
        if (roomUUid != null) {
            byId = roomRepository.findById(roomUUid);
            if (!byId.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
        Post post = new Post();
        post.setContent(request.content);
         post.setRoom(roomUUid == null ? null : byId.get());
        post.setUser(getCurrentUser());
        post.setTags(request.tags);
        post.setTitle(request.title);
        post.setDate(new Date());
        post = postRepository.save(post);
        return post;
    }
}
