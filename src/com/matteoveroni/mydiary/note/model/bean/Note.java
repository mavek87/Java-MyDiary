package com.matteoveroni.mydiary.note.model.bean;

import com.matteoveroni.mydiary.diary.model.bean.Diary;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Matteo Veroni
 */
@Entity
@Table(name="NOTES")
public class Note implements Serializable {

	@Id
	@GeneratedValue
	private long id;

	@Column
	private String title;

	@Column(columnDefinition = "CLOB")
	@Lob
	private String message;

	@ManyToOne
	private Diary diary;

	@Column
	@Temporal(TemporalType.DATE)
	private Date creationDate;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModificationTimestamp;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Diary getDiary() {
		return diary;
	}

	public void setDiary(Diary diary) {
		this.diary = diary;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	@PrePersist
	public void setCreationDate(Date creationDate) {
		this.creationDate = new Date();
	}

	public Date getLastModificationTimestamp() {
		return this.lastModificationTimestamp;
	}

	public void setLastModificationTimestamp(Date lastModificationTimestamp) {
		this.lastModificationTimestamp = lastModificationTimestamp;
	}
}
