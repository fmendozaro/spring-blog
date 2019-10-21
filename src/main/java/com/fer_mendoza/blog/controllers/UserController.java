package com.fer_mendoza.blog.controllers;

/**
 * Created by Fer on 1/5/17.
 */

import com.fer_mendoza.blog.models.FriendList;
import com.fer_mendoza.blog.models.FriendStatus;
import com.fer_mendoza.blog.models.User;
import com.fer_mendoza.blog.models.UserRole;
import com.fer_mendoza.blog.services.UserService;
import com.fer_mendoza.blog.repositories.FriendListRepository;
import com.fer_mendoza.blog.repositories.UserRoles;
import com.fer_mendoza.blog.repositories.UsersRepository;
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
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoles userRoles;

    @Autowired
    private UserService usersService;

    @Autowired
    private FriendListRepository friendListRepository;

    @PostMapping("/users/create")
    public String saveUser(@Valid User user, Errors validation, Model m){

        String username = user.getUsername();
        User existingUsername = usersRepository.findByUsername(username);
        User existingEmail = usersRepository.findByEmail(user.getEmail());


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

        User newUser = usersRepository.save(user);

        UserRole ur = new UserRole();
        ur.setRole("ROLE_USER");
        ur.setUserId(newUser.getId());
        userRoles.save(ur);

        // Programmatic login after registering a user
        usersService.authenticate(user);

        m.addAttribute("user", user);
        return "redirect:/";

    }

    @GetMapping("/users/{id}")
    public String showUser(@PathVariable Long id, Model viewModel){
        User user = usersRepository.getOne(id);
        viewModel.addAttribute("user", user);
        viewModel.addAttribute("sessionUser", usersService.loggedInUser());
        viewModel.addAttribute("showEditControls", usersService.canEditProfile(user));
        return "users/show";
    }

    @GetMapping("/users/profile")
    public String showProfile(Model viewModel){
        User logUser = usersService.loggedInUser();

        if(logUser == null){
            viewModel.addAttribute("msg", "You need to be logged in to be able to see this page");
            return "error/custom";
        }

        return "redirect:/users/" + usersService.loggedInUser().getId();
    }

    @GetMapping("/users/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model viewModel){
        User user = usersRepository.getOne(id);
        viewModel.addAttribute("user", user);
        viewModel.addAttribute("showEditControls", usersService.canEditProfile(user));
        return "users/edit";
    }

    @PostMapping("/users/{id}/edit")
    public String editUser(@PathVariable Long id, @Valid User editedUser, Errors validation, Model m){

        editedUser.setId(id);

        if (validation.hasErrors()) {
            m.addAttribute("errors", validation);
            m.addAttribute("user", editedUser);
            m.addAttribute("showEditControls", checkEditAuth(editedUser));
            return "users/edit";
        }
        editedUser.setPassword(passwordEncoder.encode(editedUser.getPassword()));
        usersRepository.save(editedUser);
        return "redirect:/users/"+id;
    }

    // Edit controls are being showed up if the user is logged in and it's the same user viewing the file
    public Boolean checkEditAuth(User user){
        return usersService.isLoggedIn() && (user.getId() == usersService.loggedInUser().getId());
    }

    @GetMapping("/users/friends")
    public String showFriends(Model vModel){
        User user = usersService.loggedInUser();
        vModel.addAttribute("friendsList", user.getFriends());
        return "users/friends";
    }

    @GetMapping("/users/{id}/friend-request")
    public void sendFriendRequest(@PathVariable long id ) {
        friendListRepository.save(new FriendList(usersService.loggedInUser(), usersRepository.getOne(id), FriendStatus.PENDING));
    }

}
