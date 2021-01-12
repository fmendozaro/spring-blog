package com.fer_mendoza.blog.controllers;

import com.fer_mendoza.blog.models.Post;
import com.fer_mendoza.blog.models.Tag;
import com.fer_mendoza.blog.services.EmailService;
import com.fer_mendoza.blog.services.UserService;
import com.fer_mendoza.blog.repositories.PostRepository;
import com.fer_mendoza.blog.repositories.TagRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    EmailService emailService;

    @GetMapping("/posts")
    public String getPosts(Model m){
        m.addAttribute("page", postRepositoryRepo.postsInReverse() );
        return "posts/index";
    }

    @GetMapping("/posts/feed")
    public String getPostsFeed(Model m){
        m.addAttribute("page", postRepositoryRepo.postsInReverse() );
        return "fragments/posts-feed :: feed";
    }

    @GetMapping("posts/{id}")
    public String show(@PathVariable Long id, Model m){
        Post post = postRepositoryRepo.getOne(id);
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
        uploadFileHandler(postSubmitted, m, uploadedFile);

        postSubmitted.setTags(tags);
        postSubmitted.setUser(usersSvc.loggedInUser());
        Post savedPost = postRepositoryRepo.save(postSubmitted);
        emailService.prepareAndSend(savedPost, "New post created confirmation",
                "Dear " + savedPost.getUser().getUsername()
                        + ", thank you for creating a post. Your blog post id is "
                        + savedPost.getId());

        return "redirect:/posts";
    }

    private void uploadFileHandler(@Valid Post postSubmitted, Model m, @RequestParam(name = "file") MultipartFile uploadedFile) {
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
    }

    @GetMapping("posts/{id}/edit")
    public String showEdit(@PathVariable Long id, Model m){
        Post post = postRepositoryRepo.getOne(id);
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

        Post postToBeUpdated = postRepositoryRepo.getOne(postEdited.getId());

        // Files handle
        uploadFileHandler(postToBeUpdated, m, uploadedFile);

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

    // Shows all the posts via json
    @GetMapping(value = "/posts.json", produces = "application/json")
    public @ResponseBody Iterable<Post> viewAllPostsInJSONFormat() {
        return postRepositoryRepo.findAll();
    }

    // Shows one post at a time via json
    @GetMapping(value = "/posts/{id}.json", produces = "application/json")
    public @ResponseBody Post viewPostInJSONFormat(@PathVariable long id) {
        return postRepositoryRepo.findById(id).get();
    }

    @PostMapping("/posts/delete")
    public String deletePost(@ModelAttribute Post post){
        postRepositoryRepo.delete(postRepositoryRepo.getOne(post.getId()));
        return "redirect:/posts";
    }

    @PostMapping("/posts/search")
    public String search(@RequestParam(name = "term") String term, Model vModel){
        term = "%"+term+"%";
        vModel.addAttribute("posts", postRepositoryRepo.findByBodyIsLikeOrTitleIsLike(term, term));
        return "posts/results";
    }

    @GetMapping("/getPostsDates.json")
    @ResponseBody
    public List<Map<String, String>>  getPostsDates(){

        List<Map<String, String>> events = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Iterable<Post> posts = postRepositoryRepo.findAll();

        for (Post post : posts) {
            HashMap<String, String> map = new HashMap<>();
            map.put("title", post.getTitle());
            map.put("date", simpleDateFormat.format(post.getCreateDate()));
            events.add(map);
        }

        return events;
    }

    @GetMapping("/posts/remove-tag")
    public String deleteTagsUI(){
        return "demos/simplified/remove-tag";
    }

    @PostMapping("/posts/remove-tag")
    @ResponseBody
    public String deleteTagFromPost(@RequestParam(value = "tag_id") long tagIdToRemove, @RequestParam(value = "post_id") long postId){
        Post post = postRepositoryRepo.getOne(postId);
        List<Tag> tags = post.getTags();
        tags.removeIf(tag -> tag.getId().equals(tagIdToRemove));
        postRepositoryRepo.save(post);
        return "deleted";
    }

}
