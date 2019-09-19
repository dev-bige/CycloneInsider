/*
* @author Andrew Dort
*Post.java - class to create post variables for the controller
*
*
*/
package com.example.post;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @NotEmpty
    private Integer PostId;


    @NotEmpty
    private Integer UserId;

    @NotEmpty
    private String post_name;



    @NotEmpty
    private String post_category;


    @NotEmpty
    private String userposted_name;


    @NotEmpty
    private String content_posted;

    public Integer getPostId() {
        return PostId;
    }

    public void setId(Integer PostId) {
        this.PostId = PostId;
    }

    public boolean isNew() {
        return this.PostId == null;
    }

    public String getpost_name() {
        return this.post_name;
    }

    public void setpost_name(String post_name) {
        this.post_name = post_name;
    }


    public void setUserId(Integer UserId) {
        this.UserId = UserId;
    }


    public Integer getUserId() {
        return this.UserId;
    }


    public String getpost_category() {
        return this.post_category;
    }

    public void setpost_category(String post_category) {
        this.post_category = post_category;
    }

    public String getcontent_posted() {
        return this.content_posted;
    }

    public void setcontent_posted(String content_posted) {
        this.content_posted = content_posted;

    }


    }





