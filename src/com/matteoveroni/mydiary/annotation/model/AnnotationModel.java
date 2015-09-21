package com.matteoveroni.mydiary.annotation.model;

import com.matteoveroni.mydiary.annotation.model.bean.Annotation;
import com.matteoveroni.mydiary.database.DAO;

/**
 *
 * @author Matteo Veroni
 */
public class AnnotationModel{

    private final DAO databaseManager = DAO.getInstance();

    public Annotation getFirstAnnotation() {
        return (Annotation) databaseManager.read(Annotation.class, null, DAO.ElementsOnWhichOperate.FIRST);
    }

    public Annotation getLastAnnotation() {
        return (Annotation) databaseManager.read(Annotation.class, null, DAO.ElementsOnWhichOperate.LAST);
    }

    public Annotation getAnnotation(long annotationId) {
        return (Annotation) databaseManager.read(Annotation.class, annotationId, DAO.ElementsOnWhichOperate.REQUESTED);
    }

    public Annotation getPreviousAnnotation(Annotation currentAnnotation) {
        return (Annotation) databaseManager.read(Annotation.class, currentAnnotation.getId(), DAO.ElementsOnWhichOperate.PREVIOUS);
    }

    public Annotation getNextAnnotation(Annotation currentAnnotation) {
        return (Annotation) databaseManager.read(Annotation.class, currentAnnotation.getId(), DAO.ElementsOnWhichOperate.NEXT);
    }

    public void updateAnnotation(Annotation annotationToUpdate) {
        databaseManager.update(annotationToUpdate);
    }

    public void saveAnnotation(Annotation annotationToSave) {
        databaseManager.write(annotationToSave);
    }
}
