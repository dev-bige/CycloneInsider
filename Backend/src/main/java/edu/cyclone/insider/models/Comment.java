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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public InsiderUser getUser() {
        return user;
    }

    public void setUser(InsiderUser user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

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
