package com.codeup.controllers;

/**
 * Created by Fer on 1/5/17.
 */

import com.codeup.models.User;
import com.codeup.Repositories.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("users")
public class UsersController extends BaseController {

    @Autowired
    Users usersDao;

    @Value("${yelp-consumer-key}")
    private String yelpCKey;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showForm(Model m){
        JSONController json = new JSONController(yelpCKey);
        m.addAttribute("user", new User());
        return "users/create";
    }

    @PostMapping("/create")
    public String saveUser(@ModelAttribute User user, Model m){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersDao.save(user);
        m.addAttribute("user", user);
        return "redirect:/login";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable Long id, Model m){
        User user = usersDao.findById(id);
        m.addAttribute("user", user);
        m.addAttribute("showEditControls", isLoggedIn() && user.getUsername() == loggedInUser().getUsername());
        return "users/show";
    }

}
