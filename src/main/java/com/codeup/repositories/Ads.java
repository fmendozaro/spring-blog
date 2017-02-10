package com.codeup.repositories;

import com.codeup.models.Ad;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Fer on 2/9/17.
 */
@Repository
public interface Ads extends CrudRepository<Ad, Long> {

}
