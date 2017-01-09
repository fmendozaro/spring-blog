package com.codeup.models;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Created by Fer on 1/5/17.
 */
public class Hibernate {

    public static SessionFactory getSessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build()
                ;

        return new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory()
                ;
    }
}
