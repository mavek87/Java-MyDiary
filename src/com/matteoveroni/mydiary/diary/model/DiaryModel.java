package com.matteoveroni.mydiary.diary.model;

import com.matteoveroni.mydiary.note.model.bean.Note;
import com.matteoveroni.mydiary.diary.model.bean.Diary;
import com.matteoveroni.mydiary.utilities.formatters.ExceptionsFormatter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class DiaryModel {

//	private final DAO databaseManager = DAO.getInstance();
	private Diary diary;
	private static final Logger LOG = LoggerFactory.getLogger(DiaryModel.class);

	public void setDiary(Diary diary) {
		this.diary = diary;
	}

	public Note getFirstNote() {
		Note firstNote = null;
		try {
			firstNote = diary.getNotes().get(0);
		} catch (Exception ex) {
			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
		}
		return firstNote;
//		return (Annotation) databaseManager.read(HibernateAnnotationBean.class, null, DAO.ElementsOnWhichOperate.FIRST);
	}

	public Note getLastNote() {
		Note lastNote = null;
		try {
			if (diary.getNotes() != null) {
				int numberOfNotes = diary.getNotes().size();
				lastNote = diary.getNotes().get(numberOfNotes - 1);
			}
		} catch (Exception ex) {
			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
		}
		return lastNote;
//		return (Annotation) databaseManager.read(HibernateAnnotationBean.class, null, DAO.ElementsOnWhichOperate.LAST);
	}

	public List<Note> getAllTheNotes() {
		//Session session = databaseManager.openSession();
		//databaseManager.closeSession(session);
		List<Note> diaryNotes = null;
		try {
			diaryNotes = diary.getNotes();
		} catch (Exception ex) {
			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
		}
		return diaryNotes;
	}

	public void createNewNote(Note noteToSave) {
		try {
			diary.getNotes().add((Note) noteToSave);
		} catch (Exception ex) {
		}
	}

	public void removeNote(Note noteToRemove) {
		try {
			diary.getNotes().remove((Note) noteToRemove);
		} catch (Exception ex) {
		}
	}
}
