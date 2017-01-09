package com.codeup;

import com.codeup.models.DaoFactory;
import com.codeup.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
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
        DaoFactory.getPostsListDao().generatePosts();
        return "redirect:/posts";
    }

    @GetMapping("posts/{id}")
    public String show(@PathVariable int id, Model m){
        m.addAttribute("post", DaoFactory.getPostsDao().getById(id));
        return "posts/show";
    }

    @GetMapping("posts/create")
    public String showCreate(Model m){
        m.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("posts/create")
    public String createPost(@Valid Post postCreated, Errors validation, Model m){
        //DaoFactory.getPostsListDao().save(post);

        if (validation.hasErrors()) {
            m.addAttribute("errors", validation);
            System.out.println(validation.getAllErrors());
            m.addAttribute("post", postCreated);
            return "posts/create";
        }

        DaoFactory.getPostsDao().save(postCreated);
        return "redirect:/posts";
    }

    @GetMapping("posts/{id}/edit")
    public String showEdit(@PathVariable int id, Model m){
        Post post = DaoFactory.getPostsDao().getById(id);
        m.addAttribute("post", post);
        return "posts/edit";
    }

    @PostMapping("posts/{id}/edit")
    public String edit(@Valid Post postEdited, Errors val, Model m){

        if(val.hasErrors()){
            m.addAttribute("errors", val);
            m.addAttribute("post", postEdited);
            return "posts/edit";
        }

        Post newPost = DaoFactory.getPostsDao().getById(postEdited.getId());
        newPost.setTitle(postEdited.getTitle());
        newPost.setBody(postEdited.getBody());
        DaoFactory.getPostsDao().update(newPost);

        return "redirect:/posts/"+postEdited.getId();
    }

}
