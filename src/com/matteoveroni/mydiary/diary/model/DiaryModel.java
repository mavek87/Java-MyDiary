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

    public Note getFirstAnnotation() {
        return diary.getAnnotations().get(0);
//		return (Annotation) databaseManager.read(HibernateAnnotationBean.class, null, DAO.ElementsOnWhichOperate.FIRST);
    }

    public Note getLastAnnotation() {
        int numberOfAnnotations = diary.getAnnotations().size();
        return diary.getAnnotations().get(numberOfAnnotations - 1);
//		return (Annotation) databaseManager.read(HibernateAnnotationBean.class, null, DAO.ElementsOnWhichOperate.LAST);
    }

    public List<Note> getAllTheNotes() {
        Session session = databaseManager.openSession();
        List<Note> hibernateAnnotations = diary.getAnnotations();
        databaseManager.closeSession(session);
//        System.out.println("aaaaaaa " + diary.getAnnotations().size());
        return hibernateAnnotations;
    }

    public void createNewNote(Note annotationToSave) {
        diary.getAnnotations().add((Note) annotationToSave);
    }

    public void removeAnnotation(Note annotationToRemove) {
        diary.getAnnotations().remove((Note) annotationToRemove);
    }
}
