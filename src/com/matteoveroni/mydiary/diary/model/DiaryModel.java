package com.matteoveroni.mydiary.diary.model;

import com.matteoveroni.mydiary.annotation.model.bean.Annotation;
import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.diary.model.bean.Diary;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Matteo Veroni
 */
public class DiaryModel{

	private final DAO databaseManager = DAO.getInstance();
	private Diary diary;

	public void setDiary(Diary diary) {
		this.diary = diary;
	}

	public Annotation getFirstAnnotation() {
		return diary.getAnnotations().get(0);
//		return (Annotation) databaseManager.read(HibernateAnnotationBean.class, null, DAO.ElementsOnWhichOperate.FIRST);
	}

	public Annotation getLastAnnotation() {
		int numberOfAnnotations = diary.getAnnotations().size();
		return diary.getAnnotations().get(numberOfAnnotations-1);
//		return (Annotation) databaseManager.read(HibernateAnnotationBean.class, null, DAO.ElementsOnWhichOperate.LAST);
	}

	public List<Annotation> getAllTheAnnotations() {
		Session session = databaseManager.openSession();
		List<Annotation> hibernateAnnotations = diary.getAnnotations();
		List<Annotation> annotations = new ArrayList<>();
		for(Annotation annotation : hibernateAnnotations){
			annotations.add((Annotation)annotation);
		}
		databaseManager.closeSession(session);
		return annotations;
	}

	public void createNewAnnotation(Annotation annotationToSave) {
		diary.getAnnotations().add((Annotation)annotationToSave);
	}

	public void removeAnnotation(Annotation annotationToRemove) {
		diary.getAnnotations().remove((Annotation)annotationToRemove);
	}
}
