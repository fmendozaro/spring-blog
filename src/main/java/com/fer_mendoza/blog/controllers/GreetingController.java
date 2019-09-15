package com.fer_mendoza.blog.controllers;

/**
 * Created by Fer on 1/2/17.
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GreetingController {

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public String showGreeting(@RequestParam(value="name") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping(path = "/hello/{name}", method = RequestMethod.GET)
    @ResponseBody
    public String sayHello(@PathVariable String name){
        return "<h3>Hello "+name+"</h3>";
    }

}
