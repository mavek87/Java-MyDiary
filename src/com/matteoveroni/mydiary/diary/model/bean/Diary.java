package com.matteoveroni.mydiary.diary.model.bean;

import com.matteoveroni.mydiary.annotation.model.bean.Annotation;
import com.matteoveroni.mydiary.user.model.bean.UserData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
	@JoinColumn(name = "USER_ID")
	private UserData ownerUser;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "DIARIES_ANNOTATIONS", joinColumns = @JoinColumn(name = "DIARY_ID"))
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
}
