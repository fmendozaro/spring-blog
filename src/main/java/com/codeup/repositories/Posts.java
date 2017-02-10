package com.codeup.repositories;

import com.codeup.models.Post;
import com.codeup.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Fer on 1/10/17.
 */
@Repository
public interface Posts extends CrudRepository<Post, Long> {

    public Post findByUser(User user);
    public Post findById(Long id);
    public Page<Post> findAll(Pageable pageable);

    //Find posts between today and 3 days ahead
    public List<Post> findByCreateDateBetween(Date from, Date to);

}
