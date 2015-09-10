package com.matteoveroni.mydiary.screen.manager;

import com.matteoveroni.mydiary.application.manager.Manageable;
import com.matteoveroni.mydiary.screen.Screen;
import com.matteoveroni.mydiary.screen.ScreenType;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Matteo Veroni
 */
public class ScreenManager implements Disposable {

	private final Map<ScreenType, Screen> applicationScreens = new HashMap<>();
	private final List<Manageable> screenControllers = new ArrayList<>();
	private Screen currentScreen;
	private Stage mainStage;

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

	public void useScreen(ScreenType screenTypeToUse) {
		if (applicationScreens.containsKey(screenTypeToUse) && this.getApplicationStage() != null) {
			Screen screenToUse = applicationScreens.get(screenTypeToUse);
			currentScreen = screenToUse;
			mainStage.setScene(screenToUse.getScene());
			mainStage.show();
		} else {
			throw new RuntimeException("Screen Manager wasn\'t initialized with a main stage");
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

	@Override
	public void dispose() {
		mainStage.close();
		screenControllers.clear();
		applicationScreens.clear();
	}
}
