package com.matteoveroni.mydiary.diary.model.bean;

import com.matteoveroni.mydiary.annotation.model.bean.Annotation;
import com.matteoveroni.mydiary.user.model.bean.UserData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
    private String name;

    @ManyToOne
    private UserData ownerUser;

    @OneToMany
    private List<Annotation> annotations = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserData getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(UserData ownerUser) {
        this.ownerUser = ownerUser;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }

//    @Transient
//    private final Map<String, Annotation> articles = new HashMap<>();
    
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
