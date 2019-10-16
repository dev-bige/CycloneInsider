package edu.cs309.cycloneinsider.api.models;

import java.util.Date;

public class CommentModel {
    String uuid;
    String comment;
    Date date;
    PostModel post;
    InsiderUserModel user;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PostModel getPost() {
        return post;
    }

    public void setPost(PostModel post) {
        this.post = post;
    }

    public InsiderUserModel getUser() {
        return user;
    }

    public void setUser(InsiderUserModel user) {
        this.user = user;
    }
}
