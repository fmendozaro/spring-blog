package com.fer_mendoza.blog.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    // This runs every 5 seconds.
//    @Scheduled(fixedRate = 5000)
//    public void reportCurrentTime() {
//        log.info("The time is now {}", dateFormat.format(new Date()));
//    }

    // this runs everyday at 5:18 pm
    @Scheduled(cron = "0 18 15 * * *")
    public void checkPostsCreated() {

        // Bring a list of all the posts created today.
        log.info("The time executed", dateFormat.format(new Date()));
    }


}
