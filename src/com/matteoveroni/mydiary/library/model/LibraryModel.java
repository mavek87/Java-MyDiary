package com.matteoveroni.mydiary.library.model;

import com.matteoveroni.mydiary.MyDiary;
import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.diary.model.bean.Diary;
import com.matteoveroni.mydiary.user.model.bean.UserData;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class LibraryModel {

	private final DAO databaseManager = DAO.getInstance();
	private static final Logger LOG = LoggerFactory.getLogger(LibraryModel.class);

	public void createNewDiary(Diary diary, UserData user) {
		LOG.debug("Creating diary " + user.getUsername() + " in the DB...");
		databaseManager.write(diary);
		
		if (user.getDiaries() == null) {
			LOG.debug("User " + user.getUsername() + " has no diaries.. creating the first one");
			ArrayList<Diary> updatedListOfUsersDiaries = new ArrayList<>();
			updatedListOfUsersDiaries.add(diary);
			user.setDiaries(updatedListOfUsersDiaries);
		} else {
			LOG.debug("User " + user.getUsername() + " has some diaries.. adding a new one");
			user.getDiaries().add(diary);
		}

		LOG.debug("Updating user " + user.getUsername() + " in the DB...");
		databaseManager.update(user);
	}

	public List readAllTheDiaries() {
		return databaseManager.readAll(Diary.class);
	}
}
