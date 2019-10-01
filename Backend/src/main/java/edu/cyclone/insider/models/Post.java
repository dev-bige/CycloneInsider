/*
 * @author Andrew Dort
 *Post.java - class to create post variables for the controller
 *
 *
 */
package edu.cyclone.insider.models;


import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

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

    public Post() {
    }

    public InsiderUser getUser() {
        return user;
    }

    public void setUser(InsiderUser user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}





