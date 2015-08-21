package com.matteoveroni.mydiary.application;

import com.matteoveroni.mydiary.database.DatabaseManager;
import com.matteoveroni.mydiary.screen.ScreenManager;
import com.matteoveroni.mydiary.user.ApplicationUser;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;

/**
 *
 * @author Matteo Veroni
 */
public class ApplicationManager implements Disposable {

    private final ScreenManager screenManager;
    private final DatabaseManager databaseManager;
    private ApplicationUser loggedInUser;

    public ApplicationManager(ScreenManager screenManager, DatabaseManager databaseManager) {
        this.screenManager = screenManager;
        this.databaseManager = databaseManager;
    }

    public ApplicationUser getCurrentUser() {
        return loggedInUser;
    }
    
    public void setCurrentUser(ApplicationUser loggedInUser){
        this.loggedInUser = loggedInUser;
    }

    @Override
    public void dispose() {
        screenManager.dispose();
        databaseManager.dispose();
    }

}
