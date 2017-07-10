package com.codeup.repositories;

import com.codeup.models.Post;
import com.codeup.models.User;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Fer on 1/10/17.
 */
@Repository
public interface Posts extends CrudRepository<Post, Long> {

    Post findByUser(User user);
    Post findById(Long id);
    List<Post> findByBodyIsLikeOrTitleIsLike(String term, String term2);

    //Find posts between today and 3 days ahead
    List<Post> findByCreateDateBetween(Date from, Date to);

    // Store procedures test
    @Procedure(name = "in_only_test")
    String inOnlyTest(@Param("inParam1") String inParam1);

//    @Procedure(name = "in_and_out_test")
//    String inAndOutTest(@Param("inParam1") String inParam1);

}
