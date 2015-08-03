package com.matteoveroni.mydiary.model.diary;

import com.matteoveroni.mydiary.model.user.User;
import com.matteoveroni.mydiary.model.article.Article;
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
public class Diary implements Serializable {
    
    @Id 
    @GeneratedValue
    private long id;
    
    @Column
    private User owner;
    
    @Transient
    private final Map<String, Article> articles = new HashMap<>();

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    public void addArticle(String name, Article newArticle){
        articles.put(name, newArticle);
    }
    
    public Article getArticle(String name){
        if(articles.containsKey(name)){
            return articles.get(name);
        }
        return null;
    }
}
