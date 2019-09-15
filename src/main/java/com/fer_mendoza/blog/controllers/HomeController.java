package com.fer_mendoza.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Fer on 1/2/17.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("msg", "Howdy, World");
        return "index";
    }
}
