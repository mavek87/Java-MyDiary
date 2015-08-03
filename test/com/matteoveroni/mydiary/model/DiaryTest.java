package com.matteoveroni.mydiary.model;

import com.matteoveroni.mydiary.model.diary.Diary;
import com.matteoveroni.mydiary.model.article.Article;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Matteo Veroni
 */
public class DiaryTest {

    private final Diary diary = new Diary();
       
    private static final String fakeArticleName = "fake123";
    private static final String realArticleName = "realArticle";
    
    @Test
    public void testGetFakeArticle() {        
        assertNull(diary.getArticle(fakeArticleName)); 
    }
    
    @Test
    public void testAddAndGetRealArticle(){
        assertNull(diary.getArticle(realArticleName));
        Article realArticle = new Article();
        diary.addArticle(realArticleName, realArticle);
        assertNotNull(diary.getArticle(realArticleName));
    }
    
}
