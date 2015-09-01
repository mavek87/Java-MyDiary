package com.matteoveroni.mydiary.article.model;

import com.matteoveroni.mydiary.database.DAO;

/**
 *
 * @author Matteo Veroni
 */
public class HibernateArticleModel implements ArticleModel{

    private final DAO databaseManager = DAO.getInstance();
        
    @Override
    public Article getFirstArticle() {
        return (Article) databaseManager.read(Article.class, DAO.ElementOnWhichOperate.FIRST);
    }

    @Override
    public Article getLastArticle() {
        return (Article) databaseManager.read(Article.class, DAO.ElementOnWhichOperate.LAST);
    }

    @Override
    public Article getPreviousArticle(Article currentArticle) {
        return (Article) databaseManager.read(Article.class, currentArticle.getId() - 1);
    }

    @Override
    public Article getNextArticle(Article currentArticle) {
        return (Article) databaseManager.read(Article.class, currentArticle.getId() + 1);
    }

    @Override
    public void saveCurrentArticle(Article articleToSave) {
        databaseManager.update(articleToSave);
    }
}
