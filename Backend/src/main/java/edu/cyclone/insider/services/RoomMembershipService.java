package edu.cyclone.insider.services;

import edu.cyclone.insider.controllers.notifications.NotificationController;
import edu.cyclone.insider.models.InsiderUser;
import edu.cyclone.insider.models.Room;
import edu.cyclone.insider.models.RoomLevel;
import edu.cyclone.insider.models.RoomMembership;
import edu.cyclone.insider.repos.RoomMembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoomMembershipService {
    private RoomService roomService;
    private UserService userService;
    private UserStateService userStateService;
    private RoomMembershipRepository roomMembershipRepository;

    @Autowired
    public RoomMembershipService(RoomService roomService, UserService userService, UserStateService userStateService, RoomMembershipRepository roomMembershipRepository) {
        this.roomService = roomService;
        this.userService = userService;
        this.userStateService = userStateService;
        this.roomMembershipRepository = roomMembershipRepository;
    }

    public boolean isMember(UUID roomUuid) {
        return getMembership(roomUuid).isPresent();
    }

    public List<RoomMembership> getMemberships() {
        return getMemberships(userStateService.getCurrentUser().getUuid());
    }

    public List<RoomMembership> getMemberships(UUID userId) {
        return roomMembershipRepository.findUserMemberships(userId);
    }

    public List<RoomMembership> getPendingMemberships() {
        return getPendingMemberships(userStateService.getCurrentUser().getUuid());
    }

    public List<RoomMembership> getPendingMemberships(UUID userId) {
        return roomMembershipRepository.findPendingUserMemberships(userId);
    }

    public Optional<RoomMembership> getMembership(UUID roomUuid) {
        return getMembership(userStateService.getCurrentUser().getUuid(), roomUuid);
    }

    public Optional<RoomMembership> getMembership(UUID roomUuid, UUID userUuid) {
        return roomMembershipRepository.findMembership(userUuid, roomUuid);
    }

    public boolean hasCreatorPrivileges(UUID roomUuid) {
        return hasPrivilege(roomUuid, RoomLevel.CREATOR);
    }

    public boolean hasModeratorPrivileges(UUID roomUuid) {
        return hasPrivilege(roomUuid, RoomLevel.MODERATOR);
    }

    private boolean hasPrivilege(UUID roomUuid, RoomLevel roomLevel) {
        Optional<RoomMembership> membership = getMembership(roomUuid);
        if (membership.isPresent()) {
            return membership.get().getRoomLevel() == roomLevel;
        }
        return false;
    }

    public RoomMembership invite(UUID roomUuid, UUID userUuid) {
        Optional<InsiderUser> invitedUser = userService.getByUUID(userUuid);
        Optional<Room> room = roomService.getByUUID(roomUuid);
        if (!invitedUser.isPresent() || !room.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        String message = String.format("You have been invited to %s from %s", room.get().getName(), userStateService.getCurrentUser().getFullName());
        NotificationController.broadcastNotificationToUUID(userUuid, message);

        Optional<RoomMembership> currentMembership = getMembership(roomUuid, userUuid);
        if (currentMembership.isPresent()) {
            return currentMembership.get();
        }

        boolean hasCreatorPrivileges = hasCreatorPrivileges(roomUuid);
        if (hasCreatorPrivileges) {
            RoomMembership roomMembership = new RoomMembership();
            roomMembership.setUser(invitedUser.get());
            roomMembership.setRoomLevel(RoomLevel.USER);
            roomMembership.setInvitedBy(userStateService.getCurrentUser());
            roomMembership.setPending(true);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
