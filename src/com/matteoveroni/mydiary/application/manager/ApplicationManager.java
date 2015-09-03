package com.matteoveroni.mydiary.application.manager;

import com.matteoveroni.mydiary.Observable;
import com.matteoveroni.mydiary.Observer;
import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.screen.ScreenManager;
import com.matteoveroni.mydiary.user.model.User;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Matteo Veroni
 */
public class ApplicationManager implements Disposable, Observable {

    private final ScreenManager screenManager;
    private final DAO databaseManager;
    private final List<Observer> applicationManagerListeners;
    private User loggedInUser;

    public ApplicationManager(ScreenManager screenManager, DAO databaseManager) {
        this.screenManager = screenManager;
        this.databaseManager = databaseManager;
        applicationManagerListeners = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer listener) {
        applicationManagerListeners.add(listener);
    }

    @Override
    public void removeObserver(Observer listener) {
        applicationManagerListeners.remove(listener);
    }

    @Override
    public void notifyObservers() {
        for(Observer listener : applicationManagerListeners){
            listener.update();
        }
    }

    public User getCurrentUser() {
        return loggedInUser;
    }

    public void setCurrentUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        notifyObservers();
    }

    @Override
    public void dispose() {
        screenManager.dispose();
        databaseManager.dispose();
    }

}
