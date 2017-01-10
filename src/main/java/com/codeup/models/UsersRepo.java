package com.codeup.models;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Fer on 1/10/17.
 */
public interface UsersRepo extends CrudRepository<User, Long> {

    public User findByUsername(String username);
    public User findById(Long id);

}
