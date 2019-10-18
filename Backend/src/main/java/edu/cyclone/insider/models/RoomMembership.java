package edu.cyclone.insider.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class RoomMembership extends BaseModel {
    @ManyToOne
    private InsiderUser user;
    @ManyToOne
    private Room room;

    private Boolean pending;

    public RoomMembership() {
    }

    public InsiderUser getUser() {
        return user;
    }

    public void setUser(InsiderUser user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Boolean getPending() {
        return pending;
    }

    public void setPending(Boolean pending) {
        this.pending = pending;
    }
}
