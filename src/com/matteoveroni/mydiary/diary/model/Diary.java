package com.matteoveroni.mydiary.diary.model;

import com.matteoveroni.mydiary.article.model.Article;
import com.matteoveroni.mydiary.user.model.hibernate.PersistentHibernateUser;

/**
 *
 * @author Matteo Veroni
 */
public interface Diary {

    public PersistentHibernateUser getOwner();

    public void setOwner(PersistentHibernateUser owner);

    public void addArticle(String name, Article newArticle);

    public Article getArticle(String name);
}
