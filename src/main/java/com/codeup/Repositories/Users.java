package com.codeup.Repositories;

import com.codeup.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Fer on 1/10/17.
 */
@Repository
public interface Users extends CrudRepository<User, Long> {

    public User findByUsername(String username);
    public User findById(Long id);

    public User findByEmail(String email);

    //Same function of above but with HQL
    @Query("select u from User u where u.email = ?1")
    public User findByEmailQuery(String email);


}
