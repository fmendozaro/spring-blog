package com.codeup.models;

public class Event {

    private String title;
    private String start;

    public Event(String title, String start) {
        this.title = title;
        this.start = start;
    }

    public Event() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }
}
