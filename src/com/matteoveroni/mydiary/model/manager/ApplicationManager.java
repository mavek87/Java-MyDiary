package com.matteoveroni.mydiary.model.manager;

import com.matteoveroni.mydiary.resources.ResourcesManager;
import com.matteoveroni.mydiary.screen.Screen;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Matteo Veroni
 */
public class ApplicationManager {

    private static ApplicationManager applicationManager;
    private final Map<String, Screen> applicationScreens = new HashMap<>();
    private Stage applicationStage;
    private ResourcesManager resourcesManager;
    private Screen currentScreenSetted;

    public ApplicationManager(Stage applicationStage, ResourcesManager resourcesManager) {
        this.applicationStage = applicationStage;
        this.resourcesManager = resourcesManager;
    }

    public void setApplicationStage(Stage stage) {
        this.applicationStage = stage;
    }

    public Stage getApplicationStage() {
        return applicationStage;
    }

    public void loadScreen(Screen screen) {
        try {
            applicationScreens.put(screen.getName(), screen);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

//    public void loadScreens(List<Screen> screens) {
//        for (Screen screen : screens) {
//            try {
//                applicationScreens.put(screen.getName(), screen);
//            } catch (Exception ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }

    public void useScreen(String screenName) {
        
        if (applicationScreens.containsKey(screenName) && this.getApplicationStage() != null) {
            applicationStage.setScene(applicationScreens.get(screenName).getScene());
        } else {
            throw new RuntimeException("Screen Manager wasn\'t initialized with a main stage");
        }
    }

    public void setResourcesManager(ResourcesManager resourcesManager) {
        this.resourcesManager = resourcesManager;
    }

    public Scene getCurrentScene() {
        return applicationStage.getScene();
    }

//    public Object getCurrentSceneController() {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(applicationScreens.get(currentScreenSetted.getName()).getResourcePath()));
//        return (Object) fxmlLoader.getController();
//    }

    public void stop() {
        resourcesManager.disposeAll();
    }
}
