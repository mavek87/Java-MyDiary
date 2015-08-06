package com.matteoveroni.mydiary.applicationmanager;

import com.matteoveroni.mydiary.model.database.DatabaseManager;
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
        databaseManager.dispose();
    }

}
