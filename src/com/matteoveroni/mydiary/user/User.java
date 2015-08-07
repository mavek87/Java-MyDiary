package com.matteoveroni.mydiary.user;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Matteo Veroni
 */

@Embeddable
public class User implements Serializable {
    
    @Column(name = "user_id")
    private long id;
    
    @Column(name = "user_name")
    private String name;
    
    @Column(name = "user_surname")
    private String surname;
    
    @Column(name = "user_age")
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
