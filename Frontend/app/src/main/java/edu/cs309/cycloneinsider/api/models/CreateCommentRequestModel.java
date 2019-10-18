package edu.cs309.cycloneinsider.api.models;

public class CreateCommentRequestModel {
    String comment;

    public CreateCommentRequestModel(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
