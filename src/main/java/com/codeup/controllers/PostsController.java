package com.codeup.controllers;

import com.codeup.models.Post;
import com.codeup.models.Tag;
import com.codeup.repositories.Posts;
import com.codeup.repositories.Tags;
import com.codeup.services.UserSvc;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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
import java.util.Date;
import java.util.List;

/**
 * Created by Fer on 1/5/17.
 */
@Controller
public class PostsController {

    @Value("${file-upload-path}")
    private String uploadPath;

    @Autowired
    Posts postsRepo;

    @Autowired
    UserSvc usersSvc;

    @Autowired
    Tags tagsRepo;

     @GetMapping("/posts")
    public String getPosts(Model m, @PageableDefault(value=3, direction = Sort.Direction.DESC, sort = "createDate") Pageable pageable){
        m.addAttribute("page", postsRepo.findAll(pageable) );
        return "posts/index";
    }

    @GetMapping("posts/{id}")
    public String show(@PathVariable Long id, Model m){
        m.addAttribute("post", postsRepo.findById(id));
        return "posts/show";
    }

    @GetMapping("posts/create")
    public String showCreate(Model m){
        m.addAttribute("post", new Post());
        m.addAttribute("tags", tagsRepo.findAll());
        return "posts/create";
    }

    @PostMapping("posts/create")
    public String createPost(@Valid Post postCreated, Errors validation, Model m, @RequestParam(name = "file") MultipartFile uploadedFile, @RequestParam(name = "tags") List<Tag> tags){

        if (validation.hasErrors()) {
            m.addAttribute("errors", validation);
            System.out.println(validation.getAllErrors());
            m.addAttribute("post", postCreated);
            return "posts/create";
        }

        // Files handle
        if(!uploadedFile.getOriginalFilename().isEmpty()){

            String filename = uploadedFile.getOriginalFilename().replace(" ", "_").toLowerCase();
            String filepath = Paths.get(uploadPath, filename).toString();
            File destinationFile = new File(filepath);

            // Try to save it in the server
            try {
                uploadedFile.transferTo(destinationFile);
                m.addAttribute("message", "File successfully uploaded!");
            } catch (IOException e) {
                e.printStackTrace();
                m.addAttribute("message", "Oops! Something went wrong! " + e);
            }

            //Save it in the DB
            postCreated.setImageUrl(filename);
        }

        postCreated.setTags(tags);
        postCreated.setUser(usersSvc.loggedInUser());
        postsRepo.save(postCreated);

        return "redirect:/posts";
    }

    @GetMapping("posts/{id}/edit")
    public String showEdit(@PathVariable Long id, Model m){
        Post post = postsRepo.findById(id);
        m.addAttribute("post", post);
        return "posts/edit";
    }

    @PostMapping("posts/edit")
    public String edit(@Valid Post postEdited, Errors val, Model m, @RequestParam(name = "tags") List<Tag> tags, @RequestParam(name = "file") MultipartFile uploadedFile){

        if(val.hasErrors()){
            m.addAttribute("errors", val);
            m.addAttribute("post", postEdited);
            return "posts/edit";
        }

        Post postToBeUpdated = postsRepo.findById(postEdited.getId());

        // Files handle
        if(!uploadedFile.getOriginalFilename().isEmpty()){

            String filename = uploadedFile.getOriginalFilename().replace(" ", "_").toLowerCase();
            String filepath = Paths.get(uploadPath, filename).toString();
            File destinationFile = new File(filepath);

            // Try to save it in the server
            try {
                uploadedFile.transferTo(destinationFile);
                m.addAttribute("message", "File successfully uploaded!");
            } catch (IOException e) {
                e.printStackTrace();
                m.addAttribute("message", "Oops! Something went wrong! " + e);
            }

            //Save it in the DB
            postToBeUpdated.setImageUrl(filename);
        }

        postToBeUpdated.setTags(tags);
        postToBeUpdated.setTitle(postEdited.getTitle());
        postToBeUpdated.setBody(postEdited.getBody());
        postsRepo.save(postToBeUpdated);

        return "redirect:/posts/"+postEdited.getId();
    }

    // Getting posts by range of dates, just for practice.
    @PostMapping("posts/range")
    public String postByRange(){

        //Filtered Dates
        LocalDate today = new LocalDate().now();
        LocalDate toDate = today.plusDays(3);
        List<Post> filteredPosts = postsRepo.findByCreateDateBetween(today.toDate(), toDate.toDate());

        System.out.println("Today " + today.toDate().toString());

        for (Post post: filteredPosts) {
            System.out.println(post.getCreateDate());
            System.out.println(post.getTitle());
        }

        return "posts/index";
    }

    @GetMapping(value = "/posts.json")
    public @ResponseBody Page<Post> viewAllPostsInJSONFormat( @PageableDefault(value=3, direction = Sort.Direction.DESC, sort = "createDate") Pageable pageable ) {
        return postsRepo.findAll(pageable);
    }

    @PostMapping("/posts/delete")
    public String deletePost(@ModelAttribute Post post){
        postsRepo.delete(postsRepo.findOne(post.getId()));
        return "redirect:/posts";
    }

    @PostMapping("/posts/search")
    public String search(@RequestParam(name = "term") String term, Model vModel){
        term = "%"+term+"%";
        vModel.addAttribute("posts", postsRepo.findByBodyIsLikeOrTitleIsLike(term, term));
        return "posts/results";
    }

}
