package com.matteoveroni.mydiary.diary.model;

import com.matteoveroni.mydiary.annotation.model.bean.Annotation;
import com.matteoveroni.mydiary.annotation.model.bean.HibernateAnnotationBean;
import com.matteoveroni.mydiary.diary.model.bean.Diary;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Matteo Veroni
 */
public interface DiaryModel {

	public void setDiary(Diary diary);

	public Annotation getFirstAnnotation();

	public Annotation getLastAnnotation();

//    public List<HibernateAnnotationBean> getAllTheAnnotations();
	
	public Iterator getAllTheAnnotations();

	public void createNewAnnotation(Annotation annotationToSave);

	public void removeAnnotation(Annotation annotationToRemove);

}
