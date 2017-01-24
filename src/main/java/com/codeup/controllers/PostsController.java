package com.codeup.controllers;

import com.codeup.Daos.DaoFactory;
import com.codeup.models.Post;
import com.codeup.Repositories.Posts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;


/**
 * Created by Fer on 1/5/17.
 */
@Controller
public class PostsController extends BaseController {

    @Value("${file-upload-path}")
    private String uploadPath;

    @Autowired
    Posts postsDao;

    @GetMapping("/posts")
    public String getPosts(Model m, @PageableDefault(value=3, direction = Sort.Direction.DESC, sort = "createDate") Pageable pageable){
        //List<Post> posts = DaoFactory.getPostsDao().all();
        m.addAttribute("page", postsDao.findAll(pageable) );
        return "posts/index";
    }

    @GetMapping("/posts/sample")
    public String sampleData(){
        DaoFactory.getPostsListDao().generatePosts();
        return "redirect:/posts";
    }

    @GetMapping("posts/{id}")
    public String show(@PathVariable int id, Model m){
        m.addAttribute("post", postsDao.findById(id));
        return "posts/show";
    }

    @GetMapping("posts/create")
    public String showCreate(Model m){
        m.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("posts/create")
    public String createPost(@Valid Post postCreated, Errors validation, Model m, @RequestParam(name = "file") MultipartFile uploadedFile){
        //DaoFactory.getPostsListDao().save(post);

        if (validation.hasErrors()) {
            m.addAttribute("errors", validation);
            System.out.println(validation.getAllErrors());
            m.addAttribute("post", postCreated);
            return "posts/create";
        }

        //Files
        if(!uploadedFile.getOriginalFilename().isEmpty()){
            String filename = uploadedFile.getOriginalFilename();
            String filepath = Paths.get(uploadPath, filename).toString();
            File destinationFile = new File(filepath);
            try {
                uploadedFile.transferTo(destinationFile);
                m.addAttribute("message", "File successfully uploaded!");
            } catch (IOException e) {
                e.printStackTrace();
                m.addAttribute("message", "Oops! Something went wrong! " + e);
            }
            postCreated.setImageUrl("/uploads/" + filename.get);
        }

        postCreated.setUser(loggedInUser());
        postsDao.save(postCreated);
        return "redirect:/posts";
    }

    @GetMapping("posts/{id}/edit")
    public String showEdit(@PathVariable int id, Model m){
        Post post = postsDao.findById(id);
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

        Post newPost = postsDao.findById(postEdited.getId());
        newPost.setTitle(postEdited.getTitle());
        newPost.setBody(postEdited.getBody());
        postsDao.save(newPost);

        return "redirect:/posts/"+postEdited.getId();
    }

}
