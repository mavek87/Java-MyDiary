package com.matteoveroni.mydiary.library.model;

import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.diary.model.bean.Diary;
import java.util.List;

/**
 *
 * @author Matteo Veroni
 */
public class LibraryModel {

    private final DAO databaseManager = DAO.getInstance();

    public void createNewDiary(Diary diary) {
        databaseManager.write(diary);
    }

    public List readAllTheDiaries() {
        return databaseManager.readAll(Diary.class);
    }
}
