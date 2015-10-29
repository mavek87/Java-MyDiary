package com.matteoveroni.mydiary.screen.manager;

import com.matteoveroni.mydiary.application.manager.Manageable;
import com.matteoveroni.mydiary.screen.Screen;
import com.matteoveroni.mydiary.screen.framework.ScreensFramework;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
	
	private final String RESOURCE_BUNDLE_PATH = "com.matteoveroni.mydiary.bundles.MyBundle";
	
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
			screenControllers.add((Manageable) screen.getController());
			applicationScreens.put(screen.getScreenType(), screen);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void useScreen(ScreensFramework screenTypeToUse) {
		if (applicationScreens.containsKey(screenTypeToUse) && this.getApplicationStage() != null) {
			Screen screenToUse = applicationScreens.get(screenTypeToUse);
			previouslyUsedScreens.push(currentScreen);
			currentScreen = screenToUse;
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

	public void setResourceBundleForEachScreen(Locale locale){
		for(Screen screen : applicationScreens.values()){
			screen.getFXML().setResources(ResourceBundle.getBundle(RESOURCE_BUNDLE_PATH, locale));
		}
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
