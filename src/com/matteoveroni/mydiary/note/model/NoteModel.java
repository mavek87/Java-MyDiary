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
	private final String NOTES_TABLE = "NOTES";
	private static final Logger LOG = LoggerFactory.getLogger(LibraryModel.class);

	public Note getFirstNote(Diary diary) {
		Note firstNote = null;
		final String QUERY_THAT_FIND_FIRST_NOTE_IN_CURRENT_DIARY = ""
			+ "SELECT * FROM " + NOTES_TABLE + " "
			+ "WHERE ID ="
			+ "(SELECT MIN(ID) FROM " + NOTES_TABLE + " WHERE DIARY_ID=" + diary.getId() + ")";
		try {
			firstNote = (Note) databaseManager.querySQL(QUERY_THAT_FIND_FIRST_NOTE_IN_CURRENT_DIARY, Note.class);
		} catch (Exception ex) {
			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
		}
		LOG.debug(" ---> QUERY_THAT_FIND_FIRST_NOTE_IN_CURRENT_DIARY -> " + QUERY_THAT_FIND_FIRST_NOTE_IN_CURRENT_DIARY);
		return firstNote;
	}

	public Note getLastNote(Diary diary) {
		Note lastNote = null;
		final String QUERY_THAT_FIND_LAST_NOTE_IN_CURRENT_DIARY = ""
			+ "SELECT * FROM " + NOTES_TABLE + " "
			+ "WHERE ID ="
			+ "(SELECT MIN(ID) FROM " + NOTES_TABLE + " WHERE DIARY_ID=" + diary.getId() + ")";
		try {
			lastNote = (Note) databaseManager.querySQL(QUERY_THAT_FIND_LAST_NOTE_IN_CURRENT_DIARY, Note.class);
		} catch (Exception ex) {
			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
		}
		LOG.debug(" ---> QUERY_THAT_FIND_LAST_NOTE_IN_CURRENT_DIARY -> " + QUERY_THAT_FIND_LAST_NOTE_IN_CURRENT_DIARY);
		return lastNote;
	}

	public Note getNote(long noteId) {
		Note requestedNote = null;
		try {
			requestedNote = (Note) databaseManager.read(Note.class, noteId, DAO.ElementsOnWhichOperate.REQUESTED);
		} catch (Exception ex) {
			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
		}
		return requestedNote;
	}

	public Note getPreviousNote(Note currentNote) {
		Note previousNote = null;
		final String QUERY_FOR_GETTING_THE_PREVIOUS_NOTE = ""
			+ "SELECT * FROM " + NOTES_TABLE + " "
			+ "WHERE DIARY_ID=" + currentNote.getDiary().getId() + " AND ID<" + currentNote.getId() + " "
			+ "FETCH NEXT ROW ONLY";
		try {
			previousNote = (Note) databaseManager.querySQL(QUERY_FOR_GETTING_THE_PREVIOUS_NOTE, Note.class);
		} catch (Exception ex) {
			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
		}
		LOG.debug(" ---> QUERY_FOR_GETTING_THE_PREVIOUS_NOTE -> " + QUERY_FOR_GETTING_THE_PREVIOUS_NOTE);
		return previousNote;
	}

	public Note getNextNote(Note currentNote) {
		Note nextNote = null;
		final String QUERY_FOR_GETTING_THE_NEXT_NOTE = ""
			+ "SELECT * FROM " + NOTES_TABLE + " "
			+ "WHERE DIARY_ID=" + currentNote.getDiary().getId() + " AND ID>" + currentNote.getId() + " "
			+ "FETCH NEXT ROW ONLY";
		try {
			nextNote = (Note) databaseManager.querySQL(QUERY_FOR_GETTING_THE_NEXT_NOTE, Note.class);
		} catch (Exception ex) {
			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
		}
		LOG.debug(" ---> QUERY_FOR_GETTING_THE_NEXT_NOTE -> " + QUERY_FOR_GETTING_THE_NEXT_NOTE);
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
