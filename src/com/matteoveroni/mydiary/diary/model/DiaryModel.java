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

    public Note getFirstNote() {
        Note firstNote = null;
        try {
            firstNote = diary.getNotes().get(0);
        } catch (Exception ex) {
            LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
        }
        return firstNote;
//		return (Note) databaseManager.read(Note.class, null, DAO.ElementsOnWhichOperate.FIRST);
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

//	public List<Note> getAllTheNotes() {
//		List<Note> diaryNotes = null;
//		try {
//			if (diary.getNotes() != null) {
//				diaryNotes = diary.getNotes();
//			}
//		} catch (Exception ex) {
//			LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
//		}
//		return diaryNotes;
//	}
    public List<Note> getAllNotes(Diary diary) {
        List<Note> notesRetrieved = null;
        try {
            final String QUERY_THAT_FIND_ALL_THE_NOTES_IN_A_DIARY = ""
                + "SELECT * FROM " + NOTES_TABLE + " n "
                + "INNER JOIN " + DIARIES_NOTES_TABLE + " dn ON dn.DIARY_ID = n.ID "
                + "WHERE dn.DIARY_ID = "
                + "("
                + "SELECT ID FROM " + DIARIES_TABLE + " "
                + "WHERE ID = " + diary.getId()
                + ")";

            LOG.debug(" ---> QUERY_THAT_FIND_ALL_THE_NOTES_IN_A_DIARY -> " + QUERY_THAT_FIND_ALL_THE_NOTES_IN_A_DIARY);
            notesRetrieved = databaseManager.querySQL(QUERY_THAT_FIND_ALL_THE_NOTES_IN_A_DIARY, Note.class);
        } catch (Exception ex) {
            LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
        }
        return notesRetrieved;
    }

    public boolean createNewNote(Note newNoteToSave) {
        try {
            if (diary.getNotes() == null) {
                LOG.debug(" ---> Diary " + diary.getName() + " has no notes.. creating the first one");
                ArrayList<Note> updatedListOfNotes = new ArrayList<>();
                updatedListOfNotes.add(newNoteToSave);
                diary.setNotes(updatedListOfNotes);
            } else {
                LOG.debug(" ---> Diary " + diary.getName() + " has some diaries.. adding a new one");
                diary.getNotes().add(newNoteToSave);
            }
            LOG.debug(" ---> Saving note in the DB...");
            databaseManager.write(newNoteToSave);
            LOG.debug(" ---> Updating diary " + diary.getName() + " in the DB...");
            databaseManager.update(diary);
            return true;
        } catch (Exception ex) {
            LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
        }
        return false;
    }

    public void removeNote(Note noteToRemove) {
        try {
            diary.getNotes().remove(noteToRemove);
            databaseManager.update(noteToRemove);
        } catch (Exception ex) {
            LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
        }
    }
}
