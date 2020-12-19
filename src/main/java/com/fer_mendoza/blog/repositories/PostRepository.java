package com.fer_mendoza.blog.repositories;

import com.fer_mendoza.blog.models.Post;
import com.fer_mendoza.blog.models.Tag;
import com.fer_mendoza.blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Fer on 1/10/17.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Post findByUser(User user);

    // Search by body or title
    List<Post> findByBodyIsLikeOrTitleIsLike(String term, String term2);

    //Find posts between today and 3 days ahead
    List<Post> findByCreateDateBetween(Date from, Date to);

    @Query("select p.createDate from Post p")
    List<Date> createDates();

    // Get the list of posts in reverse order.
    @Query("select p from Post p order by p.id desc")
    List<Post> postsInReverse();

//    @Query("delete from post_tags where post_id = :postId and tag_id = :tagId")
//    boolean deleteTagFromPost(@Param("postId") long postId, @Param("tagId") long tagId);

    List<Post> findAllByTagsIn(List<Tag> tags);

}
