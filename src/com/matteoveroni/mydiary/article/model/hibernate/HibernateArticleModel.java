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
        return (Article) databaseManager.read(PersistentHibernateArticle.class, DAO.ElementOnWhichOperate.FIRST);
    }

    @Override
    public Article getLastArticle() {
        return (Article) databaseManager.read(PersistentHibernateArticle.class, DAO.ElementOnWhichOperate.LAST);
    }

    @Override
    public Article getPreviousArticle(Article currentArticle) {
        return (Article) databaseManager.read(PersistentHibernateArticle.class, currentArticle.getId() - 1);
    }

    @Override
    public Article getNextArticle(Article currentArticle) {
        return (Article) databaseManager.read(PersistentHibernateArticle.class, currentArticle.getId() + 1);
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
