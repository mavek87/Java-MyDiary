package com.matteoveroni.mydiary.diary.model;

import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.note.model.bean.Note;
import com.matteoveroni.mydiary.diary.model.bean.Diary;
import com.matteoveroni.mydiary.utilities.formatters.ExceptionsFormatter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class DiaryModel {

	private final DAO databaseManager = DAO.getInstance();
	private Diary diary;
	private static final Logger LOG = LoggerFactory.getLogger(DiaryModel.class);

	private final String NOTES_TABLE = "notes";
	private final String USERS_TABLE = "users";
	private final String USERS_DIARIES_TABLE = "users_diaries";

	public void setDiary(Diary diary) {
		this.diary = diary;
	}

	public List<Note> getNotesFromCurrentDiary() {
		List<Note> notesRetrieved = null;
		try {
			final String QUERY_THAT_FIND_ALL_THE_NOTES_OF_A_DIARY = ""
				+ "SELECT * FROM " + NOTES_TABLE + " "
				+ "WHERE DIARY_ID = " + diary.getId();
			LOG.debug(" ---> QUERY_THAT_FIND_ALL_THE_NOTES_IN_A_DIARY -> " + QUERY_THAT_FIND_ALL_THE_NOTES_OF_A_DIARY);
			notesRetrieved = databaseManager.querySQL(QUERY_THAT_FIND_ALL_THE_NOTES_OF_A_DIARY, Note.class);
		} catch (Exception ex) {
			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
		}
		return notesRetrieved;
	}

	public String getDiaryOwnerUsername(Diary diary) {
		String diarysOwner = null;
		try {
			final String QUERY_THAT_FIND_THE_OWNER_OF_A_DIARY = ""
				+ "SELECT " + USERS_TABLE + ".username" + " FROM " + USERS_TABLE + " "
				+ "INNER JOIN " + USERS_DIARIES_TABLE + " ON " + USERS_TABLE + ".id = " + USERS_DIARIES_TABLE + ".user_id "
				+ "WHERE " + USERS_DIARIES_TABLE + ".diary_id = " + diary.getId();
			diarysOwner = (String) databaseManager.querySQL(QUERY_THAT_FIND_THE_OWNER_OF_A_DIARY, null).get(0);
		} catch (Exception ex) {
			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
		}
		if (diarysOwner != null) {
			return diarysOwner;
		} else {
			return "Diary without a owner";
		}
	}

	public int getNumberOfNotesOfADiary(Diary diary) {
		int numberOfNotesInTheDiary;
		try {
			final String QUERY_THAT_FIND_THE_OWNER_OF_A_DIARY = ""
				+ "SELECT COUNT(ID) FROM " + NOTES_TABLE + " "
				+ "WHERE " + NOTES_TABLE + ".diary_id = " + diary.getId();
			numberOfNotesInTheDiary = (Integer) databaseManager.querySQL(QUERY_THAT_FIND_THE_OWNER_OF_A_DIARY, null).get(0);
		} catch (Exception ex) {
			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
			numberOfNotesInTheDiary = 0;
		}
		return numberOfNotesInTheDiary;
	}

	public boolean saveNoteIntoCurrentDiary(Note note) {
		try {
			if (diary.getNotes() == null) {
				LOG.debug(" ---> Diary " + diary.getName() + " has no notes.. creating the first one");
				ArrayList<Note> updatedListOfNotes = new ArrayList<>();
				updatedListOfNotes.add(note);
				diary.setNotes(updatedListOfNotes);
			} else {
				LOG.debug(" ---> Diary " + diary.getName() + " has some diaries.. adding a new one");
				diary.getNotes().add(note);
			}
			LOG.debug(" ---> Saving note in the DB...");
			databaseManager.write(note);
			LOG.debug(" ---> Updating diary " + diary.getName() + " in the DB...");
			databaseManager.update(diary);
			return true;
		} catch (Exception ex) {
			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
		}
		return false;
	}

	public void removeNoteFromCurrentDiary(Note note) {
		try {
			databaseManager.delete(note);
			databaseManager.update(diary);
		} catch (Exception ex) {
			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
		}
	}
}
