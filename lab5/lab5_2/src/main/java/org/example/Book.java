package org.example;

import java.util.Date;

public class Book {
    private final String title;
    private final String author;
    private final Date published;

    public Book(String title, String author, Date published) {
        this.title = title;
        this.author = author;
        this.published = published;
    }

    public Date getPublished() {
        return published;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

}
