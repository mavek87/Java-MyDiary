package com.matteoveroni.mydiary.application;

import com.matteoveroni.mydiary.database.DatabaseManager;
import com.matteoveroni.mydiary.screen.ScreenManager;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;

/**
 *
 * @author Matteo Veroni
 */
public class ApplicationManager implements Disposable {

    private ScreenManager screenManager;
    private DatabaseManager databaseManager;

    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    public void setDatabaseManager(DatabaseManager databaseManager) {
    this.databaseManager  = databaseManager;
    }
     
    @Override
    public void dispose() {
		screenManager.dispose();
        databaseManager.dispose();
    }

}
