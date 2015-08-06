package com.matteoveroni.mydiary;

import com.matteoveroni.mydiary.screen.ScreenManager;
import com.matteoveroni.mydiary.screen.ScreenManagerBuilder;
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
    private static final String APPLICATION_VERSION = "0.0.4";

    private ScreenManagerBuilder applicationManagerBuilder;
    private ScreenManager application;

    @Override
    public void start(Stage applicationStage) throws IOException {
        
        ScreenManagerBuilder screenManagerBuilder = new ScreenManagerBuilder(APPLICATION_NAME, APPLICATION_VERSION, applicationStage);
        ScreenManager screenManager = screenManagerBuilder.build();

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
//        application.stop();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
