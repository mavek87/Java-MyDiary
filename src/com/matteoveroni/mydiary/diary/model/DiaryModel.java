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

	private final String DIARIES_TABLE = "DIARIES";
	private final String NOTES_TABLE = "NOTES";
	private final String DIARIES_NOTES_TABLE = "DIARIES_NOTES";

	public void setDiary(Diary diary) {
		this.diary = diary;
	}

	public List<Note> getNotesFromCurrentDiary(Diary currentDiary) {
		List<Note> notesRetrieved = null;
		try {
			final String QUERY_THAT_FIND_ALL_THE_NOTES_IN_A_DIARY = ""
				+ "SELECT * FROM " + NOTES_TABLE + " n "
				+ "INNER JOIN " + DIARIES_NOTES_TABLE + " dn ON dn.NOTE_ID = n.ID "
				+ "WHERE dn.DIARY_ID = "
				+ "("
				+ "SELECT ID FROM " + DIARIES_TABLE + " "
				+ "WHERE ID = " + currentDiary.getId()
				+ ")";
			LOG.debug(" ---> QUERY_THAT_FIND_ALL_THE_NOTES_IN_A_DIARY -> " + QUERY_THAT_FIND_ALL_THE_NOTES_IN_A_DIARY);
			notesRetrieved = databaseManager.querySQL(QUERY_THAT_FIND_ALL_THE_NOTES_IN_A_DIARY, Note.class);
		} catch (Exception ex) {
			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
		}
		return notesRetrieved;
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
			diary.getNotes().remove(note);
			databaseManager.update(note);
		} catch (Exception ex) {
			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
		}
	}
}
