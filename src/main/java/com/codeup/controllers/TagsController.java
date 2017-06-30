package com.codeup.controllers;

import com.codeup.models.Tag;
import com.codeup.repositories.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by fer on 6/30/17.
 */
@Controller
public class TagsController {

    @Autowired
    Tags tagsDao;

    @GetMapping("/tags.json")
    @ResponseBody
    public Iterable<Tag> getTagsJSON(){
        return tagsDao.findAll();
    }

}
