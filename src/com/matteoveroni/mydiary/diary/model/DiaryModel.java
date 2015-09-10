package com.matteoveroni.mydiary.diary.model;

import com.matteoveroni.mydiary.annotation.model.bean.Annotation;
import java.util.List;

/**
 *
 * @author Matteo Veroni
 */
public interface DiaryModel {
	
    public Annotation getFirstAnnotation();

    public Annotation getLastAnnotation();

    public List<Annotation> getAllTheAnnotations();

    public Annotation createNewAnnotation(Annotation articleToSave);
	
	public void removeAnnotation(Annotation articleToRemove);

}
