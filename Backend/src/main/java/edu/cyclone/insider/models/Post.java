/*
 * @author Andrew Dort
 *Post.java - class to create post variables for the controller
 *
 *
 */
package edu.cyclone.insider.models;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Class for generating a post
 */
@Entity
@Table(name = "post")
public class Post extends BaseModel {
    @ManyToOne
    private InsiderUser user;
    @ManyToOne
    private Room room;
    private String title;
    @ElementCollection
    private List<String> tags;
    private String content;
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Post() {
    }

    /**
     * gets the current user
     *
     * @return user
     */
    public InsiderUser getUser() {
        return user;
    }

    /**
     * sets the user who created the post to current user
     *
     * @param user
     */
    public void setUser(InsiderUser user) {
        this.user = user;
    }

    /**
     * gets the current room that was generated
     *
     * @return room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * sets the room created to the current room
     *
     * @param room
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * gets the title of the post
     *
     * @return title- of type String
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets the title of the post
     *
     * @param title- of type String
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTags() {
        return tags;
    }

    /**
     * sets the tags for the post
     *
     * @param tags- from the list of type String
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * gets the content generated when post was created
     *
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * sets the content of the post to the current content that was generated
     *
     * @param content- of type String
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * gets the date that the post was created/edit on
     *
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * sets the date of the post
     *
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }


}





