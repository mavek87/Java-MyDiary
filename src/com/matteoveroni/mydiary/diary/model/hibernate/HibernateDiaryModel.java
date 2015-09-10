package com.matteoveroni.mydiary.diary.model.hibernate;

import com.matteoveroni.mydiary.annotation.model.Annotation;
import com.matteoveroni.mydiary.annotation.model.hibernate.PersistentHibernateAnnotation;
import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.diary.model.DiaryModel;
import java.util.List;

/**
 *
 * @author Matteo Veroni
 */
public class HibernateDiaryModel implements DiaryModel {

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
	public List<Annotation> getAllTheArticles() {
		return databaseManager.readAll(PersistentHibernateAnnotation.class);
	}

	@Override
	public Annotation createNewArticle(Annotation articleToSave) {
		databaseManager.write(articleToSave);
		return getFirstArticle();
	}

	@Override
	public void removeArticle(Annotation articleToRemove) {
		databaseManager.delete(articleToRemove);
	}
}
