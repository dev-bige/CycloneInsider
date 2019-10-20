package edu.cyclone.insider.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
    @Entity
    public class FavPost extends BaseModel {
        @ManyToOne
        private InsiderUser user;
        @ManyToOne
        private Post post;
        @Basic
        @Temporal(TemporalType.TIMESTAMP)
        private Date date;


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
    }