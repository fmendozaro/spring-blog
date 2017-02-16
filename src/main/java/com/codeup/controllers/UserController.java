package com.codeup.controllers;

/**
 * Created by Fer on 1/5/17.
 */

import com.codeup.models.User;
import com.codeup.models.UserRole;
import com.codeup.repositories.Roles;
import com.codeup.repositories.UserRoles;
import com.codeup.repositories.Users;
import com.codeup.services.UserSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    Users usersDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    Roles roles;

    @Autowired
    UserRoles userRoles;

    @Autowired
    UserSvc userSvc;

    @PostMapping("users/create")
    public String saveUser(@Valid User user, Errors validation, Model m){

        if (validation.hasErrors()) {
            m.addAttribute("errors", validation);
            m.addAttribute("user", user);
            return "users/create";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User newUser = usersDao.save(user);

        UserRole ur = new UserRole();
        ur.setRole("ROLE_USER");
        ur.setUserId(newUser.getId());
        userRoles.save(ur);

        m.addAttribute("user", user);
        return "redirect:/login";
    }

    @GetMapping("users/{id}")
    public String showUser(@PathVariable Long id, Model m){
        User user = usersDao.findById(id);
        m.addAttribute("user", user);
        m.addAttribute("showEditControls", userSvc.isLoggedIn() && user.getUsername() == userSvc.loggedInUser().getUsername());
        return "users/show";
    }

}
