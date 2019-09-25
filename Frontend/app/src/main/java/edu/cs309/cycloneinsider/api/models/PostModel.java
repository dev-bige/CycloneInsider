package edu.cs309.cycloneinsider.api.models;


import java.util.List;

public class PostModel {
    String content;
    String title;
    InsiderUserModel user;
    RoomModel room;
    List<String> tags;
}
