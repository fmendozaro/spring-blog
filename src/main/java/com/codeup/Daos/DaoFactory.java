package com.codeup.Daos;

import com.codeup.Repositories.Posts;
import com.codeup.Repositories.Users;
import com.codeup.models.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by Fer on 1/5/17.
 */
public class DaoFactory {

    private static Posts postsDao;
    private static Users usersDao;
    private static SessionFactory sessionFactory = Hibernate.getSessionFactory();
    private static Session session = sessionFactory.openSession();

    public static Posts getPostsListDao() {
        if (postsDao == null) {
            postsDao = new ListPosts();
        }
        return postsDao;
    }

    public static Posts getPostsDao(){
        if(postsDao == null){
            postsDao = new PostsDao(session);
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
