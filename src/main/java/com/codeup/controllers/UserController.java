package com.codeup.controllers;

/**
 * Created by Fer on 1/5/17.
 */

import com.codeup.models.User;
import com.codeup.models.UserRole;
import com.codeup.repositories.UserRoles;
import com.codeup.repositories.UsersRepository;
import com.codeup.services.UserService;
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
    UsersRepository usersDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRoles userRoles;

    @Autowired
    UserService usersService;

    @PostMapping("users/create")
    public String saveUser(@Valid User user, Errors validation, Model m){

        String username = user.getUsername();
        User existingUsername = usersDao.findByUsername(username);
        User existingEmail = usersDao.findByEmail(user.getEmail());


        if(existingUsername != null){

            validation.rejectValue("username", "user.username", "Duplicated username " + username);

        }

        if(existingEmail != null){

            validation.rejectValue("email", "user.email", "Duplicated email " + user.getEmail());

        }

        if (validation.hasErrors()) {
            m.addAttribute("errors", validation);
            m.addAttribute("user", user);
            return "users/create";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Custom validation if the username is taken

        User newUser = usersDao.save(user);

        UserRole ur = new UserRole();
        ur.setRole("ROLE_USER");
        ur.setUserId(newUser.getId());
        userRoles.save(ur);

        // Programmatic login after registering a user
        usersService.authenticate(user);

        m.addAttribute("user", user);
        return "redirect:/";

    }

    @GetMapping("users/{id}")
    public String showUser(@PathVariable Long id, Model viewModel){
        User user = usersDao.findOne(id);
        viewModel.addAttribute("user", user);
        viewModel.addAttribute("showEditControls", usersService.canEditProfile(user));
        return "users/show";
    }

    @GetMapping("users/profile")
    public String showProfile(Model viewModel){
        User logUser = usersService.loggedInUser();

        if(logUser == null){
            viewModel.addAttribute("msg", "You need to be logged in to be able to see");
            return "error/custom";
        }

        return "redirect:/users/" + usersService.loggedInUser().getId();
    }

    @GetMapping("users/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model viewModel){
        User user = usersDao.findOne(id);
        viewModel.addAttribute("user", user);
        viewModel.addAttribute("showEditControls", usersService.canEditProfile(user));
        return "users/edit";
    }

    @PostMapping("users/{id}/edit")
    public String editUser(@PathVariable Long id, @Valid User editedUser, Errors validation, Model m){

        editedUser.setId(id);

        if (validation.hasErrors()) {
            m.addAttribute("errors", validation);
            m.addAttribute("user", editedUser);
            m.addAttribute("showEditControls", checkEditAuth(editedUser));
            return "users/edit";
        }
        editedUser.setPassword(passwordEncoder.encode(editedUser.getPassword()));
        usersDao.save(editedUser);
        return "redirect:/users/"+id;
    }

    // Edit controls are being showed up if the user is logged in and it's the same user viewing the file
    public Boolean checkEditAuth(User user){
        return usersService.isLoggedIn() && (user.getId() == usersService.loggedInUser().getId());
    }


}
