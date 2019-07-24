package us.tacos4.blog.repositories;

import us.tacos4.blog.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Fer on 1/10/17.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}
