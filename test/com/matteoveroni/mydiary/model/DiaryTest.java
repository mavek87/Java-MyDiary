package com.matteoveroni.mydiary.model;

import com.matteoveroni.mydiary.diary.model.hibernate.PersistentHibernateDiary;
import com.matteoveroni.mydiary.article.model.hibernate.PersistentHibernateArticle;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Matteo Veroni
 */
public class DiaryTest {

    private final PersistentHibernateDiary diary = new PersistentHibernateDiary();
       
    private static final String fakeArticleName = "fake123";
    private static final String realArticleName = "realArticle";
    
    @Test
    public void testGetFakeArticle() {        
        assertNull(diary.getArticle(fakeArticleName)); 
    }
    
    @Test
    public void testAddAndGetRealArticle(){
        assertNull(diary.getArticle(realArticleName));
        PersistentHibernateArticle realArticle = new PersistentHibernateArticle();
        diary.addArticle(realArticleName, realArticle);
        assertNotNull(diary.getArticle(realArticleName));
    }
    
}
