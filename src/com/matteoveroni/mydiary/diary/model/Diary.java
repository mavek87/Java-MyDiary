package com.matteoveroni.mydiary.diary.model;

import com.matteoveroni.mydiary.article.model.Article;
import com.matteoveroni.mydiary.user.ApplicationUser;

/**
 *
 * @author Matteo Veroni
 */
public interface Diary {

    public ApplicationUser getOwner();

    public void setOwner(ApplicationUser owner);

    public void addArticle(String name, Article newArticle);

    public Article getArticle(String name);
}
