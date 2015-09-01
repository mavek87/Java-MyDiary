package com.matteoveroni.mydiary.article.model;

import java.util.Date;

/**
 *
 * @author Matteo Veroni
 */
public interface ArticleAAA {

    public long getId();

    public void setId(long id);

    public String getTitle();

    public void setTitle(String title);

    public String getMessage();

    public void setMessage(String message);

    public String getAuthor();

    public void setAuthor(String author);

    public Date getDate();

    public void setDate(Date date);
}
