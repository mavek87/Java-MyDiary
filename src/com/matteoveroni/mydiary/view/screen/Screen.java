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

    private FXMLLoader fxml;
    private String name;
    private String resourcePath;
    private Scene scene;

    public Screen(Screens screen) throws IOException {
        fxml = new FXMLLoader(getClass().getResource(screen.screenResource()));
        name = screen.name();
        resourcePath = screen.screenResource();
        scene = new Scene((Parent) fxml.load());
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
