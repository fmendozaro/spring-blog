package com.fer_mendoza.blog.repositories;

import com.fer_mendoza.blog.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Fer on 2/27/17.
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

}
