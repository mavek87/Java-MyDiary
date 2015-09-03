package com.matteoveroni.mydiary.application.builder;

import com.matteoveroni.mydiary.application.manager.ApplicationManager;
import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.screen.Screen;
import com.matteoveroni.mydiary.screen.ScreenManager;
import com.matteoveroni.mydiary.screen.ScreenType;
import com.matteoveroni.mydiary.screen.ScreensFactory;
import javafx.stage.Stage;

/**
 *
 * @author Matteo Veroni
 */
public class ApplicationBuilder{

    private ApplicationManager applicationManager;
    private final DAO databaseManager = DAO.getInstance();
    private final ScreenManager screenManager;
    private final ScreensFactory screensFactory = ScreensFactory.getInstance();

    private final String applicationName;
    private final String applicationVersion;

    public ApplicationBuilder(String applicationName, String applicationVersion, Stage primaryStage) {
        this.applicationName = applicationName;
        this.applicationVersion = applicationVersion;
        this.screenManager = new ScreenManager(primaryStage);
    }

    public ApplicationManager build() {
        screenManagerSetup();
        loadScreensInScreenManager();
        useInitialScreen();
        applicationManager = new ApplicationManager(screenManager, databaseManager);
        return applicationManager;
    }

    private void screenManagerSetup() {
        screenManager.getApplicationStage().setTitle(applicationName + " - v. " + applicationVersion);
        screenManager.getApplicationStage().show();
        centerWindow();
    }

    private void loadScreensInScreenManager() {
        int indexOfTheCurrentScreenToBuild = 0;
        try {
            for (ScreenType screenTypeToBuild : ScreenType.values()) {
                Screen newScreen = screensFactory.createScreen(screenTypeToBuild);
                screenManager.loadScreen(newScreen);
                indexOfTheCurrentScreenToBuild++;
            }
        } catch (Exception ex) {
            throw new RuntimeException("IMPOSSIBLE TO BUILD SCREEN -> \'" + ScreenType.values()[indexOfTheCurrentScreenToBuild] + "\'!\n\nException occurred: \n" + ex);
        }
    }

    private void useInitialScreen() {
        screenManager.useScreen(ScreenType.LOGIN_SCREEN);
    }

    private void centerWindow() {
//        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
//        applicationManager.getApplicationStage().setX((primScreenBounds.getWidth() - applicationManager.getApplicationStage().getWidth()) / 2);
//        applicationManager.getApplicationStage().setY((primScreenBounds.getHeight() - applicationManager.getApplicationStage().getHeight()) / 4);
    }
}
