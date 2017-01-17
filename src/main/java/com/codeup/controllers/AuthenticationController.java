package com.codeup.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Fer on 1/10/17.
 */
@Controller
public class AuthenticationController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
