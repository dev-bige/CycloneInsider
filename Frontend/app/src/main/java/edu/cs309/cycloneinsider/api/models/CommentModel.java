package edu.cs309.cycloneinsider.api.models;

import java.util.Date;

public class CommentModel {
    String uuid;
    String comment;
    Date date;
    PostModel post;
    InsiderUserModel user;
}
