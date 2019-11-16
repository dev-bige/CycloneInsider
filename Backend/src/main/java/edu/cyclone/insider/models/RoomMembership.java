package edu.cyclone.insider.models;

import javax.persistence.*;

/**
 * class for generating a room for posts
 */
@Entity
public class RoomMembership extends BaseModel {
    @ManyToOne
    private InsiderUser user;
    @ManyToOne
    private InsiderUser invitedBy;
    @ManyToOne
    private Room room;

    @Enumerated(EnumType.ORDINAL)
    @Column
    private RoomLevel roomLevel = RoomLevel.USER;

    private Boolean pending;

    public RoomMembership() {
    }
    /**
     * gets the user that created the room
     * @return user
     */
    public InsiderUser getUser() {
        return user;
    }
    /**
     * sets the user of the room to the current
     * @param user- the current user
     */
    public void setUser(InsiderUser user) {
        this.user = user;
    }
    /**
     * gets the current room that was created
     * @return room
     */
    public Room getRoom() {
        return room;
    }
    /**
     * sets the room to the current room
     * @param room
     */
    public void setRoom(Room room) {
        this.room = room;
    }
    /**
     * gets the flag to see if the invite is pending still
     * @return pending- Boolean
     */
    public Boolean isPending() {
        return pending;
    }
    /**
     * sets the pending flag
     * @param pending- of type Boolean
     */
    public void setIsPending(Boolean pending) {
        this.pending = pending;
    }
    /**
     * gets who sent out the invite to the room
     * @return user- the one who sent the invite
     */
    public InsiderUser getInvitedBy() {
        return invitedBy;
    }
    /**
     * sets the user sending the invite to to the current user
     * @param invitedBy
     */
    public void setInvitedBy(InsiderUser invitedBy) {
        this.invitedBy = invitedBy;
    }

    public RoomLevel getRoomLevel() {
        return roomLevel;
    }

    public void setRoomLevel(RoomLevel userLevel) {
        this.roomLevel = userLevel;
    }
}
