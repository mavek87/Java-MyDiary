package com.matteoveroni.mydiary.diary.model.bean;

import com.matteoveroni.mydiary.note.model.bean.Note;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Matteo Veroni
 */
@Entity
@Table(name = "DIARIES")
public class Diary implements Serializable {

	@Id
	@GeneratedValue
	private long id;

	@Column
	private String name;

	@OneToMany(fetch = FetchType.EAGER, mappedBy="diary", cascade=CascadeType.ALL)
//	@JoinTable(name = "DIARIES_NOTES", 
//		joinColumns = {
//			@JoinColumn(name = "DIARY_ID", referencedColumnName = "ID")
//		},
//		inverseJoinColumns = {
//			@JoinColumn(name = "NOTE_ID", referencedColumnName = "ID")
//		})
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

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
}
