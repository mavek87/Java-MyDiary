package com.matteoveroni.mydiary.diary.model.bean;

import com.matteoveroni.mydiary.annotation.model.bean.Annotation;
import com.matteoveroni.mydiary.user.model.bean.User;

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
