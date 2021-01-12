package com.fer_mendoza.blog.controllers;

import com.fer_mendoza.blog.models.Event;
import com.fer_mendoza.blog.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by fer on 7/10/17.
 */
@Controller
public class TestsController {

    @Value("${google-maps-key}")
    private String mapsKey;

    @Value("${app.url}")
    private String appUrl;

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

    @GetMapping("/restaurants")
    @CrossOrigin
    @ResponseBody
    public String getRestaurants(){
        String restaurants = "";
        Path jsonPath = Paths.get( "restaurants.json");
        try {
            List<String> jsonContent = Files.readAllLines(jsonPath);
            for (String line : jsonContent){
                restaurants += line;
            }
        } catch (IOException e) {
                e.printStackTrace();
        }

        return restaurants;
    }

    @GetMapping("/show-maps")
    public String showMap(Model vModel){
        vModel.addAttribute("mapkey", mapsKey);
        return "demos/map";
    }

    @GetMapping("/test-sessions")
    public String testSessions(Model vModel){

        vModel.addAttribute("mapkey", mapsKey);
        return "demos/map";
    }

    @GetMapping("/testPathContext")
    @ResponseBody
    public String testPathContext(){
        return "app.url = " + appUrl;
    }
}