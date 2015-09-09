package com.matteoveroni.mydiary.application.manager;

import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.patterns.Listenable;
import com.matteoveroni.mydiary.patterns.Listener;
import com.matteoveroni.mydiary.screen.manager.ScreenManager;
import com.matteoveroni.mydiary.screen.ScreenType;
import com.matteoveroni.mydiary.user.model.User;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Matteo Veroni
 */
public class ApplicationManager implements Manager, Disposable, Listenable {

    private final ScreenManager screenManager;
    private final DAO database;
    private final List<Listener> listeners = new ArrayList<>();
    private final List<Disposable> resourcesToDisposeWhenApplicationClose = new ArrayList<>();
    private User loggedInUser;

    public ApplicationManager(ScreenManager screenManager, DAO databaseAccessObject) {
        this.screenManager = screenManager;
        this.database = databaseAccessObject;
        resourcesToDisposeWhenApplicationClose.add(this.screenManager);
        resourcesToDisposeWhenApplicationClose.add(this.database);
        setThisApplicationManagerAsScreenControllersManager();
    }

    @Override
    public void registerListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    @Override
    public void notifyListeners() {
        for (Listener listener : listeners) {
            listener.update();
        }
    }

    @Override
    public void changeScreen(ScreenType screenType) {
        screenManager.useScreen(screenType);
        notifyListeners();
    }

    @Override
    public User getLoggedUser() {
        return loggedInUser;
    }

    @Override
    public void setCurrentUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
//		notifyListeners();
    }

    @Override
    public void dispose() {
        for(Disposable resource : resourcesToDisposeWhenApplicationClose){
            resource.dispose();
        }
    }

    private void setThisApplicationManagerAsScreenControllersManager() {
        for (Manageable controller : screenManager.getScreenControllers()) {
            controller.setManager(this);
        }
    }
}
