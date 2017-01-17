package com.codeup.controllers;

/**
 * Created by Fer on 1/5/17.
 */

import com.codeup.models.User;
import com.codeup.Repositories.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("users")
public class UsersController extends BaseController {

    @Autowired
    UsersRepo usersDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String saveUser(Model m){
        m.addAttribute("user", new User());
        return "users/create";
    }

    @PostMapping("/create")
    public String saveUser(@ModelAttribute User user, Model m){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersDao.save(user);
        m.addAttribute("user", user);
        return "users/show";
    }

    @GetMapping("/{id}")
    public String saveUser(@PathVariable Long id, Model m){
        User user = usersDao.findById(id);
        m.addAttribute("user", user);
        m.addAttribute("showEditControls", isLoggedIn() && user.getUsername() == loggedInUser().getUsername());
        return "users/show";
    }

}
