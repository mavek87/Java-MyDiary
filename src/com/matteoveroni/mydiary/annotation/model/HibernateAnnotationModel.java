package com.matteoveroni.mydiary.annotation.model;

import com.matteoveroni.mydiary.annotation.model.bean.Annotation;
import com.matteoveroni.mydiary.annotation.model.bean.HibernateAnnotationBean;
import com.matteoveroni.mydiary.database.DAO;

/**
 *
 * @author Matteo Veroni
 */
public class HibernateAnnotationModel implements AnnotationModel {

    private final DAO databaseManager = DAO.getInstance();

    @Override
    public Annotation getFirstAnnotation() {
        return (Annotation) databaseManager.read(HibernateAnnotationBean.class, null, DAO.ElementsOnWhichOperate.FIRST);
    }

    @Override
    public Annotation getLastAnnotation() {
        return (Annotation) databaseManager.read(HibernateAnnotationBean.class, null, DAO.ElementsOnWhichOperate.LAST);
    }

    @Override
    public Annotation getAnnotation(long annotationId) {
        return (Annotation) databaseManager.read(HibernateAnnotationBean.class, annotationId, DAO.ElementsOnWhichOperate.REQUESTED);
    }

    @Override
    public Annotation getPreviousAnnotation(Annotation currentAnnotation) {
        return (Annotation) databaseManager.read(HibernateAnnotationBean.class, currentAnnotation.getId(), DAO.ElementsOnWhichOperate.PREVIOUS);
    }

    @Override
    public Annotation getNextAnnotation(Annotation currentAnnotation) {
        return (Annotation) databaseManager.read(HibernateAnnotationBean.class, currentAnnotation.getId(), DAO.ElementsOnWhichOperate.NEXT);
    }

    @Override
    public void updateAnnotation(Annotation annotationToUpdate) {
        databaseManager.update(annotationToUpdate);
    }

    @Override
    public void saveAnnotation(Annotation annotationToSave) {
        databaseManager.write(annotationToSave);
    }
}
