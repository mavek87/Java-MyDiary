package com.matteoveroni.mydiary.diary.model.bean;

import com.matteoveroni.mydiary.annotation.model.bean.Annotation;
import com.matteoveroni.mydiary.user.model.bean.User;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 *
 * @author Matteo Veroni
 */

@Entity
public class HibernateDiaryBean implements Serializable, Diary {
    
    @Id 
    @GeneratedValue
    private long id;
    
    @Column
    private User owner;
    
    @Transient
    private final Map<String, Annotation> articles = new HashMap<>();

    @Override
    public User getOwner() {
        return owner;
    }

    @Override
    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    @Override
    public void addArticle(String name, Annotation newArticle){
        articles.put(name, newArticle);
    }
    
    @Override
    public Annotation getArticle(String name){
        if(articles.containsKey(name)){
            return articles.get(name);
        }
        return null;
    }
}
