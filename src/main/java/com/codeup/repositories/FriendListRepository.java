package com.codeup.repositories;

import com.codeup.models.FriendList;
import com.codeup.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Fer on 1/10/17.
 */
@Repository
public interface FriendListRepository extends CrudRepository<FriendList, Long> {
    List<FriendList> findAllByStatus(String status);
}
