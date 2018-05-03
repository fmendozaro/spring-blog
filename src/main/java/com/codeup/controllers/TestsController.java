package com.codeup.controllers;

import com.codeup.models.Event;
import com.codeup.repositories.EventRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by fer on 7/10/17.
 */
@Controller
public class TestsController {

    EventRepository eventRepo;

    public TestsController (EventRepository eventRepo){
        this.eventRepo = eventRepo;
    }

    @GetMapping("/linkedin")
    public String linkedingLogin(){
        return "demos/linkedinAuth";
    }

    @GetMapping("oauth/linkedin/callback")
    public String linkedingLogin(@RequestParam String code, @RequestParam String state){

        return "demos/linkedinAuth";
    }

    @GetMapping("/postsCalendar")
    public String showCalendar(){
        return "demos/fullCalendar";
    }

    @GetMapping("/save-event")
    public String showEventForm(Model viewModel){
        viewModel.addAttribute("event", new Event());
        return "demos/save-event";
    }

    @PostMapping("/save-event")
    public String saveEvent(@ModelAttribute Event event){
        eventRepo.save(event);
        return "demos/save-event";
    }

}