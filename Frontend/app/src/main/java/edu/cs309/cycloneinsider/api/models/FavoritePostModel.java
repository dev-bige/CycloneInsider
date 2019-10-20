package edu.cs309.cycloneinsider.api.models;

import java.util.Date;

public class FavoritePostModel {
    String uuid;
    InsiderUserModel user;
    PostModel post;
    Date date;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public InsiderUserModel getUser() {
        return user;
    }

    public void setUser(InsiderUserModel user) {
        this.user = user;
    }

    public PostModel getPost() {
        return post;
    }

    public void setPost(PostModel post) {
        this.post = post;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
