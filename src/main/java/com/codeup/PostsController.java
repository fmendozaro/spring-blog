package com.codeup;

import com.codeup.models.DaoFactory;
import com.codeup.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


/**
 * Created by Fer on 1/5/17.
 */
@Controller
public class PostsController {

    @GetMapping("/posts")
    public String getPosts(Model m){
        List<Post> posts = DaoFactory.getPostsDao().all();
        m.addAttribute("posts", posts);
        return "posts/index";
    }

    @GetMapping("/posts/sample")
    public String sampleData(){
        DaoFactory.getPostsDao().generatePosts();
        return "redirect:/posts";
    }

    @GetMapping("posts/create")
    public String showCreate(Model m){
        m.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("posts/create")
    public String createPost(@ModelAttribute Post post){
        DaoFactory.getPostsDao().insert(post);
        return "redirect:/posts";
    }

}
