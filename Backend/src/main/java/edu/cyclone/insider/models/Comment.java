package edu.cyclone.insider.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@Entity
@Table(name = "comments")
public class Comment extends BaseModel {
    @NotNull
    @Size(max = 2000)
    private String comment;
    @ManyToOne
    private InsiderUser user;
    @ManyToOne
    private Post post;
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Comment() {
    }

    /**
     * gets the comment
     *
     * @return comment-String
     */
    public String getComment() {
        return comment;
    }

    /**
     * generates the comment on the post
     *
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * gets the user who created post
     *
     * @return user
     */
    public InsiderUser getUser() {
        return user;
    }

    /**
     * sets person who created the post to the user.
     *
     * @param user
     */
    public void setUser(InsiderUser user) {
        this.user = user;
    }

    /**
     * gets the post that was created
     *
     * @return post
     */
    public Post getPost() {
        return post;
    }

    /**
     * sets the Post
     *
     * @param post-post to set current post to
     */
    public void setPost(Post post) {
        this.post = post;
    }

    /**
     * gets the current date
     *
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * sets the date to current date and time
     * * @param date
     *
     * @return date
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
