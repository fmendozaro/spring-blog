package com.codeup.controllers;

import com.codeup.Daos.DaoFactory;
import com.codeup.models.Post;
import com.codeup.repositories.Posts;
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
    Posts postsDao;

    @Autowired
    UserSvc usersSvc;

//    @Autowired
//    PostSvc postSvc;

     @GetMapping("/posts")
    public String getPosts(Model m, @PageableDefault(value=3, direction = Sort.Direction.DESC, sort = "createDate") Pageable pageable){
        m.addAttribute("page", postsDao.findAll(pageable) );
        return "posts/index";
    }

    @GetMapping("/posts/sample")
    public String sampleData(){
        Date today = new Date();
        for (Post post: DaoFactory.getPostsListDao().generatePosts()) {
            post.setUser(usersSvc.loggedInUser());
            post.setCreateDate(today);
            post.setModifyDate(today);
            postsDao.save(post);
        }
        return "redirect:/posts";
    }

    @GetMapping("posts/{id}")
    public String show(@PathVariable Long id, Model m){
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

        postCreated.setUser(usersSvc.loggedInUser());
        postsDao.save(postCreated);
        return "redirect:/posts";
    }

    @GetMapping("posts/{id}/edit")
    public String showEdit(@PathVariable Long id, Model m){
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

    // Getting posts by range of dates, just for practice.
    @PostMapping("posts/range")
    public String postByRange(){

        //Filtered Dates
        LocalDate today = new LocalDate().now();
        LocalDate toDate = today.plusDays(3);
        List<Post> filteredPosts = postsDao.findByCreateDateBetween(today.toDate(), toDate.toDate());

        System.out.println("Today " + today.toDate().toString());

        for (Post post: filteredPosts) {
            System.out.println(post.getCreateDate());
            System.out.println(post.getTitle());
        }

        return "posts/index";
    }

    @GetMapping(value = "/posts.json")
    public @ResponseBody Page<Post> viewAllPostsInJSONFormat( @PageableDefault(value=3, direction = Sort.Direction.DESC, sort = "createDate") Pageable pageable ) {
        return postsDao.findAll(pageable);
    }

    @PostMapping("/posts/delete")
    public String deletePost(@ModelAttribute Post post){
        postsDao.delete(postsDao.findOne(post.getId()));
        return "redirect:/posts";
    }

    @PostMapping("/posts/search")
    public String search(@RequestParam(name = "term") String term, Model vModel){
        term = "%"+term+"%";
        vModel.addAttribute("posts", postsDao.findByBodyIsLikeOrTitleIsLike(term, term));
        return "posts/results";
    }


}
