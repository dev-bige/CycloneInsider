package edu.cs309.cycloneinsider.api.models;

public class RoomMembershipModel {
    String uuid;
    RoomModel room;
    InsiderUserModel user;

    public RoomModel getRoom() {
        return room;
    }

    public void setRoom(RoomModel room) {
        this.room = room;
    }

    public InsiderUserModel getUser() {
        return user;
    }

    public void setUser(InsiderUserModel user) {
        this.user = user;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
