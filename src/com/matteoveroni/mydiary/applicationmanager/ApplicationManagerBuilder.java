package com.matteoveroni.mydiary.applicationmanager;

import com.matteoveroni.mydiary.model.database.DatabaseManager;
import com.matteoveroni.mydiary.screen.ScreenManager;
import com.matteoveroni.mydiary.screen.ScreenType;
import com.matteoveroni.mydiary.screen.ScreensFactory;
import com.matteoveroni.mydiary.screen.model.Screen;
import java.io.IOException;
import javafx.stage.Stage;

/**
 *
 * @author Matteo Veroni
 */
public class ApplicationManagerBuilder {

    private final ApplicationManager applicationManager = new ApplicationManager();
    private final DatabaseManager databaseManager = new DatabaseManager();
    private final ScreenManager screenManager;
    private final ScreensFactory screensFactory = ScreensFactory.getInstance();

    private final String applicationName;
    private final String applicationVersion;

    public ApplicationManagerBuilder(String applicationName, String applicationVersion, Stage primaryStage) {
        this.applicationName = applicationName;
        this.applicationVersion = applicationVersion;
        this.screenManager = new ScreenManager(primaryStage);
    }

    public ApplicationManager build() {
        mainStageSetup();
        buildScreens();
        useInitialScreen();
        applicationManager.setScreenManager(screenManager);
        applicationManager.setDatabaseManager(databaseManager);
        return applicationManager;
    }

    private void mainStageSetup() {
        screenManager.getApplicationStage().setResizable(true);
        screenManager.getApplicationStage().setMaxWidth(800);
        screenManager.getApplicationStage().setMaxHeight(600);
        screenManager.getApplicationStage().setMinWidth(640);
        screenManager.getApplicationStage().setMinHeight(480);
        screenManager.getApplicationStage().setTitle(applicationName + " - v. " + applicationVersion);
        screenManager.getApplicationStage().show();
        centerWindow();
    }

    private void buildScreens() {
        try {
            Screen articleScreen = screensFactory.createScreen(ScreenType.ARTICLE_SCREEN);
            screenManager.loadScreen(articleScreen);
        } catch (IOException ex) {
            throw new RuntimeException();
        }
    }

    private void useInitialScreen() {
        screenManager.useScreen(ScreenType.ARTICLE_SCREEN);
    }

    private void centerWindow() {
//        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
//        applicationManager.getApplicationStage().setX((primScreenBounds.getWidth() - applicationManager.getApplicationStage().getWidth()) / 2);
//        applicationManager.getApplicationStage().setY((primScreenBounds.getHeight() - applicationManager.getApplicationStage().getHeight()) / 4);
    }
}
