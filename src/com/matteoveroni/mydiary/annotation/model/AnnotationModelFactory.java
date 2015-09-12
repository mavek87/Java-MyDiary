package com.matteoveroni.mydiary.annotation.model;

import com.matteoveroni.mydiary.annotation.model.AnnotationModel;
import com.matteoveroni.mydiary.annotation.model.AnnotationModelType;
import com.matteoveroni.mydiary.annotation.model.HibernateAnnotationModel;

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
