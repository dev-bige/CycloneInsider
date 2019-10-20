package edu.cs309.cycloneinsider.api.models;

public class FavoritePostModel {
    String uuid;
    InsiderUserModel user;
    PostModel post;

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
}
