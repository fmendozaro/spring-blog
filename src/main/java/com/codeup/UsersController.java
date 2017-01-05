package com.codeup;

/**
 * Created by Fer on 1/5/17.
 */

import com.codeup.models.DaoFactory;
import com.codeup.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {

    @GetMapping("users/create")
    public String saveUser(Model m){
        m.addAttribute("user", new User());
        return "users/create";
    }

    @PostMapping("users/create")
    public String saveUser(@ModelAttribute User user, Model m){
        DaoFactory.getUsersDao().save(user);
        m.addAttribute("user", user);
        return "users/create";
    }

}
