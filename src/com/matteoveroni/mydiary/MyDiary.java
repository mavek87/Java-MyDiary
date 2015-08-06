package com.matteoveroni.mydiary;

import com.matteoveroni.mydiary.resources.ResourcesManager;
import com.matteoveroni.mydiary.model.manager.ApplicationManager;
import com.matteoveroni.mydiary.model.manager.ApplicationManagerBuilder;
import com.matteoveroni.mydiary.screen.Screen;
import com.matteoveroni.mydiary.screen.ScreenType;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
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
//        applicationManagerBuilder = new ApplicationManagerBuilder(APPLICATION_NAME, APPLICATION_VERSION, primaryStage, resourcesManager);
//        application = applicationManagerBuilder.build();
        ApplicationManager a = new ApplicationManager(primaryStage, resourcesManager);
        
        Screen articleScreen = new Screen(ScreenType.ARTICLE_SCREEN);
        a.loadScreen(articleScreen);
        a.useScreen(articleScreen.getName());
        System.out.println("fine");

    }

//    @Override
//    public void start(Stage primaryStage) throws IOException {
//        try{
//        System.out.println("hardcoded: " + getClass().getResource(""));
//        Parent root = FXMLLoader.load(getClass().getResource("ArticleScreen.fxml"));
//        Scene scene = new Scene(root);
//        primaryStage.setScene(scene); 
//        primaryStage.show();
//        }catch(Exception ex){
//            System.out.println("Eccezione: " + ex);
//            throw new RuntimeException();
//        }
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
