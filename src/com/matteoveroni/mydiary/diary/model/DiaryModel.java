package com.matteoveroni.mydiary.diary.model;

import com.matteoveroni.mydiary.note.model.bean.Note;
import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.diary.model.bean.Diary;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Matteo Veroni
 */
public class DiaryModel {

    private final DAO databaseManager = DAO.getInstance();
    private Diary diary;

    public void setDiary(Diary diary) {
        this.diary = diary;
    }

    public Note getFirstNote() {
        return diary.getNotes().get(0);
//		return (Annotation) databaseManager.read(HibernateAnnotationBean.class, null, DAO.ElementsOnWhichOperate.FIRST);
    }

    public Note getLastNote() {
        int numberOfNotes = diary.getNotes().size();
        return diary.getNotes().get(numberOfNotes - 1);
//		return (Annotation) databaseManager.read(HibernateAnnotationBean.class, null, DAO.ElementsOnWhichOperate.LAST);
    }

    public List<Note> getAllTheNotes() {
        Session session = databaseManager.openSession();
        List<Note> hibernateNotes = diary.getNotes();
        databaseManager.closeSession(session);
//        System.out.println("aaaaaaa " + diary.getNotes().size());
        return hibernateNotes;
    }

    public void createNewNote(Note noteToSave) {
        diary.getNotes().add((Note) noteToSave);
    }

    public void removeNote(Note noteToRemove) {
        diary.getNotes().remove((Note) noteToRemove);
    }
}
