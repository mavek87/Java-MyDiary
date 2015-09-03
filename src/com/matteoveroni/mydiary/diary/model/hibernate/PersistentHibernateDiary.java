package com.matteoveroni.mydiary.diary.model.hibernate;

import com.matteoveroni.mydiary.article.model.Article;
import com.matteoveroni.mydiary.diary.model.Diary;
import com.matteoveroni.mydiary.user.model.User;
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
public class PersistentHibernateDiary implements Serializable, Diary {
    
    @Id 
    @GeneratedValue
    private long id;
    
    @Column
    private User owner;
    
    @Transient
    private final Map<String, Article> articles = new HashMap<>();

    @Override
    public User getOwner() {
        return owner;
    }

    @Override
    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    @Override
    public void addArticle(String name, Article newArticle){
        articles.put(name, newArticle);
    }
    
    @Override
    public Article getArticle(String name){
        if(articles.containsKey(name)){
            return articles.get(name);
        }
        return null;
    }
}
