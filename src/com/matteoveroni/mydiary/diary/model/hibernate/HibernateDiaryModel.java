package com.matteoveroni.mydiary.diary.model.hibernate;

import com.matteoveroni.mydiary.article.model.Article;
import com.matteoveroni.mydiary.article.model.hibernate.PersistentHibernateArticle;
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
	public Article getFirstArticle() {
		return (Article) databaseManager.read(PersistentHibernateArticle.class, null, DAO.ElementsOnWhichOperate.FIRST);
	}

	@Override
	public Article getLastArticle() {
		return (Article) databaseManager.read(PersistentHibernateArticle.class, null, DAO.ElementsOnWhichOperate.LAST);
	}

	@Override
	public List<Article> getAllTheArticles() {
		return databaseManager.readAll(PersistentHibernateArticle.class);
	}

	@Override
	public Article createNewArticle(Article articleToSave) {
		databaseManager.write(articleToSave);
		return getFirstArticle();
	}

	@Override
	public void removeArticle(Article articleToRemove) {
		databaseManager.delete(articleToRemove);
	}
}
