package com.codeup.controllers;

/**
 * Created by Fer on 1/5/17.
 */

import com.codeup.models.User;
import com.codeup.repositories.Roles;
import com.codeup.repositories.UserRoles;
import com.codeup.repositories.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("users")
public class UsersController extends BaseController {

    @Autowired
    Users usersDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    Roles roles;

    @PostMapping("/create")
    public String saveUser(@ModelAttribute User user, Model m){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //user.setRole(roles.findByRole("ROLE_USER"));
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
