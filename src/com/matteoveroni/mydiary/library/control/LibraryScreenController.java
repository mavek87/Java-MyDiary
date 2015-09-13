package com.matteoveroni.mydiary.library.control;

import com.matteoveroni.mydiary.application.manager.Manageable;
import com.matteoveroni.mydiary.application.manager.Manager;
import com.matteoveroni.mydiary.utilities.patterns.Listener;
import com.matteoveroni.mydiary.annotation.model.bean.Annotation;
import com.matteoveroni.mydiary.application.manager.DataObjectMessage;
import com.matteoveroni.mydiary.diary.model.bean.Diary;
import com.matteoveroni.mydiary.diary.model.DiaryModel;
import com.matteoveroni.mydiary.diary.model.HibernateDiaryModel;
import com.matteoveroni.mydiary.diary.model.bean.HibernateDiaryBean;
import com.matteoveroni.mydiary.screen.ScreensFramework;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

/**
 * DiaryScreenController Controller class
 *
 * @author Matteo Veroni
 */
public class LibraryScreenController implements Initializable, Manageable, Listener {

    private Manager manager;
    private final Diary currentDiary = new HibernateDiaryBean();
    private final DiaryModel model = new HibernateDiaryModel();
    private Annotation currentSelectedAnnotation;

    @FXML
    private Tab pane_manageDiary;
    @FXML
    private Tab pane_selectDiary;
    @FXML
    private Tab pane_createNewDiary;
    @FXML
    private Button cmd_createDiary;
    @FXML
    private Button cmd_openDiary;
    @FXML
    private ComboBox<?> cmb_chooseDiary;
    @FXML
    private TextField txt_newDiaryName;

    /**
     * Initializes the DiaryScreenController class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmd_createDiary.setDisable(true);
//        cmd_openDiary.setDisable(true);

    }

    @Override
    public void setManager(Manager manager) {
        this.manager = manager;
        manager.registerListener(this);
    }

    @Override
    public void update(DataObjectMessage pushedData) {
    }

    @FXML
    void openSelectedDiary(ActionEvent event) {
        manager.changeScreen(ScreensFramework.DIARY_SCREEN);
    }

    @FXML
    void createNewDiary(ActionEvent event) {
    }
    
    private void resetSelectionPane(){
        
    }
    
    private void resetCreationPane(){
        
    }
    
    private void resetManagementPane(){
        
    }
}
