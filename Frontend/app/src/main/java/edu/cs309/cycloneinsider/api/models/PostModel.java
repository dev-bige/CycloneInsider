package edu.cs309.cycloneinsider.api.models;


import java.util.List;

public class PostModel {
    String content;
    String title;
    InsiderUserModel user;
    RoomModel room;
    List<String> tags;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public InsiderUserModel getUser() {
        return user;
    }

    public void setUser(InsiderUserModel user) {
        this.user = user;
    }

    public RoomModel getRoom() {
        return room;
    }

    public void setRoom(RoomModel room) {
        this.room = room;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
