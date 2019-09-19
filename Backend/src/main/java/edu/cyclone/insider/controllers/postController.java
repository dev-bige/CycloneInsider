/*
 *@author Andrew Dort
 *
 *
 */
package com.example.post;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class postController {

    @GetMapping(value="annotated/Post/{PostId}")
    public @ResponseBody Post getData(@PathVariable Integer PostId) {
        Post post = new Post();
        post.setpost_name("First Post");
        post.setId(PostId);
        post.setUserId(7);
        post.setpost_category("TestPost");
        post.setcontent_posted("This is the message that is stored inside the post created");
        return post;
    }



}



