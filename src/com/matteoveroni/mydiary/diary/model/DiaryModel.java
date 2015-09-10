package com.matteoveroni.mydiary.diary.model;

import com.matteoveroni.mydiary.annotation.model.bean.Annotation;
import java.util.List;

/**
 *
 * @author Matteo Veroni
 */
public interface DiaryModel {
	
    public Annotation getFirstArticle();

    public Annotation getLastArticle();

    public List<Annotation> getAllTheArticles();

    public Annotation createNewArticle(Annotation articleToSave);
	
	public void removeArticle(Annotation articleToRemove);

}
