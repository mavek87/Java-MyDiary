package com.matteoveroni.mydiary.user.model.bean;

import com.matteoveroni.mydiary.diary.model.bean.Diary;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Matteo Veroni
 */
@Entity
@Table(name = "USERS")
public class UserData implements Serializable {

	@Id
	@GeneratedValue
	private long id;

	@Column
	private String username;

	@Column
	private String password;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private Integer age;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USERS_DIARIES",
		joinColumns = {
			@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
		},
		inverseJoinColumns = {
			@JoinColumn(name = "DIARY_ID", referencedColumnName = "ID")
		})
	private List<Diary> diaries = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public List<Diary> getDiaries() {
		return diaries;
	}

	public void setDiaries(List<Diary> diaries) {
		this.diaries = diaries;
	}

	@Override
	public String toString() {
		return getUsername();
	}
}
