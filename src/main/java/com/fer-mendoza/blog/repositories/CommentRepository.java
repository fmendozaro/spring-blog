package us.tacos4.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.tacos4.blog.models.Comment;

import java.util.List;

/**
 * Created by Fer on 2/27/17.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByParent(Comment parent);
}
