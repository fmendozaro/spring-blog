package com.codeup.repositories;

import com.codeup.models.Post;

import java.util.List;

/**
 * Created by Fer on 1/5/17.
 */
public interface PostsWithLists {

    List<Post> all();
    void save(Post p);
    void update(Post p);
    Post getById(int id);
    List<Post> generatePosts();

}
