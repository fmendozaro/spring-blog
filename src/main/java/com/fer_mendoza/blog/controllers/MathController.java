package com.fer_mendoza.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Fer on 1/2/17.
 */
@Controller
public class MathController {
    @GetMapping("/add/{a}/and/{b}")
    @ResponseBody
    public double add(@PathVariable double a, @PathVariable double b){
        return a+b;
    }

    @GetMapping("/subtract/{a}/from/{b}")
    @ResponseBody
    public double subtract(@PathVariable double a, @PathVariable double b){
        return b-a;
    }

    @GetMapping("/multiply/{a}/and/{b}")
    @ResponseBody
    public double multiply(@PathVariable double a, @PathVariable double b){
        return a*b;
    }

    @GetMapping("/divide/{a}/by/{b}")
    @ResponseBody
    public double divide(@PathVariable double a, @PathVariable double b){
        return a/b;
    }
}
