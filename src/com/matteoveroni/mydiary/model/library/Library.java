package com.matteoveroni.mydiary.model.library;

import com.matteoveroni.mydiary.model.diary.Diary;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Matteo Veroni
 */
public class Library {

    private final Map<String, Diary> library = new HashMap<>();

    public void addDiary(String name, Diary newDiary) {
        if (!library.containsKey(name)) {
            library.put(name, newDiary);
        }
    }

    public Diary getDiary(String diaryName) {
        if (library.containsKey(diaryName)) {
            return library.get(diaryName);
        }
        return null;
    }
}
