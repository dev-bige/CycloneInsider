package edu.cyclone.insider.controllers.room;

import edu.cyclone.insider.controllers.BaseController;
import edu.cyclone.insider.controllers.post.models.PostCreateRequestModel;
import edu.cyclone.insider.controllers.room.models.CreateRoomRequestModel;
import edu.cyclone.insider.models.InsiderUser;
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
        Optional<RoomMembership> membership = roomMembershipRepository.findMembership(getCurrentUser().getUuid(), room_uuid);
        if (membership.isPresent()) {
            RoomMembership roomMembership = membership.get();
            //If pending, we need to change it to not pending
            if (roomMembership.getPending()) {
                roomMembership.setPending(false);
                roomMembership = roomMembershipRepository.save(roomMembership);
            }
            //Return current membership if already exists...
            return roomMembership;
        }

        if (byId.get().getPrivateRoom()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
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

    @RequestMapping(value = "{roomUuid}", method = RequestMethod.GET)
    public Room getRoom(@PathVariable("roomUuid") UUID roomUuid) {
        membershipCheck(roomUuid);
        Optional<Room> byId = roomRepository.findById(roomUuid);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "{roomUuid}/posts", method = RequestMethod.POST)
    public Post postToRoom(@PathVariable("roomUuid") UUID roomUuid, @RequestBody PostCreateRequestModel request) {
        membershipCheck(roomUuid);
        return createPost(request, roomUuid);
    }

    @RequestMapping(value = "{roomUuid}/posts", method = RequestMethod.GET)
    public List<Post> getRoomPosts(@PathVariable("roomUuid") UUID roomUuid) {
        return postRepository.getPostsByRoom(roomUuid);
    }

    @RequestMapping(value = "{roomUuid}/invite", method = RequestMethod.POST)
    public RoomMembership invite(@RequestParam("userUuid") UUID userId, @PathVariable("roomUuid") UUID roomUuid) {
        if (!getCurrentUser().getAdmin() || !getCurrentUser().getProfessor()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        Optional<Room> room = roomRepository.findById(roomUuid);
        Optional<InsiderUser> user = usersRepository.findById(userId);

        if (!room.isPresent() || !user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        RoomMembership roomMembership = new RoomMembership();
        roomMembership.setPending(true);
        roomMembership.setUser(user.get());
        roomMembership.setRoom(room.get());
        roomMembership.setInvitedBy(getCurrentUser());
        return roomMembershipRepository.save(roomMembership);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Room createRoom(@RequestBody CreateRoomRequestModel model) {
        Room room = new Room();
        room.setName(model.name);
        room.setCreator(getCurrentUser());
        room.setPrivateRoom(model.privateRoom);
        room.setDescription(model.description);
        room = roomRepository.save(room);
        joinRoom(room.getUuid());
        return room;
    }

    @RequestMapping(value = "{roomUuid}", method = RequestMethod.DELETE)
    public void deleteRoom(@PathVariable("roomUuid") UUID roomUuid) {
        Optional<Room> room = roomRepository.findById(roomUuid);
        if (!room.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        UUID creatorUUID = room.get().getCreator().getUuid();
        if (!creatorUUID.equals(getCurrentUser().getUuid())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only the creator can delete the room");
        }
        roomRepository.deleteById(roomUuid);
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

    /**
     * Checks if user is part of the room, if not, we throw an exception
     *
     * @param roomUuid
     */
    public void membershipCheck(UUID roomUuid) {
        Optional<RoomMembership> membership = roomMembershipRepository.findMembership(getCurrentUser().getUuid(), roomUuid);
        if (!membership.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "This user is not part of this room");
        }
    }
}
