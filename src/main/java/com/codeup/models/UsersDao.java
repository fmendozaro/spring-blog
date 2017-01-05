package com.codeup.models;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

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
