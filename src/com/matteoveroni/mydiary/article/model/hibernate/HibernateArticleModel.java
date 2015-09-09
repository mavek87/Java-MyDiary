package com.matteoveroni.mydiary.article.model.hibernate;

import com.matteoveroni.mydiary.article.model.Article;
import com.matteoveroni.mydiary.article.model.ArticleModel;
import com.matteoveroni.mydiary.database.DAO;

/**
 *
 * @author Matteo Veroni
 */
public class HibernateArticleModel implements ArticleModel {

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
	public Article getPreviousArticle(Article currentArticle) {
		return (Article) databaseManager.read(PersistentHibernateArticle.class, currentArticle.getId(), DAO.ElementsOnWhichOperate.PREVIOUS);
	}

	@Override
	public Article getNextArticle(Article currentArticle) {
		return (Article) databaseManager.read(PersistentHibernateArticle.class, currentArticle.getId(), DAO.ElementsOnWhichOperate.NEXT);
	}

	@Override
	public void saveCurrentArticle(Article articleToSave) {
		databaseManager.update(articleToSave);
	}

	@Override
	public Article createNewArticle(Article articleToSave) {
		databaseManager.write(articleToSave);
		return getFirstArticle();
	}
}
