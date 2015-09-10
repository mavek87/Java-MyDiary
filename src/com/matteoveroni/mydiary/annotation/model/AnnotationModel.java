package com.matteoveroni.mydiary.annotation.model;

/**
 *
 * @author Matteo Veroni
 */
public interface AnnotationModel {
    
    public Annotation getFirstArticle();

    public Annotation getLastArticle();

    public Annotation getPreviousArticle(Annotation currentArticle);

    public Annotation getNextArticle(Annotation currentArticle);

    public void saveCurrentArticle(Annotation articleToSave);
    
    public Annotation createNewArticle(Annotation articleToSave);
}
