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
	private FXMLLoader fxml;
	private String name;
	private String resourcePath;
	private Scene scene;

	private static final Logger LOG = LoggerFactory.getLogger(Screen.class);

	public Screen(ScreensFramework selectedScreenType, Locale locale) throws IOException {
		screenType = selectedScreenType;
		fxml = new FXMLLoader(getClass().getResource(screenType.getScreenResourcePath()));
		name = screenType.name();
		resourcePath = screenType.getScreenResourcePath();
		initializeScreenWithDefaultResourceBundle(fxml);
		scene = new Scene((Parent) fxml.load());
	}

	private void initializeScreenWithDefaultResourceBundle(FXMLLoader fxml) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(ResourceBundleFramework.RESOURCE_BUNDLE_PATH, ResourceBundleFramework.SUPPORTED_DEFAULT_LOCALE);
		fxml.setResources(resourceBundle);
	}

//	public void changeResourceBundler(Locale locale) {
//		try {
//			fxml = new FXMLLoader(getClass().getResource(screenType.getScreenResourcePath()));
//			resourcePath = screenType.getScreenResourcePath();
//			ResourceBundle resourceBundle = ResourceBundle.getBundle(ResourceBundleFramework.RESOURCE_BUNDLE_PATH, locale);
//			fxml.setResources(resourceBundle);
//			scene = new Scene((Parent) fxml.load());
//			LOG.debug("Screen " + name + " resource bundle translated in -> " + locale.getLanguage());
//		} catch (Exception ex) {
//			throw new RuntimeException();
//		}
//	}

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
