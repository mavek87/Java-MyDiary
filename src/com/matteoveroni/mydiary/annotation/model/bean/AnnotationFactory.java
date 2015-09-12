package com.matteoveroni.mydiary.annotation.model.bean;

import com.matteoveroni.mydiary.annotation.model.bean.Annotation;
import com.matteoveroni.mydiary.annotation.model.bean.AnnotationType;
import com.matteoveroni.mydiary.annotation.model.bean.HibernateAnnotationBean;

/**
 *
 * @author Matteo Veroni
 */
public class AnnotationFactory {

    public static Annotation createAnnotation(AnnotationType typeOfAnnotation) {
        Annotation annotation = null;
        switch (typeOfAnnotation) {
            case HIBERNATE:
                annotation = new HibernateAnnotationBean();
                break;
            default:
                break;
        }
        return annotation;
    }
}
