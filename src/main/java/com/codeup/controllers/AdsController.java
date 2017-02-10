package com.codeup.controllers;

import com.codeup.models.Ad;
import com.codeup.repositories.Ads;
import com.codeup.services.AdSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
class AdsController {

    @Autowired
    Ads adsDao;

    @GetMapping("/ads")
    public String ads(Model m){
        m.addAttribute("ads", adsDao.findAll());
        //another way to to the same thing without using variables
        //m.addAttribute("ads", adSvc.findAll());
        return "demos/ads/index";
    }

    @GetMapping("/ads/{id}")
    public String getAd(@PathVariable Long id, Model m){
        m.addAttribute("ad", adsDao.findOne( id ));
        return "demos/ads/show";
    }

    @GetMapping("ads/create")
    public String showForm(@ModelAttribute Ad ad, Model vModel){
        vModel.addAttribute("msg","");
        return "demos/ads/create";
    }

    @PostMapping("ads/create")
    public String createAd(@ModelAttribute Ad ad, Model vModel){

        adsDao.save(ad);
        //vModel.addAttribute("msg", "Done!");
        //return "demos/ads/create";
        return "redirect:/ads";

    }


}