package edu.cyclone.insider.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
    @Entity
    @Table(name = "FavPost")
    public class FavPost extends BaseModel {

        @ManyToOne private Post post;
        @Basic
        @Temporal(TemporalType.TIMESTAMP)
        private Date date;

        public FavPost() {
        }
        public Post getPost() {
            return post;
        }
        public void setPost(Post post) {
            this.post = post;
        }



}
