package com.codeup.controllers;

import com.codeup.Repositories.Posts;
import com.codeup.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Fer on 1/18/17.
 */
public class JSONController {

    @Autowired
    Posts posts;

    @GetMapping(value = "/posts.json", produces = "application/json")
    public @ResponseBody
    Page<Post> viewAllPostsInJSONFormat(
            @PageableDefault(value=10) Pageable pageable
    ) {
        return posts.findAll(pageable);
    }

}
