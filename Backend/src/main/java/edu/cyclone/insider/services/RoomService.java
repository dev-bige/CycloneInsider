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
    private RoomMembershipRepository roomMembershipRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository,
                       UserStateService userStateService,
                       RoomMembershipRepository roomMembershipRepository) {
        this.roomRepository = roomRepository;
        this.userStateService = userStateService;
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
        return roomRepository.getPublicRooms();
    }

    public Room createRoom(CreateRoomRequestModel model) {
        if (userStateService.hasAdminPrivileges() || userStateService.hasProfessorPrivileges()) {
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

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    public void deleteRoom(UUID roomId) {
        Optional<RoomMembership> membership = roomMembershipRepository.getMembership(userStateService.getCurrentUser().getUuid(), roomId);
        if (membership.isPresent() && membership.get().getRoomLevel() == RoomLevel.CREATOR) {
            roomRepository.deleteById(roomId);
            return;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
