package com.matteoveroni.mydiary.diary.model;

import com.matteoveroni.mydiary.article.model.Article;
import com.matteoveroni.mydiary.user.UserData;

/**
 *
 * @author Matteo Veroni
 */
public interface Diary {

    public UserData getOwner();

    public void setOwner(UserData owner);

    public void addArticle(String name, Article newArticle);

    public Article getArticle(String name);
}
