package com.matteoveroni.mydiary.library;

import com.matteoveroni.mydiary.diary.model.bean.HibernateDiaryBean;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Matteo Veroni
 */
public class Library {

    private final Map<String, HibernateDiaryBean> library = new HashMap<>();

    public void addDiary(String name, HibernateDiaryBean newDiary) {
        if (!library.containsKey(name)) {
            library.put(name, newDiary);
        }
    }

    public HibernateDiaryBean getDiary(String diaryName) {
        if (library.containsKey(diaryName)) {
            return library.get(diaryName);
        }
        return null;
    }
}
