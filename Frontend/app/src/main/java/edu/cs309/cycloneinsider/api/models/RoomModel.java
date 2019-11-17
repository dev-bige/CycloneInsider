package edu.cs309.cycloneinsider.api.models;

public class RoomModel {
    public String name;
    public String uuid;
    public InsiderUserModel creator;

    public InsiderUserModel getCreator() {
        return creator;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }
}
