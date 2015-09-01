package com.matteoveroni.mydiary.diary.model;

import com.matteoveroni.mydiary.article.model.Article;
import java.util.List;

/**
 *
 * @author Matteo Veroni
 */
public interface DiaryModel {

    public Article getFirstArticle();

    public Article getLastArticle();

    public List<Article> getAllTheArticles();

    public Article createNewArticle(Article articleToSave);

}
