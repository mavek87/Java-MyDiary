package com.matteoveroni.mydiary.screen;

import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Matteo Veroni
 */
public class ScreenManager implements Disposable{

    private final Map<ScreenType, Screen> applicationScreens = new HashMap<>();
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
			ManageableScreen screenController = screen.getController();
			screenController.setScreenManager(this);
            applicationScreens.put(screen.getScreenType(), screen);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
	
    public void useScreen(ScreenType screenTypeToUse) {
        if (applicationScreens.containsKey(screenTypeToUse) && this.getApplicationStage() != null) {
            mainStage.setScene(applicationScreens.get(screenTypeToUse).getScene());
            mainStage.show();
        } else {
            throw new RuntimeException("Screen Manager wasn\'t initialized with a main stage");
        }
    }

    public Scene getCurrentScene() {
        return mainStage.getScene();
    }

	@Override
	public void dispose() {
		mainStage.close();
		applicationScreens.clear();
	}
}
