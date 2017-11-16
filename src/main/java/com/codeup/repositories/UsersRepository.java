package com.codeup.repositories;

import com.codeup.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Fer on 1/10/17.
 */
@Repository
public interface UsersRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
    User findById(Long id);
    User findByEmail(String email);

    //Same function of above but with HQL
    @Query("select u from User u where u.email = ?1")
    User findByEmailQuery(String email);



}
