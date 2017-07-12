package com.codeup.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by fer on 7/10/17.
 */
@Controller
public class TestsController {

    @GetMapping("/linkedin")
    public String linkedingLogin(){
        return "demos/linkedinAuth";
    }

    @GetMapping("oauth/linkedin/callback")
    public String linkedingLogin(@RequestParam String code, @RequestParam String state){

        return "demos/linkedinAuth";
    }

}
