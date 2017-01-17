package com.codeup.Repositories;

import com.codeup.models.Post;
import com.codeup.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Fer on 1/10/17.
 */
@Repository
public interface PostsRepo extends CrudRepository<Post, Long> {

    public Post findByUser(User user);
    public Post findById(int id);
    public Page<Post> findAll(Pageable pageable);

}
