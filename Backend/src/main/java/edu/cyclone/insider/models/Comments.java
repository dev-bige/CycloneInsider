package edu.cyclone.insider.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "Comments")
public class Comments extends BaseModel {
    @NotNull
    @Size(max = 2000)
    @ManyToOne
    private String user_Comment;
    @ManyToOne
    private InsiderUser user;
    @ManyToOne
    private Post post;

    public Comments() {
    }


    public String getComment() {
        return user_Comment;
    }

    public void setComment(String user_Comment) {
        this.user_Comment = user_Comment;
    }

    public InsiderUser getUser() {
        return user;
    }

    void setUser(InsiderUser user) {
        this.user = user;
    }

    public void setPost(Post post) {

        this.post = post;
    }

    public Post getPost() {
        return post;
    }


}
