package edu.cyclone.insider.controllers.room;

import edu.cyclone.insider.controllers.BaseController;
import edu.cyclone.insider.controllers.room.models.CreateRoomRequestModel;
import edu.cyclone.insider.models.Room;
import edu.cyclone.insider.models.RoomMembership;
import edu.cyclone.insider.repos.RoomMembershipRepository;
import edu.cyclone.insider.repos.RoomRepository;
import edu.cyclone.insider.repos.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("rooms")
public class RoomController extends BaseController {
    private final RoomRepository roomRepository;
    private final RoomMembershipRepository roomMembershipRepository;

    @Autowired
    public RoomController(UsersRepository usersRepository, RoomRepository roomRepository, RoomMembershipRepository roomMembershipRepository) {
        super(usersRepository);
        this.roomRepository = roomRepository;
        this.roomMembershipRepository = roomMembershipRepository;
    }

    @RequestMapping(value = "memberships", method = RequestMethod.GET)
    public List<RoomMembership> getUserMemberships() {
        return roomMembershipRepository.findUserMemberships(getCurrentUser().getUuid());
    }

    @RequestMapping(value = "{uuid}/join", method = RequestMethod.POST)
    public RoomMembership joinRoom(@PathVariable("uuid") UUID room_uuid) {
        Optional<Room> byId = roomRepository.findById(room_uuid);
        if(!byId.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //Check if membership already exists
        RoomMembership membership = roomMembershipRepository.findMembership(getCurrentUser().getUuid(), room_uuid);
        if(membership != null) {
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

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Room createRoom(@RequestBody CreateRoomRequestModel model) {
        Room room = new Room();
        room.setName(model.name);
        room.setDescription(model.description);
        room = roomRepository.save(room);
        return room;
    }
}
