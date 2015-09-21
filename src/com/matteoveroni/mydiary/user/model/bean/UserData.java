package com.matteoveroni.mydiary.user.model.bean;

import com.matteoveroni.mydiary.diary.model.bean.Diary;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Matteo Veroni
 */
@Entity
@Table(name="Users")
public class UserData implements Serializable {

    @Id
    private String username;

    @Column
    private String password;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private Integer age;

    @OneToMany
//    (fetch = FetchType.EAGER)
    private Collection<Diary> diaries = new HashSet<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Collection<Diary> getDiaries() {
        return diaries;
    }

    public void setDiaries(Collection<Diary> diaries) {
        this.diaries = diaries;
    }

    @Override
    public String toString() {
        return getUsername();
    }
}
