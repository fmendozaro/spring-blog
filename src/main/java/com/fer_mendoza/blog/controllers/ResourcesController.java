package com.fer_mendoza.blog.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ResourcesController {

    @Value("${fileStackApiKey}")
    private String fsAPIKey;

    @GetMapping(path = "/keys.js", produces = "application/javascript")
    @ResponseBody
    public String apikey(){
        return "const fsAPIKey='"+fsAPIKey+"'";
    }
}