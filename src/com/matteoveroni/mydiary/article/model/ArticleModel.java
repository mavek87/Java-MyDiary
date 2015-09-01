package com.matteoveroni.mydiary.article.model;

/**
 *
 * @author Matteo Veroni
 */
public interface ArticleModel {
    
    public Article getFirstArticle();

    public Article getLastArticle();

    public Article getPreviousArticle(Article currentArticle);

    public Article getNextArticle(Article currentArticle);

    public void saveCurrentArticle(Article articleToSave);
    
    public Article createNewArticle(Article articleToSave);
}
