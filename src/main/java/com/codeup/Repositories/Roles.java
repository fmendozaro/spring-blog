package com.codeup.Repositories;

import com.codeup.models.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Fer on 1/10/17.
 */
@Repository
public interface Roles extends CrudRepository<Role, Long> {
    @Query("select r.role from Role r, User u where u.username=?1 and r.id = u.role.id")
    public List<String> ofUserWith(String username);
}
