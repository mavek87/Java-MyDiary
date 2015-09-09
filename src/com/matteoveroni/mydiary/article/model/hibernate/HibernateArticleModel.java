package com.matteoveroni.mydiary.article.model.hibernate;

import com.matteoveroni.mydiary.article.model.Article;
import com.matteoveroni.mydiary.article.model.ArticleModel;
import com.matteoveroni.mydiary.database.DAOManager;

/**
 *
 * @author Matteo Veroni
 */
public class HibernateArticleModel implements ArticleModel {

	private final DAOManager databaseManager = DAOManager.getInstance();

	@Override
	public Article getFirstArticle() {
		return (Article) databaseManager.read(PersistentHibernateArticle.class, null, DAOManager.ElementOnWhichOperate.FIRST);
	}

	@Override
	public Article getLastArticle() {
		return (Article) databaseManager.read(PersistentHibernateArticle.class, null, DAOManager.ElementOnWhichOperate.LAST);
	}

	@Override
	public Article getPreviousArticle(Article currentArticle) {
		return (Article) databaseManager.read(PersistentHibernateArticle.class, currentArticle.getId(), DAOManager.ElementOnWhichOperate.PREVIOUS);
	}

	@Override
	public Article getNextArticle(Article currentArticle) {
		return (Article) databaseManager.read(PersistentHibernateArticle.class, currentArticle.getId(), DAOManager.ElementOnWhichOperate.NEXT);
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
