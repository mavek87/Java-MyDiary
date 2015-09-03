package com.matteoveroni.mydiary.diary.model;

import com.matteoveroni.mydiary.article.model.Article;
import com.matteoveroni.mydiary.user.model.hibernate.HibernateUser;

/**
 *
 * @author Matteo Veroni
 */
public interface Diary {

    public HibernateUser getOwner();

    public void setOwner(HibernateUser owner);

    public void addArticle(String name, Article newArticle);

    public Article getArticle(String name);
}
