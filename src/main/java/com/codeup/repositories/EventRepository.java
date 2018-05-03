package com.codeup.repositories;

import com.codeup.models.Event;
import com.codeup.models.Post;
import com.codeup.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Fer on 1/10/17.
 */
@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

}
