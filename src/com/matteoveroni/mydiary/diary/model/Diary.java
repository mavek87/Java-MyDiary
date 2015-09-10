package com.matteoveroni.mydiary.diary.model;

import com.matteoveroni.mydiary.annotation.model.Annotation;
import com.matteoveroni.mydiary.user.model.User;

/**
 *
 * @author Matteo Veroni
 */
public interface Diary {

    public User getOwner();

    public void setOwner(User owner);

    public void addArticle(String name, Annotation newArticle);

    public Annotation getArticle(String name);
}
