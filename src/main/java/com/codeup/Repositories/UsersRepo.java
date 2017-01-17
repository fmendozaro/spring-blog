package com.codeup.Repositories;

import com.codeup.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Fer on 1/10/17.
 */
@Repository
public interface UsersRepo extends CrudRepository<User, Long> {

    public User findByUsername(String username);
    public User findById(Long id);

}
