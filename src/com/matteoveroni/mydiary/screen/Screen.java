package com.matteoveroni.mydiary.screen;

import com.matteoveroni.mydiary.bundles.ResourceBundleFramework;
import com.matteoveroni.mydiary.screen.framework.ScreensFramework;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Screen Object
 *
 * @author Matteo Veroni
 */
public class Screen {

	private final ScreensFramework screenType;
	private FXMLLoader FXMLScreen;
	private String name;
	private String resourcePath;
	private Scene scene;

	private static final Logger LOG = LoggerFactory.getLogger(Screen.class);

	public Screen(ScreensFramework selectedScreenType, Locale locale) throws IOException {
		screenType = selectedScreenType;
		FXMLScreen = new FXMLLoader(getClass().getResource(screenType.getScreenResourcePath()));
		name = screenType.name();
		resourcePath = screenType.getScreenResourcePath();
		initializeScreenWithRequestedLocale(FXMLScreen, locale);
		scene = new Scene((Parent) FXMLScreen.load());
	}

	private void initializeScreenWithRequestedLocale(FXMLLoader fxml, Locale locale) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(ResourceBundleFramework.RESOURCE_BUNDLE_FILE_PATH, locale);
		fxml.setResources(resourceBundle);
	}

	public ScreensFramework getScreenType() {
		return screenType;
	}

	public FXMLLoader getFXML() {
		return FXMLScreen;
	}

	public void setFXML(FXMLLoader fxml) {
		this.FXMLScreen = fxml;
	}

	public <T> T getController() {
		return FXMLScreen.getController();
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
