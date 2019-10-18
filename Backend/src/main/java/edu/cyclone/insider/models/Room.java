package edu.cyclone.insider.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Room extends BaseModel {
    private String name;
    private String description;
    private Boolean privateRoom;
    @ManyToOne
    private InsiderUser creator;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPrivateRoom() {
        return privateRoom;
    }

    public void setPrivateRoom(Boolean privateRoom) {
        this.privateRoom = privateRoom;
    }

    public InsiderUser getCreator() {
        return creator;
    }

    public void setCreator(InsiderUser creator) {
        this.creator = creator;
    }
}
