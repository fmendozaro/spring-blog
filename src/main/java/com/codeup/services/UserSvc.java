package com.codeup.services;

import com.codeup.models.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by Fer on 1/11/17.
 */
@Service("usersSvc")
public class UserSvc {

    public boolean isLoggedIn() {
        boolean isAnonymousUser =
                SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
        return ! isAnonymousUser;
    }

    public User loggedInUser() {
        if (! isLoggedIn()) {
            return null;
        }
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    // Checks if the user is the owner of the post
    public boolean isOwner(User postUser){
        return (postUser.getUsername().equals(loggedInUser().getUsername()));
    }


    // Edit controls are being showed up if the user is logged in and it's the same user viewing the file
    public boolean canEditProfile(User profileUser){
        return isLoggedIn() && (profileUser.getId() == loggedInUser().getId());
    }

}