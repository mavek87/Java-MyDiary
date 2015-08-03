package com.matteoveroni.mydiary;

import com.matteoveroni.mydiary.model.manager.resources.ResourcesManager;
import com.matteoveroni.mydiary.model.manager.ApplicationManager;
import com.matteoveroni.mydiary.model.manager.ApplicationManagerBuilder;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Matteo Veroni
 */
public class MyDiary extends Application {

    private static final String APPLICATION_NAME = "MyDiary";
    private static final String APPLICATION_VERSION = "0.0.3";

    private ApplicationManagerBuilder applicationManagerBuilder;
    private ApplicationManager application;
    private ResourcesManager resourcesManager;

    @Override
    public void start(Stage primaryStage) throws IOException {
        resourcesManager = new ResourcesManager();
        applicationManagerBuilder = new ApplicationManagerBuilder(APPLICATION_NAME, APPLICATION_VERSION, primaryStage, resourcesManager);
        application = applicationManagerBuilder.build();

//        Parent root = FXMLLoader.load(getClass().getResource("/mydiary/resources/ArticleScreen.fxml"));
//        Scene scene = new Scene(root);
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }

//    @Override
//    public void start(Stage primaryStage) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("resources/ArticleScreen.fxml"));
//        Scene scene = new Scene(root);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }

    @Override
    public void stop() {
        application.stop();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
