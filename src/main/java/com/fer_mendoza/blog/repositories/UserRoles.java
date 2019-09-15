package com.fer_mendoza.blog.repositories;

import com.fer_mendoza.blog.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by Fer on 1/10/17.
 */
@Repository
public interface UserRoles extends JpaRepository<UserRole, Long> {
//    @Query("select r.role from Role r, User u where u.username=?1 and r.id = u.role.id")
//    public List<String> ofUserWith(String username);

    @Query("select ur.role from UserRole ur, User u where u.username=?1 and ur.userId = u.id")
    List<String> ofUserWith(String username);
}
