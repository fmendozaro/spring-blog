package com.codeup.Daos;

import com.codeup.Repositories.Users;
import com.codeup.models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Created by Fer on 1/5/17.
 */
public class UsersDao implements Users {
    private Session session;

    public UsersDao(Session session) {
        this.session = session;
    }

    @Override
    public void save(User user) {
        Transaction tx = session.beginTransaction();
        session.save(user);
        tx.commit();
    }



}
