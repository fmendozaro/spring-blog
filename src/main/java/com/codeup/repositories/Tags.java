package com.codeup.repositories;

import com.codeup.models.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Fer on 2/27/17.
 */
@Repository
public interface Tags extends CrudRepository<Tag, Long>{

}
