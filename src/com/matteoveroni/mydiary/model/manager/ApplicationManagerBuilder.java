package com.matteoveroni.mydiary.model.manager;

import com.matteoveroni.mydiary.resources.ResourcesManager;
import com.matteoveroni.mydiary.screen.Screen;
import com.matteoveroni.mydiary.screen.ScreensFactory;
import com.matteoveroni.mydiary.screen.ScreenType;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.stage.Stage;

/**
 *
 * @author Matteo Veroni
 */
public class ApplicationManagerBuilder {

    private final ApplicationManager applicationManager;
    private final ScreensFactory screensFactory = ScreensFactory.getInstance();
    private final Map<String, Screen> applicationScreens = new HashMap<>();

    private final String applicationName;
    private final String applicationVersion;

    public ApplicationManagerBuilder(String applicationName, String applicationVersion, Stage primaryStage, ResourcesManager resourcesManager) {
        this.applicationName = applicationName;
        this.applicationVersion = applicationVersion;
        this.applicationManager = new ApplicationManager(primaryStage, resourcesManager);
    }

    public ApplicationManager build() {
        mainStageSetup();
        System.out.println("\n1");
        buildScreens();
                System.out.println("\n2");

        loadScreens();
                System.out.println("\n3");

        applicationManager.useScreen(ScreenType.ARTICLE_SCREEN.getScreenName());
                System.out.println("\n4");

        return applicationManager;
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

    private void buildScreens() {
        try {
            Screen articleScreen = new Screen(ScreenType.ARTICLE_SCREEN);
            applicationScreens.put("ArticleScreen", articleScreen);
//            for(Screen screen : applicationScreens){
//                System.out.println("Ho creato lo schermo" + screen.getName() + " " + screen.getResourcePath());
//            }
        } catch (IOException ex) {
            throw new RuntimeException();
        }
    }

    private void loadScreens() {
        Screen articleScreen = applicationScreens.get("ArticleScreen");
        applicationManager.loadScreen(articleScreen);
    }

    private void centerWindow() {
//        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
//        applicationManager.getApplicationStage().setX((primScreenBounds.getWidth() - applicationManager.getApplicationStage().getWidth()) / 2);
//        applicationManager.getApplicationStage().setY((primScreenBounds.getHeight() - applicationManager.getApplicationStage().getHeight()) / 4);
    }
}
