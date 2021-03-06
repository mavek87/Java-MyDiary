package com.matteoveroni.mydiary.application.manager;

import com.matteoveroni.mydiary.application.messages.DataObjectMessage;
import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.screen.Screen;
import com.matteoveroni.mydiary.utilities.patterns.Listenable;
import com.matteoveroni.mydiary.utilities.patterns.Listener;
import com.matteoveroni.mydiary.screen.manager.ScreenManager;
import com.matteoveroni.mydiary.screen.framework.ScreensFramework;
import com.matteoveroni.mydiary.user.model.bean.UserData;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import java.util.HashSet;
import java.util.Locale;
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
	private UserData loggedInUser;

	private final String applicationName;
	private final String applicationVersion;

	private final Set<Listener> listeners = new HashSet<>();
	private DataObjectMessage dataToPush;

	private final Set<Disposable> resourcesToDisposeWhenApplicationClose = new HashSet<>();

	private static final Logger LOG = LoggerFactory.getLogger(ApplicationManager.class);

	public ApplicationManager(String applicationName, String applicationVersion, ScreenManager screenManager, DAO databaseAccessObject) {
		this.applicationName = applicationName;
		this.applicationVersion = applicationVersion;
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
	public void notifyListeners(DataObjectMessage dataToPush) {
		for (Listener listener : listeners) {
			listener.update(dataToPush);
		}
	}

	@Override
	public void changeScreen(ScreensFramework screenType) {
		LOG.debug(" ---> Changing screen from " + screenManager.getCurrentScreen().getName() + " to " + screenType);

		notifyListeners(dataToPush);

		screenManager.useScreen(screenType);
		clearObjectToPush();
	}

	@Override
	public boolean usePreviousScreen() {
		if (screenManager.getPreviousScreen() != null) {
			screenManager.usePreviousScreen();
			return true;
		}
		return false;
	}

	@Override
	public Screen getCurrentScreen() {
		return screenManager.getCurrentScreen();
	}

	@Override
	public UserData getLoggedInUser() {
		return this.loggedInUser;
	}

	@Override
	public void setLoggedInUser(UserData loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	@Override
	public void storeObjectToPush(Object dataToPush, Class senderClass) {
		this.dataToPush = new DataObjectMessage();
		this.dataToPush.setData(dataToPush);
		this.dataToPush.setSenderClass(senderClass);
	}

	private void clearObjectToPush() {
		this.dataToPush = null;
	}

	private void setThisApplicationManagerAsScreenControllersManager() {
		LOG.debug(" ---> Setting the application manager as manager of all the screen controllers");
		for (Manageable controller : screenManager.getScreenControllers()) {
			controller.setManager(this);
		}
	}

	@Override
	public void dispose() {
		for (Disposable resource : resourcesToDisposeWhenApplicationClose) {
			resource.dispose();
		}
	}

	@Override
	public String getApplicationName() {
		return applicationName;
	}

	@Override
	public String getApplicationVersion() {
		return applicationVersion;
	}

	@Override
	public String getApplicationTitle() {
		return this.getApplicationName() + " - v. " + this.getApplicationVersion() + " ";
	}

	@Override
	public void setResourceBundle(Locale locale) {
		screenManager.changeResourceBundleForEachScreen(locale);
	}
}
