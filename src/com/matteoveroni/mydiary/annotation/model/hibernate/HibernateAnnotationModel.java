package com.matteoveroni.mydiary.annotation.model.hibernate;

import com.matteoveroni.mydiary.annotation.model.Annotation;
import com.matteoveroni.mydiary.annotation.model.AnnotationModel;
import com.matteoveroni.mydiary.database.DAO;

/**
 *
 * @author Matteo Veroni
 */
public class HibernateAnnotationModel implements AnnotationModel {

	private final DAO databaseManager = DAO.getInstance();

	@Override
	public Annotation getFirstArticle() {
		return (Annotation) databaseManager.read(PersistentHibernateAnnotation.class, null, DAO.ElementsOnWhichOperate.FIRST);
	}

	@Override
	public Annotation getLastArticle() {
		return (Annotation) databaseManager.read(PersistentHibernateAnnotation.class, null, DAO.ElementsOnWhichOperate.LAST);
	}

	@Override
	public Annotation getPreviousArticle(Annotation currentAnnotation) {
		return (Annotation) databaseManager.read(PersistentHibernateAnnotation.class, currentAnnotation.getId(), DAO.ElementsOnWhichOperate.PREVIOUS);
	}

	@Override
	public Annotation getNextArticle(Annotation currentAnnotation) {
		return (Annotation) databaseManager.read(PersistentHibernateAnnotation.class, currentAnnotation.getId(), DAO.ElementsOnWhichOperate.NEXT);
	}

	@Override
	public void saveCurrentArticle(Annotation annotationToSave) {
		databaseManager.update(annotationToSave);
	}

	@Override
	public Annotation createNewArticle(Annotation annotationToSave) {
		databaseManager.write(annotationToSave);
		return getFirstArticle();
	}
}
