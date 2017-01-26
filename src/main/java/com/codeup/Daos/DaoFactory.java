package com.codeup.Daos;

import com.codeup.Repositories.PostsWithLists;
import com.codeup.models.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by Fer on 1/5/17.
 */
public class DaoFactory {

    private static PostsWithLists postsWithListsDao;
    private static SessionFactory sessionFactory = Hibernate.getSessionFactory();
    private static Session session = sessionFactory.openSession();

    public static PostsWithLists getPostsListDao() {
        if (postsWithListsDao == null) {
            postsWithListsDao = new ListPosts();
        }
        return postsWithListsDao;
    }

}
