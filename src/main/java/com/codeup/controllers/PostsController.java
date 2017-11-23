package com.codeup.controllers;

import com.codeup.models.Event;
import com.codeup.models.Post;
import com.codeup.models.Tag;
import com.codeup.repositories.PostRepository;
import com.codeup.repositories.TagRepository;
import com.codeup.services.UserService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fer on 1/5/17.
 */
@Controller
public class PostsController {

    @Value("${file-upload-path}")
    private String uploadPath;

    @Autowired
    PostRepository postRepositoryRepo;

    @Autowired
    UserService usersSvc;

    @Autowired
    TagRepository tagRepositoryRepo;

    @GetMapping("/posts")
    public String getPosts(Model m){
        m.addAttribute("page", postRepositoryRepo.postsInReverse() );
        return "posts/index";
    }

    @GetMapping("posts/{id}")
    public String show(@PathVariable Long id, Model m){
        Post post = postRepositoryRepo.findOne(id);
        m.addAttribute("isOwner", usersSvc.isOwner(post.getUser()));
        m.addAttribute("post", post);
        return "posts/show";
    }

    @GetMapping("posts/create")
    public String showCreate(Model m){
        m.addAttribute("post", new Post());
        m.addAttribute("tags", tagRepositoryRepo.findAll());
        return "posts/create";
    }

    @PostMapping("posts/create")
    public String createPost(@Valid Post postSubmitted, Errors validation, Model m, @RequestParam(name = "file") MultipartFile uploadedFile, @RequestParam(name = "tags") List<Tag> tags){

        if (validation.hasErrors()) {
            m.addAttribute("errors", validation);
            System.out.println(validation.getAllErrors());
            m.addAttribute("post", postSubmitted);
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
            postSubmitted.setImageUrl(filename);
        }

        postSubmitted.setTags(tags);
        postSubmitted.setUser(usersSvc.loggedInUser());
        postRepositoryRepo.save(postSubmitted);

        return "redirect:/posts";
    }

    @GetMapping("posts/{id}/edit")
    public String showEdit(@PathVariable Long id, Model m){
        Post post = postRepositoryRepo.findOne(id);
        if(!usersSvc.isOwner(post.getUser())){
            return "redirect:/posts/" + id;
        }
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

        Post postToBeUpdated = postRepositoryRepo.findOne(postEdited.getId());

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
        postRepositoryRepo.save(postToBeUpdated);

        return "redirect:/posts/"+postEdited.getId();
    }

    // Getting posts by range of dates, just for practice.
    @PostMapping("posts/range")
    public String postByRange(){

        //Filtered Dates
        LocalDate today = new LocalDate().now();
        LocalDate toDate = today.plusDays(3);
        List<Post> filteredPosts = postRepositoryRepo.findByCreateDateBetween(today.toDate(), toDate.toDate());

        System.out.println("Today " + today.toDate().toString());

        for (Post post: filteredPosts) {
            System.out.println(post.getCreateDate());
            System.out.println(post.getTitle());
        }

        return "posts/index";
    }

    @GetMapping(value = "/posts.json")
    public @ResponseBody Iterable<Post> viewAllPostsInJSONFormat() {
        return postRepositoryRepo.findAll();
    }

    @PostMapping("/posts/delete")
    public String deletePost(@ModelAttribute Post post){
        postRepositoryRepo.delete(postRepositoryRepo.findOne(post.getId()));
        return "redirect:/posts";
    }

    @PostMapping("/posts/search")
    public String search(@RequestParam(name = "term") String term, Model vModel){
        term = "%"+term+"%";
        vModel.addAttribute("posts", postRepositoryRepo.findByBodyIsLikeOrTitleIsLike(term, term));
        return "posts/results";
    }

    @GetMapping("/storedp")
    @ResponseBody
    public String storedPTest(){
        String res1 = postRepositoryRepo.inOnlyTest("Fer");
//        System.out.println(postsRepo.inOnlyTest("Fer"));
        return "Executed " + res1;
    }


    @GetMapping("/getPostsDates.json")
    @ResponseBody
    public List<Event> getPostsDates(){
        List<Event> events = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Iterable<Post> posts = postRepositoryRepo.findAll();

        for (Post post : posts) {
            Event obj = new Event();
            obj.setStart(simpleDateFormat.format(post.getCreateDate()));
            obj.setTitle(post.getTitle());
            events.add(obj);
        }

        return events;
    }

}
