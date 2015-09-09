package com.matteoveroni.mydiary.application.manager;

import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.patterns.Listenable;
import com.matteoveroni.mydiary.patterns.Listener;
import com.matteoveroni.mydiary.screen.ScreenManager;
import com.matteoveroni.mydiary.screen.ScreenType;
import com.matteoveroni.mydiary.user.model.User;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Matteo Veroni
 */
public class ApplicationManager implements Disposable, Listenable {

	private final ScreenManager screenManager;
	private final DAO databaseManager;
	private final List<Listener> listeners = new ArrayList<>();
	private User loggedInUser;

	public ApplicationManager(ScreenManager screenManager, DAO databaseManager) {
		this.screenManager = screenManager;
		this.databaseManager = databaseManager;
		setThisApplicationManagerAsScreenControllersManager();
		//		registerThisManagerAsListenerOfAllTheSubManagers();
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

//	@Override
//	public void update() {
//		notifyAllTheSubManagersListeners();
//	}
	
	public void changeScreen(ScreenType screenType) {
		screenManager.useScreen(screenType);
		notifyListeners();
	}

	public User getCurrentUser() {
		return loggedInUser;
	}

	public void setCurrentUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
//		notifyListeners();
	}

	@Override
	public void dispose() {
		screenManager.dispose();
		databaseManager.dispose();
	}

//	private void registerThisManagerAsListenerOfAllTheSubManagers() {
//		screenManager.registerListener(this);
//	}
//
//	private void notifyAllTheSubManagersListeners() {
//		screenManager.notifyListeners();
//	}
	private void setThisApplicationManagerAsScreenControllersManager() {
		for (Manageable controller : screenManager.getScreenControllers()) {
			controller.setManager(this);
		}
	}
}
