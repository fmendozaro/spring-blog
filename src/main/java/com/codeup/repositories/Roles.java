package com.codeup.repositories;

import com.codeup.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Fer on 2/13/17.
 */

@Repository
public interface Roles extends CrudRepository<Role, Long> {
    public Role findByRole(String role);
}
