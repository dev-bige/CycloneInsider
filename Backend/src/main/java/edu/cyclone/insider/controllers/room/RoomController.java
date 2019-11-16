package edu.cyclone.insider.controllers.room;

import edu.cyclone.insider.controllers.post.models.PostCreateRequestModel;
import edu.cyclone.insider.controllers.room.models.CreateRoomRequestModel;
import edu.cyclone.insider.models.Post;
import edu.cyclone.insider.models.Room;
import edu.cyclone.insider.models.RoomLevel;
import edu.cyclone.insider.models.RoomMembership;
import edu.cyclone.insider.services.PostsService;
import edu.cyclone.insider.services.RoomMembershipService;
import edu.cyclone.insider.services.RoomService;
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
public class RoomController {
    private RoomMembershipService roomMembershipService;
    private RoomService roomService;
    private PostsService postsService;

    @Autowired
    public RoomController(RoomMembershipService roomMembershipService,
                          RoomService roomService,
                          PostsService postsService) {
        this.roomMembershipService = roomMembershipService;
        this.roomService = roomService;
        this.postsService = postsService;
    }


    @RequestMapping(value = "{uuid}/join", method = RequestMethod.POST)
    public RoomMembership joinRoom(@PathVariable("uuid") UUID room_uuid) {
        return roomMembershipService.joinRoom(room_uuid);
    }

    @RequestMapping(value = "public", method = RequestMethod.GET)
    public List<Room> getPublicRooms() {
        return roomService.getPublicRooms();
    }

    @RequestMapping(value = "{roomUuid}", method = RequestMethod.GET)
    public Room getRoom(@PathVariable("roomUuid") UUID roomUuid) {
        return roomService.getByUUID(roomUuid);
    }

    @RequestMapping(value = "{roomUuid}/posts", method = RequestMethod.POST)
    public Post postToRoom(@PathVariable("roomUuid") UUID roomUuid, @RequestBody PostCreateRequestModel request) {
        return postsService.createPost(request, roomUuid);
    }

    @RequestMapping(value = "{roomUuid}/posts", method = RequestMethod.GET)
    public List<Post> getRoomPosts(@PathVariable("roomUuid") UUID roomUuid) {
        roomService.getByUUID(roomUuid);
        return postsService.getPostsByRoom(roomUuid);
    }

    @RequestMapping(value = "{roomUuid}/invite", method = RequestMethod.POST)
    public RoomMembership invite(@RequestParam("userUuid") UUID userId, @PathVariable("roomUuid") UUID roomUuid) {
        return roomMembershipService.invite(roomUuid, userId);
    }

    @RequestMapping(value = "{roomUuid}/leave", method = RequestMethod.DELETE)
    public void leaveRoom(@PathVariable("roomUuid") UUID roomUuid) {
        roomMembershipService.deleteMembership(roomUuid);
    }

    /**
     * Create a room
     *
     * @param model the room model
     * @return the room that was created
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Room createRoom(@RequestBody CreateRoomRequestModel model) {
        return roomService.createRoom(model);
    }

    /**
     * Delete a room. Only the user that created the room can delete it
     *
     * @param roomUuid The room uuid you want to delete
     */
    @RequestMapping(value = "{roomUuid}", method = RequestMethod.DELETE)
    public void deleteRoom(@PathVariable("roomUuid") UUID roomUuid) {
        roomService.deleteRoom(roomUuid);
    }
}
