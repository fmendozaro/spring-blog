package com.codeup.models;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Fer on 1/10/17.
 */
public interface PostsRepo extends CrudRepository<Post, Long> {

    public Post findByOwner(User user);
    public Post findById(Long id);

}
