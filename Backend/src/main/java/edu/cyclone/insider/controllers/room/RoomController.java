package edu.cyclone.insider.controllers.room;

import edu.cyclone.insider.controllers.post.models.PostCreateRequestModel;
import edu.cyclone.insider.controllers.room.models.CreateRoomRequestModel;
import edu.cyclone.insider.models.Post;
import edu.cyclone.insider.models.Room;
import edu.cyclone.insider.models.RoomMembership;
import edu.cyclone.insider.services.PostsService;
import edu.cyclone.insider.services.RoomMembershipService;
import edu.cyclone.insider.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    /**
     * Join a room
     *
     * @param room_uuid
     * @return room that was joined
     */
    @RequestMapping(value = "{uuid}/join", method = RequestMethod.POST)
    public RoomMembership joinRoom(@PathVariable("uuid") UUID room_uuid) {

        return roomMembershipService.joinRoom(room_uuid);
    }

    @RequestMapping(value = "public", method = RequestMethod.GET)
    public List<Room> getPublicRooms() {
        return roomService.getPublicRooms();
    }

    /**
     * get a room
     *
     * @param roomUuid
     * @return the room was specified with param
     */
    @RequestMapping(value = "{roomUuid}", method = RequestMethod.GET)
    public Room getRoom(@PathVariable("roomUuid") UUID roomUuid) {
        return roomService.getByUUID(roomUuid);
    }

    /**
     * get all members to a room
     *
     * @param roomUuid
     * @return list of members in the room
     */
    @RequestMapping(value = "{roomUuid}/members", method = RequestMethod.GET)
    public List<RoomMembership> getMembersInRoom(@PathVariable("roomUuid") UUID roomUuid) {
        return roomMembershipService.getMembersInRoom(roomUuid);
    }

    /**
     * Post to a room
     *
     * @param roomUuid
     * @return a generated post
     */
    @RequestMapping(value = "{roomUuid}/posts", method = RequestMethod.POST)
    public Post postToRoom(@PathVariable("roomUuid") UUID roomUuid, @RequestBody PostCreateRequestModel request) {
        return postsService.createPost(request, roomUuid);
    }

    /**
     * get the posts in a room
     *
     * @param roomUuid
     * @return all posts in a specified room
     */
    @RequestMapping(value = "{roomUuid}/posts", method = RequestMethod.GET)
    public List<Post> getRoomPosts(@PathVariable("roomUuid") UUID roomUuid) {
        roomService.getByUUID(roomUuid);
        return postsService.getPostsByRoom(roomUuid);
    }

    /**
     * Invite user to a room
     *
     * @param roomUuid
     * @param userId
     * @return a invite sent to user blank for room blank
     */
    @RequestMapping(value = "{roomUuid}/invite", method = RequestMethod.POST)
    public RoomMembership invite(@RequestParam("userUuid") UUID userId, @PathVariable("roomUuid") UUID roomUuid) {
        return roomMembershipService.invite(roomUuid, userId);
    }

    /**
     * lets user leave a room
     *
     * @param roomUuid updates roomembership service without user that left
     */
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
