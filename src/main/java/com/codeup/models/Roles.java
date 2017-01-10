package com.codeup.models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Fer on 1/10/17.
 */
public interface Roles extends CrudRepository<UserRole, Long> {
    @Query("select ur.role from UserRole ur, User u where u.username=?1 and ur.userId = u.id")
    public List<String> ofUserWith(String username);
}
