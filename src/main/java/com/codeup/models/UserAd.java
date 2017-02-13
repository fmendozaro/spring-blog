package com.codeup.models;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Fer on 2/10/17.
 */
@Entity
@Table(name = "ad_users")
public class UserAd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    String username;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String password;

    @OneToMany(mappedBy = "user")
    List<Ad> ads;

    public UserAd() {
    }

    public UserAd(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
