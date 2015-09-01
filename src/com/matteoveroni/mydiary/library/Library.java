package com.matteoveroni.mydiary.library;

import com.matteoveroni.mydiary.diary.model.hibernate.PersistentHibernateDiary;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Matteo Veroni
 */
public class Library {

    private final Map<String, PersistentHibernateDiary> library = new HashMap<>();

    public void addDiary(String name, PersistentHibernateDiary newDiary) {
        if (!library.containsKey(name)) {
            library.put(name, newDiary);
        }
    }

    public PersistentHibernateDiary getDiary(String diaryName) {
        if (library.containsKey(diaryName)) {
            return library.get(diaryName);
        }
        return null;
    }
}
