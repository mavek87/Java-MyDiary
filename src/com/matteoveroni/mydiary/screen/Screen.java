package com.matteoveroni.mydiary.screen;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Screen Object
 *
 * @author Matteo Veroni
 */
public class Screen {

    private FXMLLoader fxml;
    private String name;
    private String resourcePath;
    private Scene scene;

    public Screen(ScreenType selectedScreen) throws IOException {
        System.out.println("screen constructor: " + getClass().getResource("") + "aaaaaaaaaaaaaaaaaaaaaa: " + getClass().getResource(selectedScreen.getScreenResourcePath()));
        fxml = new FXMLLoader(getClass().getResource(selectedScreen.getScreenResourcePath()));
//        fxml = new FXMLLoader(getClass().getResource("ArticleScreen.fxml"));
        name = selectedScreen.name();
        resourcePath = selectedScreen.getScreenResourcePath();
        scene = new Scene((Parent) fxml.load());
        System.out.println("cane rognoso");
    }

    public FXMLLoader getFxmlLoader() {
        return fxml;
    }

    public void setFxmlLoader(FXMLLoader fxml) {
        this.fxml = fxml;
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
