package com.matteoveroni.mydiary.annotation.model.bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Matteo Veroni
 */
@Entity
@Table(name = "Annotation")
public class HibernateAnnotationBean implements Serializable, Annotation {

	@Id
	@GeneratedValue
	private long id;

	@Column
	private String title;

	@Column(columnDefinition = "CLOB")
	@Lob
	private String message;

	@Column
	private String author;

	@Column
	@Temporal(TemporalType.DATE)
	private Date creationDate;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModificationDate;

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getAuthor() {
		return author;
	}

	@Override
	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public Date getCreationDate() {
		return this.creationDate;
	}

	@Override
	@PrePersist
	public void setCreationDate(Date creationDate) {
		this.creationDate = new Date();
	}

	@Override
	public Date getLastModifationTimestamp() {
		return this.lastModificationDate;
	}

	@Override
	@PreUpdate
	public void setLastModificationTimestamp(Date lastModificationDate) {
		this.lastModificationDate = new Date();
	}
}
