package com.fer_mendoza.blog.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Fer on 1/3/17.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 20, unique = true)
    @NotBlank(message = "Username can't be empty")
    private String username;

    @Column(nullable = false, unique = true)
    @Email(message = "Invalid email")
    @NotBlank(message = "Email can't be empty")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Password can't be empty")
    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<FriendList> friends;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonBackReference
    private List<Comment> comments;

    public User() {
    }

    public User(String username, String email, String password, List<FriendList> friends) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.friends = friends;
    }

    // Copy constructor an alternative for clone
    public User(User user) {
        this.id = user.id;
        this.username = user.username;
        this.password = user.password;
        this.email = user.email;
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

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<FriendList> getFriends() {
        return friends;
    }

    public void setFriends(List<FriendList> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return String.format("%s email is %s and its ID is %d", username, email, id);
    }

}