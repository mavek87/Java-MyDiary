package com.matteoveroni.mydiary.diary.model;

import com.matteoveroni.mydiary.annotation.model.bean.Annotation;
import com.matteoveroni.mydiary.annotation.model.bean.HibernateAnnotationBean;
import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.diary.model.bean.Diary;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Matteo Veroni
 */
public class HibernateDiaryModel implements DiaryModel {

	private final DAO databaseManager = DAO.getInstance();
	private Diary diary;

	@Override
	public void setDiary(Diary diary) {
		this.diary = diary;
	}

	@Override
	public Annotation getFirstAnnotation() {
		return diary.getAnnotations().get(0);
//		return (Annotation) databaseManager.read(HibernateAnnotationBean.class, null, DAO.ElementsOnWhichOperate.FIRST);
	}

	@Override
	public Annotation getLastAnnotation() {
		int numberOfAnnotations = diary.getAnnotations().size();
		return diary.getAnnotations().get(numberOfAnnotations-1);
//		return (Annotation) databaseManager.read(HibernateAnnotationBean.class, null, DAO.ElementsOnWhichOperate.LAST);
	}

	@Override
	public Iterator getAllTheAnnotations() {
//		Session session = databaseManager.openSession();
//		List<HibernateAnnotationBean> hibernateAnnotations = diary.getAnnotations();
//		List<Annotation> annotations = new ArrayList<>();
//		for(HibernateAnnotationBean annotation : hibernateAnnotations){
//			annotations.add((Annotation)annotation);
//		}
//		databaseManager.closeSession(session);
//		return annotations;
		return diary.getAnnotations().iterator();
	}

	@Override
	public void createNewAnnotation(Annotation annotationToSave) {
		diary.getAnnotations().add((HibernateAnnotationBean)annotationToSave);
	}

	@Override
	public void removeAnnotation(Annotation annotationToRemove) {
		diary.getAnnotations().remove((HibernateAnnotationBean)annotationToRemove);
	}
}
