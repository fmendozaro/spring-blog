package com.codeup;

import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;

/**
 * Created by Fer on 1/2/17.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public String index(Model model){
        model.addAttribute("msg", "Howdy, World");
        return "index";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doLogin(Model m, ServletRequest request) {
        m.addAttribute("username", request.getParameter("username"));
        return "home";
    }
}
