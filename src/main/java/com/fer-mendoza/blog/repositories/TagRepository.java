package us.tacos4.blog.repositories;

import us.tacos4.blog.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Fer on 2/27/17.
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

}
