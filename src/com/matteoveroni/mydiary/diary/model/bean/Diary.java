package com.matteoveroni.mydiary.diary.model.bean;

import com.matteoveroni.mydiary.note.model.bean.Note;
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
import javax.persistence.Table;

/**
 *
 * @author Matteo Veroni
 */
@Entity
@Table(name="DIARIES")
public class Diary implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @ManyToOne
    private UserData ownerUser;

    @OneToMany
//    (fetch = FetchType.EAGER)
//    @Fetch(value = FetchMode.SUBSELECT)
//    @JoinTable(name = "DIARIES_NOTES",
//        joinColumns = {
//            @JoinColumn(name = "DIARY_ID", referencedColumnName = "ID")
//        },
//        inverseJoinColumns = {
//            @JoinColumn(name = "NOTE_ID", referencedColumnName = "ID")
//        })
    private List<Note> notes = new ArrayList<>();

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

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
