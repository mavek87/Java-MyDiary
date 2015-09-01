package com.matteoveroni.mydiary.article.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;

/**
 *
 * @author Matteo Veroni
 */
@Entity
public class Article implements Serializable, ArticleAAA {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String title;

    @Column(columnDefinition = "CLOB")
    @Lob
    private String message;

    @Column
    private String author;

    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }
}
