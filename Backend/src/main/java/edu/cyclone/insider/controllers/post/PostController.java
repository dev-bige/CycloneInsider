/*
 *@author Andrew Dort
 *
 *
 */
package edu.cyclone.insider.controllers.post;

import edu.cyclone.insider.models.Post;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("posts")
public class PostController {

    @RequestMapping(value = "front-page", method = RequestMethod.GET)
    public List<Post> getFrontPagePosts() {
        //TODO write code to read DB
        return new ArrayList<>();
    }

    @RequestMapping(value = "front-page", method = RequestMethod.POST)
    public void postFrontPagePost(@RequestBody Post post) {
        //TODO write code to write to DB
    }
}



