package com.matteoveroni.mydiary;

import com.matteoveroni.mydiary.application.manager.ApplicationManager;
import com.matteoveroni.mydiary.application.builder.ApplicationManagerBuilder;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 * @version 0.0.17
 *
 * www.matteoveroni.com || www.infoeinternet.com
 *
 */
public class MyDiary extends Application {

    private static final String APPLICATION_NAME = "MyDiary";
    private static final String APPLICATION_VERSION = "0.0.17";
    private static final Logger LOG = LoggerFactory.getLogger(MyDiary.class);

    private ApplicationManagerBuilder applicationManagerBuilder;
    private ApplicationManager applicationManager;

    @Override
    public void start(Stage applicationStage) {
        LOG.debug(" ---> Application started");
        applicationManagerBuilder = new ApplicationManagerBuilder(APPLICATION_NAME, APPLICATION_VERSION, applicationStage);
        LOG.debug(" ---> ApplicationBuilder initialized");
        applicationManager = applicationManagerBuilder.build();
        LOG.debug(" ---> ApplicationManager builded");
    }

    @Override
    public void stop() {
        LOG.debug(" ---> Application stop");
        applicationManager.dispose();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
