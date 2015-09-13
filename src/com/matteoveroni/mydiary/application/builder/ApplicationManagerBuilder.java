package com.matteoveroni.mydiary.application.builder;

import com.matteoveroni.mydiary.application.manager.ApplicationManager;
import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.screen.Screen;
import com.matteoveroni.mydiary.screen.manager.ScreenManager;
import com.matteoveroni.mydiary.screen.ScreensFramework;
import com.matteoveroni.mydiary.screen.factory.ScreenFactory;
import javafx.geometry.Rectangle2D;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class ApplicationManagerBuilder {

    private ApplicationManager applicationManager;
    private final DAO databaseManager = DAO.getInstance();
    private final ScreenManager screenManager;
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationManagerBuilder.class);

    private final String applicationName;
    private final String applicationVersion;

    public ApplicationManagerBuilder(String applicationName, String applicationVersion, Stage primaryStage) {
        this.applicationName = applicationName;
        this.applicationVersion = applicationVersion;
        setWindowDimensions(primaryStage);
        centerWindow(primaryStage);
        this.screenManager = new ScreenManager(primaryStage);
    }

    public ApplicationManager build() {
        LOG.debug(" ---> Starting to build the application manager");
        screenManagerSetup();
        loadScreensInScreenManager();
        useInitialScreen();
        applicationManager = new ApplicationManager(screenManager, databaseManager);
        LOG.debug(" ---> Application manager builded");
        return applicationManager;
    }

    private void screenManagerSetup() {
        LOG.debug(" ---> Starting to setup the screen manager");
        screenManager.getApplicationStage().setTitle("\t" + applicationName + " - v. " + applicationVersion);
        screenManager.getApplicationStage().show();
    }

    private void loadScreensInScreenManager() {
        LOG.debug(" ---> Starting to load all the screens in the screen manager");
        int indexOfTheCurrentScreenToBuild = 0;
        try {
            for (ScreensFramework screenTypeToBuild : ScreensFramework.values()) {
                Screen newScreen = ScreenFactory.createScreen(screenTypeToBuild);
                screenManager.loadScreen(newScreen);
                indexOfTheCurrentScreenToBuild++;
            }
        } catch (Exception ex) {
            LOG.error(" ---> IMPOSSIBLE TO BUILD SCREEN -> \'" + ScreensFramework.values()[indexOfTheCurrentScreenToBuild] + "\'!\n\nException occurred: \n" + ex);
            throw new RuntimeException(ex);
        }
    }

    private void useInitialScreen() {
        LOG.debug(" ---> Use the login screen");
        screenManager.useScreen(ScreensFramework.LOGIN_SCREEN);
    }

    private void setWindowDimensions(Stage primaryStage) {
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
    }

    private void centerWindow(Stage primaryStage) {
        Rectangle2D primScreenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();

        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }
}
