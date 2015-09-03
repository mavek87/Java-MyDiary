package com.matteoveroni.mydiary;

import com.matteoveroni.mydiary.application.ApplicationManager;
import com.matteoveroni.mydiary.application.ApplicationBuilder;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

/**
 *
 * @author Matteo Veroni
 */
public class MyDiary extends Application {

    private static final String APPLICATION_NAME = "MyDiary";
    private static final String APPLICATION_VERSION = "0.0.14";

    private ApplicationBuilder applicationBuilder;
    private ApplicationManager applicationManager;

    @Override
    public void start(Stage applicationStage) {
        applicationBuilder = new ApplicationBuilder(APPLICATION_NAME, APPLICATION_VERSION, applicationStage);
        applicationManager = applicationBuilder.build();
    }

    @Override
    public void stop() {
        applicationManager.dispose();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
