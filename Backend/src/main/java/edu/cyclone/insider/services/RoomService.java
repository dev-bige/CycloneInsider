package edu.cyclone.insider.services;

import edu.cyclone.insider.controllers.room.models.CreateRoomRequestModel;
import edu.cyclone.insider.models.Room;
import edu.cyclone.insider.models.RoomLevel;
import edu.cyclone.insider.models.RoomMembership;
import edu.cyclone.insider.repos.RoomMembershipRepository;
import edu.cyclone.insider.repos.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoomService {
    private RoomRepository roomRepository;
    private UserStateService userStateService;
    private RoomMembershipService roomMembershipService;
    private RoomMembershipRepository roomMembershipRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository,
                       UserStateService userStateService,
                       RoomMembershipService roomMembershipService,
                       RoomMembershipRepository roomMembershipRepository) {
        this.roomRepository = roomRepository;
        this.userStateService = userStateService;
        this.roomMembershipService = roomMembershipService;
        this.roomMembershipRepository = roomMembershipRepository;
    }

    public Room getByUUID(UUID uuid) {
        Optional<Room> byId = roomRepository.findById(uuid);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public Optional<Room> getByUUIDOptional(@NonNull UUID uuid) {
        return roomRepository.findById(uuid);
    }

    public List<Room> getPublicRooms() {
        return roomRepository.publicRooms();
    }

    public Room createRoom(CreateRoomRequestModel model) {
        if (!userStateService.hasAdminPrivileges() || !userStateService.hasProfessorPrivileges()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Room room = new Room();
        room.setName(model.name);
        room.setCreator(userStateService.getCurrentUser());
        room.setPrivateRoom(model.privateRoom);
        room.setDescription(model.description);
        room = roomRepository.save(room);

        RoomMembership roomMembership = new RoomMembership();
        roomMembership.setRoom(room);
        roomMembership.setUser(userStateService.getCurrentUser());
        roomMembership.setIsPending(false);
        roomMembership.setRoomLevel(RoomLevel.CREATOR);
        roomMembership = roomMembershipRepository.save(roomMembership);
        return room;
    }

    public void deleteRoom(UUID roomId) {
        boolean hasCreatorPrivileges = roomMembershipService.hasCreatorPrivileges(roomId);
        if(hasCreatorPrivileges) {
            roomRepository.deleteById(roomId);
            return;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
