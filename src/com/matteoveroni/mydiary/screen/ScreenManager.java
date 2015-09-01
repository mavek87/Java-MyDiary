package com.matteoveroni.mydiary.screen;

import com.matteoveroni.mydiary.Observer;
import com.matteoveroni.mydiary.Observable;
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
public class ScreenManager implements Disposable, Observable{

    private final Map<ScreenType, Screen> applicationScreens = new HashMap<>();
	private final List<Observer> observersScreens = new ArrayList<>();
    private Stage mainStage;

    public ScreenManager(Stage applicationStage) {
        this.mainStage = applicationStage;
    }

	@Override
	public void registerObserver(Observer newObserverScreen) {
		observersScreens.add(newObserverScreen);
	}

	@Override
	public void removeObserver(Observer observerScreen) {
		observersScreens.remove(observerScreen);
	}

	@Override
	public void notifyObservers() {
		for(Observer observer : observersScreens){
			observer.update();
		}
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
            
            Screen screen = applicationScreens.get(screenTypeToUse);
//            ManageableScreen screenController = screen.getController();
//            screenController.updateScreen();
			notifyObservers();
            
			// da rivedere le righe seguenti non so se servono!
            mainStage.setScene(screen.getScene());
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
		observersScreens.clear();
		mainStage.close();
		applicationScreens.clear();
	}
}
