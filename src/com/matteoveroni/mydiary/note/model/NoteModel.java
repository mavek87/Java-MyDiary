package com.matteoveroni.mydiary.note.model;

import com.matteoveroni.mydiary.note.model.bean.Note;
import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.diary.model.bean.Diary;
import com.matteoveroni.mydiary.library.model.LibraryModel;
import com.matteoveroni.mydiary.utilities.formatters.ExceptionsFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class NoteModel {

	private final DAO databaseManager = DAO.getInstance();
	private static final Logger LOG = LoggerFactory.getLogger(LibraryModel.class);

	private final String NOTES_TABLE = "NOTES";

	public Note getFirstNote(Diary diary) {
		Note firstNote = null;
		try {
			final String QUERY_THAT_FIND_FIRST_NOTE_IN_CURRENT_DIARY = ""
				+ "SELECT * FROM " + NOTES_TABLE + " "
				+ "WHERE ID ="
				+ "(SELECT MIN(ID) FROM " + NOTES_TABLE + " WHERE DIARY_ID=" + diary.getId() + ")";
			LOG.debug(" ---> QUERY_THAT_FIND_FIRST_NOTE_IN_CURRENT_DIARY -> " + QUERY_THAT_FIND_FIRST_NOTE_IN_CURRENT_DIARY);
			firstNote = (Note) databaseManager.querySQL(QUERY_THAT_FIND_FIRST_NOTE_IN_CURRENT_DIARY, null);
		} catch (Exception ex) {
			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
		}
		return firstNote;
	}

	public Note getLastNote(Diary diary) {
		Note lastNote = null;
		try {
			final String QUERY_THAT_FIND_LAST_NOTE_IN_CURRENT_DIARY = ""
				+ "SELECT * FROM " + NOTES_TABLE + " "
				+ "WHERE ID ="
				+ "(SELECT MIN(ID) FROM " + NOTES_TABLE + " WHERE DIARY_ID=" + diary.getId() + ")";
			LOG.debug(" ---> QUERY_THAT_FIND_LAST_NOTE_IN_CURRENT_DIARY -> " + QUERY_THAT_FIND_LAST_NOTE_IN_CURRENT_DIARY);
			lastNote = (Note) databaseManager.querySQL(QUERY_THAT_FIND_LAST_NOTE_IN_CURRENT_DIARY, null);
		} catch (Exception ex) {
			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
		}
		return lastNote;
	}

	public Note getNote(long noteId) {
		Note requestedNote = null;
		try {
			requestedNote = (Note) databaseManager.read(Note.class, noteId, DAO.ElementsOnWhichOperate.PREVIOUS);
		} catch (Exception ex) {
			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
		}
		return requestedNote;
	}

	public Note getPreviousNote(Note currentNote) {
		Note previousNote = null;
		try {
			final String QUERY_FOR_GETTING_THE_PREVIOUS_NOTE = ""
				+ "SELECT * FROM " + NOTES_TABLE + " "
				+ "WHERE DIARY_ID=" + currentNote.getDiary().getId() + " AND ID<" + currentNote.getId() + " "
				+ "FETCH NEXT ROW ONLY";
			LOG.debug(" ---> QUERY_FOR_GETTING_THE_PREVIOUS_NOTE -> " + QUERY_FOR_GETTING_THE_PREVIOUS_NOTE);
			previousNote = (Note) databaseManager.querySQL(QUERY_FOR_GETTING_THE_PREVIOUS_NOTE, null);
		} catch (Exception ex) {
			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
		}
		return previousNote;
	}

	public Note getNextNote(Note currentNote) {
		Note nextNote = null;
		try {
			final String QUERY_FOR_GETTING_THE_NEXT_NOTE = ""
				+ "SELECT * FROM " + NOTES_TABLE + " "
				+ "WHERE DIARY_ID=" + currentNote.getDiary().getId() + " AND ID>" + currentNote.getId() + " "
				+ "FETCH NEXT ROW ONLY";
			LOG.debug(" ---> QUERY_FOR_GETTING_THE_NEXT_NOTE -> " + QUERY_FOR_GETTING_THE_NEXT_NOTE);
			nextNote = (Note) databaseManager.querySQL(QUERY_FOR_GETTING_THE_NEXT_NOTE, null);
		} catch (Exception ex) {
			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
		}
		return nextNote;
	}

//	public Note getPreviousNote(Note currentNote) {
//		return (Note) databaseManager.read(Note.class, currentNote.getId(), DAO.ElementsOnWhichOperate.PREVIOUS);
//	}
//
//	public Note getNextNote(Note currentNote) {
//		return (Note) databaseManager.read(Note.class, currentNote.getId(), DAO.ElementsOnWhichOperate.NEXT);
//	}
	public void updateNote(Note noteToUpdate) {
		databaseManager.update(noteToUpdate);
	}

	public void saveNote(Note noteToSave) {
		databaseManager.write(noteToSave);
	}
}
