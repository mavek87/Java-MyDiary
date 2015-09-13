package com.matteoveroni.mydiary.annotation.model;

/**
 *
 * @author Matteo Veroni
 */
public class AnnotationModelFactory {

    public static AnnotationModel createAnnotationModel(AnnotationModelType annotationModelType) {
        AnnotationModel annotationModel = null;
        switch (annotationModelType) {
            case HIBERNATE_ANNOTATION_MODEL:
                annotationModel = new HibernateAnnotationModel();
                break;
            default:
                break;
        }
        return annotationModel;
    }
}
