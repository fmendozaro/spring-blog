package com.codeup.models;

import javax.persistence.*;

/**
 * Created by Fer on 1/10/17.
 */
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "role")
    private String role;

    public Role(int i) {
        this.id = i;
    }

    public Role() {
    }
}
