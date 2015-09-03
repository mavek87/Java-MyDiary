package com.matteoveroni.mydiary.application;

import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.screen.ScreenManager;
import com.matteoveroni.mydiary.user.UserData;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;

/**
 *
 * @author Matteo Veroni
 */
public class ApplicationManager implements Disposable {

    private final ScreenManager screenManager;
    private final DAO databaseManager;
    private UserData loggedInUser;

    public ApplicationManager(ScreenManager screenManager, DAO databaseManager) {
        this.screenManager = screenManager;
        this.databaseManager = databaseManager;
    }

    public UserData getCurrentUser() {
        return loggedInUser;
    }
    
    public void setCurrentUser(UserData loggedInUser){
        this.loggedInUser = loggedInUser;
    }

    @Override
    public void dispose() {
        screenManager.dispose();
        databaseManager.dispose();
    }

}
