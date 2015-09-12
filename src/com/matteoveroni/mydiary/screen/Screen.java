package com.matteoveroni.mydiary.screen;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Screen Object
 *
 * @author Matteo Veroni
 */
public class Screen {

	private final ScreensFramework screenType;
	private FXMLLoader fxml;
	private String name;
	private String resourcePath;
	private Scene scene;
    
	public Screen(ScreensFramework selectedScreenType) throws IOException {
		screenType = selectedScreenType;
		fxml = new FXMLLoader(getClass().getResource(screenType.getScreenResourcePath()));
		name = screenType.name();
		resourcePath = screenType.getScreenResourcePath();
		scene = new Scene((Parent) fxml.load());
	}

	public ScreensFramework getScreenType() {
		return screenType;
	}

	public FXMLLoader getFXML() {
		return fxml;
	}

	public void setFXML(FXMLLoader fxml) {
		this.fxml = fxml;
	}

	public <T> T getController() {
		return fxml.getController();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
}
