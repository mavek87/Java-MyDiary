package com.matteoveroni.mydiary.screen.manager;

import com.matteoveroni.mydiary.application.manager.Manageable;
import com.matteoveroni.mydiary.bundles.ResourceBundleFramework;
import com.matteoveroni.mydiary.screen.Screen;
import com.matteoveroni.mydiary.screen.factory.ScreenFactory;
import com.matteoveroni.mydiary.screen.framework.ScreensFramework;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class ScreenManager implements Disposable {

	private final Map<ScreensFramework, Screen> applicationScreens = new HashMap<>();
	private final List<Manageable> screenControllers = new ArrayList<>();
	private Screen currentScreen;
	private final Stack<Screen> previouslyUsedScreens = new Stack<>();
	private Stage mainStage;

	private static final Logger LOG = LoggerFactory.getLogger(ScreenManager.class);

	public ScreenManager(Stage applicationStage) {
		this.mainStage = applicationStage;
	}

	public void setApplicationStage(Stage stage) {
		this.mainStage = stage;
	}

	public Stage getApplicationStage() {
		return mainStage;
	}

	public void loadScreen(Screen screen) {
		try {
			LOG.debug("Loading screen " + screen.getName() + " controller");
			screenControllers.add((Manageable) screen.getController());
			LOG.debug("Saving screen " + screen.getName() + " inside screen manager");
			applicationScreens.put(screen.getScreenType(), screen);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void useScreen(ScreensFramework screenTypeToUse) {
		if (applicationScreens.containsKey(screenTypeToUse) && this.getApplicationStage() != null) {
			Screen screenToUse = applicationScreens.get(screenTypeToUse);
			try {
				LOG.debug("Saving the previous screen in use that was -> " + currentScreen.getName());
			} catch (NullPointerException ex) {
			}
			previouslyUsedScreens.push(currentScreen);
			currentScreen = screenToUse;
			LOG.debug("New screen to use -> " + currentScreen.getName());
			LOG.debug("Uploading the stage with the current screen " + currentScreen.getName());
			mainStage.setScene(screenToUse.getScene());
			mainStage.show();
		} else {
			throw new RuntimeException(" ---> Screen Manager wasn\'t initialized with a main stage");
		}
	}

	public void usePreviousScreen() {
		if (!previouslyUsedScreens.empty()) {
			Screen previousScreenToUse = previouslyUsedScreens.pop();
			currentScreen = previousScreenToUse;
			mainStage.setScene(previousScreenToUse.getScene());
			mainStage.show();
		}
	}

	public void changeResourceBundleForEachScreen(Locale locale) {

		applicationScreens.clear();
		screenControllers.clear();

		Screen screenInUse = currentScreen;

		int indexOfTheCurrentScreenToBuild = 0;
		try {
			for (ScreensFramework screenTypeToBuild : ScreensFramework.values()) {
				Screen newScreen = ScreenFactory.createScreen(screenTypeToBuild, locale);
				loadScreen(newScreen);
				indexOfTheCurrentScreenToBuild++;
			}
		} catch (Exception ex) {
			LOG.error(" ---> IMPOSSIBLE TO BUILD THE SCREEN -> \'" + ScreensFramework.values()[indexOfTheCurrentScreenToBuild] + "\'!\n\nException occurred: \n" + ex);
			throw new RuntimeException(ex);
		}

		useScreen(screenInUse.getScreenType());

//		LOG.info("Translating every screen in -> " + locale.getLanguage());
//		for (Screen screen : applicationScreens.values()) {
//			screen.changeResourceBundler(locale);
//		}
//		LOG.info("Updating the current screen with a translated one");
//		mainStage.setScene(currentScreen.getScene());
//		mainStage.show();
	}

	public List<Manageable> getScreenControllers() {
		return screenControllers;
	}

	public Scene getCurrentScene() {
		return mainStage.getScene();
	}

	public Screen getCurrentScreen() {
		return currentScreen;
	}

	public Screen getPreviousScreen() {
		return previouslyUsedScreens.peek();
	}

	@Override
	public void dispose() {
		mainStage.close();
		screenControllers.clear();
		applicationScreens.clear();
	}
}
