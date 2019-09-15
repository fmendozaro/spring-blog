package com.fer_mendoza.blog.repositories;

import com.fer_mendoza.blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Fer on 1/10/17.
 */
@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findByEmail(String email);

    //Same function of above but with HQL
    @Query("select u from User u where u.email = ?1")
    User findByEmailQuery(String email);



}
