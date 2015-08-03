package com.matteoveroni.mydiary.view.screen;

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

    private String name;
    private Scene scene;
    private String resourcePath;
    private FXMLLoader fxml;

    public Screen(String name, String resourcePath) throws IOException {
        fxml = new FXMLLoader(getClass().getResource(resourcePath));
        scene = new Scene((Parent) fxml.load());
        this.resourcePath = resourcePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public FXMLLoader getFxmlLoader() {
        return fxml;
    }

    public void setFxmlLoader(FXMLLoader fxml) {
        this.fxml = fxml;
    }

}
