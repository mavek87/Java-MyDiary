package com.matteoveroni.mydiary.application.manager;

import com.matteoveroni.mydiary.MyDiary;
import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.utilities.patterns.Listenable;
import com.matteoveroni.mydiary.utilities.patterns.Listener;
import com.matteoveroni.mydiary.screen.manager.ScreenManager;
import com.matteoveroni.mydiary.screen.ScreensFramework;
import com.matteoveroni.mydiary.user.model.bean.User;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class ApplicationManager implements Manager, Disposable, Listenable {

	private final ScreenManager screenManager;
	private final DAO database;
	private User loggedInUser;
	private final Set<Listener> listeners = new HashSet<>();
	private final Set<Disposable> resourcesToDisposeWhenApplicationClose = new HashSet<>();
	private static final Logger LOG = LoggerFactory.getLogger(MyDiary.class);

	public ApplicationManager(ScreenManager screenManager, DAO databaseAccessObject) {
		this.screenManager = screenManager;
		this.database = databaseAccessObject;
		resourcesToDisposeWhenApplicationClose.add(this.screenManager);
		resourcesToDisposeWhenApplicationClose.add(this.database);
		setThisApplicationManagerAsScreenControllersManager();
		LOG.debug(" ---> ApplicationManagerInitialized");
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
	public void changeScreen(ScreensFramework screenType) {
		LOG.debug(" ---> Changing screen from " + screenManager.getCurrentScreen().getName() + " to " + screenType);
		screenManager.useScreen(screenType);
		notifyListeners();
	}

	@Override
	public User getLoggedInUser() {
		return this.loggedInUser;
	}

	@Override
	public void setLoggedInUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	@Override
	public void dispose() {
		for (Disposable resource : resourcesToDisposeWhenApplicationClose) {
			resource.dispose();
		}
	}

	private void setThisApplicationManagerAsScreenControllersManager() {
		LOG.debug(" ---> Setting the application manager as manager for all the controllers");
		for (Manageable controller : screenManager.getScreenControllers()) {
			controller.setManager(this);
		}
	}
}
