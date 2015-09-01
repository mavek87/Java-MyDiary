package com.matteoveroni.mydiary.diary.model;

import com.matteoveroni.mydiary.user.ApplicationUser;
import com.matteoveroni.mydiary.article.model.hibernate.PersistentHibernateArticle;
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
    private ApplicationUser owner;
    
    @Transient
    private final Map<String, PersistentHibernateArticle> articles = new HashMap<>();

    public ApplicationUser getOwner() {
        return owner;
    }

    public void setOwner(ApplicationUser owner) {
        this.owner = owner;
    }
    
    public void addArticle(String name, PersistentHibernateArticle newArticle){
        articles.put(name, newArticle);
    }
    
    public PersistentHibernateArticle getArticle(String name){
        if(articles.containsKey(name)){
            return articles.get(name);
        }
        return null;
    }
}
