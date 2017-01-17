package com.codeup.Repositories;

import com.codeup.models.User;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Fer on 1/5/17.
 */
public interface Users {

    void save(User u);

}
