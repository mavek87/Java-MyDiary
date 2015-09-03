package com.matteoveroni.mydiary.diary.model;

import com.matteoveroni.mydiary.article.model.Article;
import com.matteoveroni.mydiary.user.model.User;

/**
 *
 * @author Matteo Veroni
 */
public interface Diary {

    public User getOwner();

    public void setOwner(User owner);

    public void addArticle(String name, Article newArticle);

    public Article getArticle(String name);
}
