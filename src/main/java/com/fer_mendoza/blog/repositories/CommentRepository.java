package com.fer_mendoza.blog.repositories;

import com.fer_mendoza.blog.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Fer on 2/27/17.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByParent(Comment parent);
}
