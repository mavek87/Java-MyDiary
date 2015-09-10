package com.matteoveroni.mydiary.annotation.model;

import com.matteoveroni.mydiary.annotation.model.bean.Annotation;

/**
 *
 * @author Matteo Veroni
 */
public interface AnnotationModel {
    
    public Annotation getFirstAnnotation();

    public Annotation getLastAnnotation();

    public Annotation getPreviousAnnotation(Annotation currentAnnotation);

    public Annotation getNextAnnotation(Annotation currentAnnotation);

    public void saveCurrentAnnotation(Annotation annotationToSave);
    
    public Annotation createNewAnnotation(Annotation annotationToSave);
}
