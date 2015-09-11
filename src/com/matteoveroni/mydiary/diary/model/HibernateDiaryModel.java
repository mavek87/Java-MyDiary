package com.matteoveroni.mydiary.diary.model;

import com.matteoveroni.mydiary.annotation.model.bean.Annotation;
import com.matteoveroni.mydiary.annotation.model.bean.HibernateAnnotationBean;
import com.matteoveroni.mydiary.database.DAO;
import java.util.List;

/**
 *
 * @author Matteo Veroni
 */
public class HibernateDiaryModel implements DiaryModel {

	private final DAO databaseManager = DAO.getInstance();

	@Override
	public Annotation getFirstAnnotation() {
		return (Annotation) databaseManager.read(HibernateAnnotationBean.class, null, DAO.ElementsOnWhichOperate.FIRST);
	}

	@Override
	public Annotation getLastAnnotation() {
		return (Annotation) databaseManager.read(HibernateAnnotationBean.class, null, DAO.ElementsOnWhichOperate.LAST);
	}

	@Override
	public List<Annotation> getAllTheAnnotations() {
		return databaseManager.readAll(HibernateAnnotationBean.class);
	}

	@Override
	public Annotation createNewAnnotation(Annotation annotationToSave) {
		databaseManager.write(annotationToSave);
		return getFirstAnnotation();
	}

	@Override
	public void removeAnnotation(Annotation annotationToRemove) {
		databaseManager.delete(annotationToRemove);
	}
}
