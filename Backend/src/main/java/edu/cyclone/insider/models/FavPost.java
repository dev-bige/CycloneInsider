package edu.cyclone.insider.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class FavPost extends BaseModel {
    @ManyToOne
    private InsiderUser user;
    @ManyToOne
    private Post post;
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    /**
     * gets the full name the user signed up with
     * @return user- user that fav the post
     */
    public InsiderUser getUser() {
        return user;
    }

    /**
     * sets the user of the favorited post
     * @param user
     */
    public void setUser(InsiderUser user) {
        this.user = user;
    }


    /**
     * gets the post that was favorited by the user
     * @return post- the post that was favorited
     */
    public Post getPost() {
        return post;
    }

    /**
     * sets the favorite post to the post
     * @param post
     */
    public void setPost(Post post) {
        this.post = post;

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}