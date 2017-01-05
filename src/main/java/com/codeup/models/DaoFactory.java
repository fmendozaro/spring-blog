package com.codeup.models;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by Fer on 1/5/17.
 */
public class DaoFactory {

    private static Posts postsDao;
    private static Users usersDao;
    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private static Session session = sessionFactory.openSession();


    public static Posts getPostsDao() {
        if (postsDao == null) {
            postsDao = new ListPosts();
        }
        return postsDao;
    }

    public static Users getUsersDao() {
        if (usersDao == null) {
            usersDao = new UsersDao(session);
        }
        return usersDao;
    }
}
