package com.codeup.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Fer on 1/5/17.
 */

@Entity
@Table(name="posts")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "in_only_test",
                procedureName = "in_only_test",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "inParam1", type = String.class)
                })
//        @NamedStoredProcedureQuery(name = "in_and_out_test",
//                procedureName = "test_pkg.in_and_out_test",
//                parameters = {
//                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "inParam1", type = String.class),
//                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "outParam1", type = String.class)
//
//                })
}) // Store procedure test
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title can't be empty.")
    @Size(min = 3, message = "A title must be at least 3 characters.")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Body can't be empty")
    @Column(nullable = false, length = 3000)
    private String body;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date")
    private Date modifyDate;

    @Column(nullable = true, name="image_url")
    private String imageUrl;

    @ManyToOne
    @JsonManagedReference
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="post_tags",
            joinColumns={@JoinColumn(name="post_id")},
            inverseJoinColumns={@JoinColumn(name="tag_id")}
    )
    private List<Tag> tags;

    public Post(Long id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public Post() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString(){
        return "Title: "+ this.getTitle() + " Body: "+ this.getBody();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }


}
