package com.codeup.services;

import com.codeup.models.Ad;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("adSvc")
public class AdSvc {

//    private List<Ad> ads = new ArrayList<>();
//
//    public AdSvc() {
//        //This run by the time the class is created
//        createAds();
//    }
//
//    public List<Ad> findAll() {
//        return ads;
//    }
//
//    public Ad save(Ad ad) {
//        ad.setId(ads.size()+1);
//        ads.add(ad);
//        return ad;
//    }
//
//    public Ad findOne(int id) {
//        return ads.get( id - 1);
//    }
//
//    private void createAds() {
//        // create some ad objects and add them to the ads list
//        // with the save method
//        for(int i=0;i<5;i++){
//            save(new Ad("title " + (i+1), "Some body content"+ (i+2) ));
//        }
//    }
}