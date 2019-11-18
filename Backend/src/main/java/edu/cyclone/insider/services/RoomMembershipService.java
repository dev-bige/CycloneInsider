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
    public RoomMembershipService(RoomService roomService,
                                 UserService userService,
                                 UserStateService userStateService,
                                 RoomMembershipRepository roomMembershipRepository) {
        this.roomService = roomService;
        this.userService = userService;
        this.userStateService = userStateService;
        this.roomMembershipRepository = roomMembershipRepository;
    }

    public boolean isMember(UUID roomUuid) {
        return getMembershipOptional(roomUuid).isPresent();
    }

    public List<RoomMembership> getMemberships() {
        return getMemberships(userStateService.getCurrentUser().getUuid());
    }

    public List<RoomMembership> getMemberships(UUID userId) {
        return roomMembershipRepository.getUserMemberships(userId);
    }

    public List<RoomMembership> getPendingMemberships() {
        return getPendingMemberships(userStateService.getCurrentUser().getUuid());
    }

    public List<RoomMembership> getPendingMemberships(UUID userId) {
        return roomMembershipRepository.getPendingUserMemberships(userId);
    }

    public Optional<RoomMembership> getMembershipOptional(UUID roomUuid) {
        return getMembershipOptional(roomUuid, userStateService.getCurrentUser().getUuid());
    }

    public Optional<RoomMembership> getMembershipOptional(UUID roomUuid, UUID userUuid) {
        return roomMembershipRepository.getMembership(userUuid, roomUuid);
    }

    public RoomMembership getMembership(UUID roomUuid) {
        return getMembership(roomUuid, userStateService.getCurrentUser().getUuid());
    }

    public RoomMembership getMembership(UUID roomUuid, UUID userUuid) {
        Optional<RoomMembership> membership = roomMembershipRepository.getMembership(userUuid, roomUuid);
        if (membership.isPresent()) {
            return membership.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


    public boolean hasCreatorPrivileges(UUID roomUuid) {
        return hasPrivilege(roomUuid, RoomLevel.CREATOR);
    }

    public boolean hasModeratorPrivileges(UUID roomUuid) {
        return hasPrivilege(roomUuid, RoomLevel.MODERATOR);
    }

    private boolean hasPrivilege(UUID roomUuid, RoomLevel roomLevel) {
        Optional<RoomMembership> membership = getMembershipOptional(roomUuid);
        if (membership.isPresent()) {
            return membership.get().getRoomLevel() == roomLevel;
        }
        return false;
    }

    public RoomMembership invite(UUID roomUuid, UUID userUuid) {
        InsiderUser invitedUser = userService.getByUUID(userUuid);
        Room room = roomService.getByUUID(roomUuid);

        String message = String.format("You have been invited to %s from %s", room.getName(), userStateService.getCurrentUser().getFullName());
        NotificationController.broadcastNotificationToUUID(userUuid, message);

        Optional<RoomMembership> currentMembership = getMembershipOptional(roomUuid, userUuid);
        if (currentMembership.isPresent()) {
            return currentMembership.get();
        }

        boolean hasCreatorPrivileges = hasCreatorPrivileges(roomUuid);
        if (hasCreatorPrivileges) {
            RoomMembership roomMembership = new RoomMembership();
            roomMembership.setUser(invitedUser);
            roomMembership.setRoomLevel(RoomLevel.USER);
            roomMembership.setInvitedBy(userStateService.getCurrentUser());
            roomMembership.setIsPending(true);
            roomMembership.setRoom(room);
            roomMembership = roomMembershipRepository.save(roomMembership);
            return roomMembership;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    public RoomMembership joinRoom(UUID roomUuid) {
        Room room = roomService.getByUUID(roomUuid);

        //Logic for joining a room that is (invites)
        //Check if membership already exists
        Optional<RoomMembership> membership = getMembershipOptional(roomUuid);
        if (membership.isPresent()) {
            RoomMembership roomMembership = membership.get();
            //If pending, we need to change it to not pending
            if (roomMembership.isPending() && roomMembership.getInvitedBy() != null) {
                String message = String.format("%s has accepted their room invite", userStateService.getCurrentUser().getFullName());
                NotificationController.broadcastNotificationToUUID(roomMembership.getInvitedBy().getUuid(), message);
                roomMembership.setIsPending(false);
                roomMembership = roomMembershipRepository.save(roomMembership);
            }
            //Return current membership if already exists...
            return roomMembership;
        }

        if (room.getPrivateRoom()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        RoomMembership roomMembership = new RoomMembership();
        roomMembership.setRoom(room);
        roomMembership.setUser(this.userStateService.getCurrentUser());
        roomMembership.setIsPending(false);
        roomMembership.setRoomLevel(RoomLevel.USER);
        roomMembership = roomMembershipRepository.save(roomMembership);
        return roomMembership;
    }

    public void deleteMembership(UUID roomUuid) {
        deleteMembership(roomUuid, userStateService.getCurrentUser().getUuid());
    }

    public void deleteMembership(UUID roomId, UUID userId) {
        Room room = roomService.getByUUID(roomId);
        RoomMembership myMembership = getMembership(roomId);
        RoomLevel myLevel = myMembership.getRoomLevel();
        RoomMembership membership = getMembership(roomId, userId);
        boolean isMyMembership = myMembership.getUuid().equals(membership.getUuid());

        //It's my membership, i have global admin, i am the creator of the room, or  i am a moderator for the room, and i can't leave a room i created
        boolean canDelete =
                isMyMembership ||
                        userStateService.hasAdminPrivileges() ||
                        myLevel == RoomLevel.CREATOR ||
                        myLevel == RoomLevel.MODERATOR;
        canDelete = canDelete && !(isMyMembership && myLevel == RoomLevel.CREATOR);

        if (canDelete) {
            roomMembershipRepository.delete(membership);
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    public List<RoomMembership> getMembersInRoom(UUID roomUuid) {
        return roomMembershipRepository.getMembersInRoom(roomUuid);
    }
}
