package com.fer_mendoza.blog.controllers;

import com.fer_mendoza.blog.models.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fer_mendoza.blog.repositories.CommentRepository;

import java.util.List;

@Controller
public class CommentsController {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/comments")
    @ResponseBody
    public List<Comment> getComments(){
        return commentRepository.findAll();
    }

    @GetMapping("/comments/children/{id}")
    @ResponseBody
    public List<Comment> getChildren(@PathVariable long id){
        return commentRepository.findByParent(commentRepository.getOne(id));
    }

}
