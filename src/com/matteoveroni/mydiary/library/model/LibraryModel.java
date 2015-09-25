package com.matteoveroni.mydiary.library.model;

import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.diary.model.bean.Diary;
import com.matteoveroni.mydiary.user.model.bean.UserData;
import com.matteoveroni.mydiary.utilities.formatters.ExceptionsFormatter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class LibraryModel {

    private final DAO databaseManager = DAO.getInstance();
    private static final Logger LOG = LoggerFactory.getLogger(LibraryModel.class);
    private final String USERS_DIARIES_TABLE = "USERS_DIARIES";

    public boolean createNewDiary(Diary diary, UserData user) {
        try {
            if (user.getDiaries() == null) {
                LOG.debug(" ---> User " + user.getUsername() + " has no diaries.. creating the first one");
                ArrayList<Diary> updatedListOfUsersDiaries = new ArrayList<>();
                updatedListOfUsersDiaries.add(diary);
                user.setDiaries(updatedListOfUsersDiaries);
            } else {
                LOG.debug(" ---> User " + user.getUsername() + " has some diaries.. adding a new one");
                user.getDiaries().add(diary);
            }
            LOG.debug(" ---> Creating diary " + diary.getName() + " in the DB...");
            databaseManager.write(diary);
            LOG.debug(" ---> Updating user " + user.getUsername() + " in the DB...");
            databaseManager.update(user);
            return true;
        } catch (Exception ex) {
            LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
        }
        return false;
    }

    public List<Diary> getDiariesForUser(UserData user) {
        List<Diary> diariesRetrieved = null;
        try {
            final String QUERY_FIND_DIARIES_IDS_FOR_CURRENT_USER = "select DIARY_ID from " + USERS_DIARIES_TABLE + " where USER_ID=\'" + user.getId() + "\'";
            LOG.debug(" ---> QUERY_FIND_DIARIES_IDS_FOR_CURRENT_USER -> " + QUERY_FIND_DIARIES_IDS_FOR_CURRENT_USER);
            List<Long> diariesIdRetrieved = databaseManager.querySQL(QUERY_FIND_DIARIES_IDS_FOR_CURRENT_USER, Long.class);
            for (Long diaryId : diariesIdRetrieved) {
                diariesRetrieved.add((Diary) databaseManager.read(Diary.class, diaryId, DAO.ElementsOnWhichOperate.REQUESTED));
            }
        } catch (Exception ex) {
            LOG.error(" ---> " + ExceptionsFormatter.toString(ex));
        }
        return diariesRetrieved;
    }
}
