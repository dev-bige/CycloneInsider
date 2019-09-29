package edu.cyclone.insider.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


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

    public void setPost(Post post) {

        this.post = post;
    }

    public Post getPost() {
        return post;
    }
}
