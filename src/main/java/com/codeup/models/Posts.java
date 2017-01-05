package com.codeup.models;

import java.util.List;

/**
 * Created by Fer on 1/5/17.
 */
public interface Posts {

    List<Post> all();
    void insert(Post p);
    Post getById(int id);
    List<Post> generatePosts();

}
