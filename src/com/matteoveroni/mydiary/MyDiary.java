package com.matteoveroni.mydiary;

import com.matteoveroni.mydiary.application.ApplicationManager;
import com.matteoveroni.mydiary.application.ApplicationBuilder;
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
    private static final String APPLICATION_VERSION = "0.0.5";

    private ApplicationBuilder applicationBuilder;
    private ApplicationManager applicationManager;

    @Override
    public void start(Stage applicationStage) throws IOException {
        applicationBuilder = new ApplicationBuilder(APPLICATION_NAME, APPLICATION_VERSION, applicationStage);
        applicationManager = applicationBuilder.build();
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
        applicationManager.dispose();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
