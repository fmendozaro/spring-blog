package com.fer_mendoza.blog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fer on 1/4/17.
 */
@Controller
public class Lectures {
    @GetMapping("/formatting")
    public String formatting(Model m){
        m.addAttribute("productName", "500GB PS4");
        m.addAttribute("productPrice", 1234.3436);
        m.addAttribute("productDate", "04/01/2017");
        return "demos/formatting";
    }

    @GetMapping("/conditions/{num}")
    public String conditions(@PathVariable String num, Model m){
        m.addAttribute("status", num);
        m.addAttribute("number", 1);
        return "demos/conditions";
    }

    @GetMapping("/demo")
    public String showDefault(Model model){

        List<String> names = new ArrayList<>();

        names.add("Fer");
        names.add("Luis");
        names.add("Ryan");
        names.add("Zach");
        names.add("Justin");

        //Passing all the attributes to my view using the model
        //This is a String
        model.addAttribute("date", "Feb 7th");
        //This is an Integer
        model.addAttribute("age", 26);
        //This is a list
        model.addAttribute("ListNames", names);

        return "demos/default";
    }

    @GetMapping("/calendar")
    public String showCAl(){
        return "demos/calendar-example";
    }

    @GetMapping("/filestack")
    public String showFileS(){
        return "demos/filestack";
    }


}
