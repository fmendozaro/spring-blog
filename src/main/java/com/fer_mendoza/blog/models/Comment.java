package com.fer_mendoza.blog.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import com.fer_mendoza.blog.repositories.CommentRepository;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comments")
public class Comment {

    @Autowired
    @Transient
    private CommentRepository commentRepository;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String body;

    @ManyToOne
    @JsonManagedReference
    private User user;

    @ManyToOne
    @JsonManagedReference
    private Comment parent;

    @ManyToOne
    @JsonManagedReference
    private Post post;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column()
    private Date createdAt;

    @OneToMany(mappedBy = "parent")
    @JsonBackReference
    private List<Comment> children;

    public Comment(){}

    public Comment(long id, String body, User user, Comment parent, Post post, Date createdAt) {
        this.id = id;
        this.body = body;
        this.user = user;
        this.parent = parent;
        this.post = post;
        this.createdAt = createdAt;
        setChildren();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Comment> getChildren() {
        return children;
    }

    public void setChildren() {
        System.out.println("setChildren ran");
        this.children = commentRepository.findByParent(this);
    }
}
