package com.codeup.Daos;

import com.codeup.Repositories.Posts;
import com.codeup.models.Post;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by Fer on 1/6/17.
 */
public class PostsDao implements Posts {
    private Session session;

    public PostsDao(Session session){
        this.session = session;
    }

    @Override
    public List<Post> all() {
        Query query = session.createQuery("from Post order by id desc");
        return query.list();
    }

    @Override
    public void save(Post p) {
        Transaction tx = session.beginTransaction();
        session.save(p);
        tx.commit();
    }

    @Override
    public void update(Post p) {
        Transaction tx = session.beginTransaction();
        session.update(p);
        tx.commit();
    }

    @Override
    public Post getById(int id) {
        Query query = session.createQuery("from Post where id = :id");
        query.setParameter("id", id);
        return (Post) query.getSingleResult();
    }

    @Override
    public List<Post> generatePosts() {
        return null;
    }

}
