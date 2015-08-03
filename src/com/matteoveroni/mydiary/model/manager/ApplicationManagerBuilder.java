package com.matteoveroni.mydiary.model.manager;

import com.matteoveroni.mydiary.model.manager.resources.ResourcesManager;
import com.matteoveroni.mydiary.view.screen.Screen;
import com.matteoveroni.mydiary.view.screen.ScreensFactory;
import com.matteoveroni.mydiary.view.screen.Screens;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.Stage;

/**
 *
 * @author Matteo Veroni
 */
public class ApplicationManagerBuilder {

    private final ApplicationManager applicationManager;
    private final ScreensFactory screensFactory = ScreensFactory.getInstance();
    private final List<Screen> applicationScreens = new ArrayList<>();

    private final String applicationName;
    private final String applicationVersion;

    public ApplicationManagerBuilder(String applicationName, String applicationVersion, Stage primaryStage, ResourcesManager resourcesManager) {
        this.applicationName = applicationName;
        this.applicationVersion = applicationVersion;
        this.applicationManager = new ApplicationManager(primaryStage, resourcesManager);
    }

    public ApplicationManager build() {
        mainStageSetup();
        buildScreens();
        loadScreens();
        applicationManager.useScreen(Screens.ARTICLE_SCREEN.screenName());
        return applicationManager;
    }

    private void buildScreens() {
        try {
            Screen articleScreen = new Screen(Screens.ARTICLE_SCREEN.screenName(), Screens.ARTICLE_SCREEN.screenResource());

//            applicationScreens.add(articleScreen);
//            for(Screen screen : applicationScreens){
//                System.out.println("Ho creato lo schermo" + screen.getName() + " " + screen.getResourcePath());
//            }
        } catch (IOException ex) {
            throw new RuntimeException();
        }
    }

    private void loadScreens() {
        applicationManager.loadScreen(applicationScreens.get(0));
    }

    private void mainStageSetup() {
        applicationManager.getApplicationStage().setResizable(true);
        applicationManager.getApplicationStage().setMaxWidth(800);
        applicationManager.getApplicationStage().setMaxHeight(600);
        applicationManager.getApplicationStage().setMinWidth(640);
        applicationManager.getApplicationStage().setMinHeight(480);
        applicationManager.getApplicationStage().setTitle(applicationName + " - v. " + applicationVersion);
        applicationManager.getApplicationStage().show();
        centerWindow();
    }

    private void centerWindow() {
//        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
//        applicationManager.getApplicationStage().setX((primScreenBounds.getWidth() - applicationManager.getApplicationStage().getWidth()) / 2);
//        applicationManager.getApplicationStage().setY((primScreenBounds.getHeight() - applicationManager.getApplicationStage().getHeight()) / 4);
    }
}
