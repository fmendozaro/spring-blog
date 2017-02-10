package com.codeup.models;

import javax.persistence.*;

/**
 * Created by Fer on 2/7/17.
 */

@Entity
@Table(name = "ads")
public class Ad {

    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 100)
    String title;

    @Column(nullable = false, length = 2000)
    String body;


    public Ad(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Ad(){

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
}
