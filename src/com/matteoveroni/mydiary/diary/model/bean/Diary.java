package com.matteoveroni.mydiary.diary.model.bean;

import com.matteoveroni.mydiary.annotation.model.bean.Annotation;
import com.matteoveroni.mydiary.user.model.bean.UserData;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Matteo Veroni
 */
@Entity
public class Diary implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private UserData ownerUser;

//    @Transient
//    private final Map<String, Annotation> articles = new HashMap<>();
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserData getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(UserData ownerUser) {
        this.ownerUser = ownerUser;
    }

//    @Override
//    public void addArticle(String name, Annotation newArticle) {
//        articles.put(name, newArticle);
//    }

//    @Override
//    public Annotation getArticle(String name) {
//        if (articles.containsKey(name)) {
//            return articles.get(name);
//        }
//        return null;
//    }
}
