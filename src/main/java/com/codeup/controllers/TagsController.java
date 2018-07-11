package com.codeup.controllers;

import com.codeup.models.Tag;
import com.codeup.repositories.PostRepository;
import com.codeup.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fer on 6/30/17.
 */
@Controller
public class TagsController {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    PostRepository postRepository;

    @GetMapping("/tags.json")
    @ResponseBody
    public Iterable<Tag> getTagsJSON(){
        return tagRepository.findAll();
    }

    @GetMapping("/tag/{id}")
    public String showPostsByTag(@PathVariable long id, Model vModel){
        List<Tag> tags = new ArrayList<>();
        tags.add(tagRepository.findOne(id));
        vModel.addAttribute("page", postRepository.findAllByTags(tags));
        vModel.addAttribute("title", "Posts by tag: " + tags.get(0).getName());
        return "posts/index";
    }

}
