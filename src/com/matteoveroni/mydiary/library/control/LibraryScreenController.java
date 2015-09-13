package com.matteoveroni.mydiary.library.control;

import com.matteoveroni.mydiary.application.manager.Manageable;
import com.matteoveroni.mydiary.application.manager.Manager;
import com.matteoveroni.mydiary.utilities.patterns.Listener;
import com.matteoveroni.mydiary.annotation.model.bean.Annotation;
import com.matteoveroni.mydiary.application.manager.DataObjectMessage;
import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.diary.model.DiaryModel;
import com.matteoveroni.mydiary.diary.model.HibernateDiaryModel;
import com.matteoveroni.mydiary.diary.model.bean.Diary;
import com.matteoveroni.mydiary.login.control.LoginScreenController;
import com.matteoveroni.mydiary.screen.ScreensFramework;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private final Diary selectedDiary = new Diary();
    private final DiaryModel model = new HibernateDiaryModel();

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
    private ComboBox<Diary> cmb_chooseDiary;
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
    }

    @Override
    public void setManager(Manager manager) {
        this.manager = manager;
        manager.registerListener(this);
    }

    @Override
    public void update(DataObjectMessage pushedData) {
        if (manager.getLoggedInUser() != null) {
            DAO databaseManager = DAO.getInstance();

            List<Diary> diaries = databaseManager.readAll(Diary.class);
            List<Diary> userDiaries = new ArrayList<>();

            for (Diary diary : diaries) {
                if (diary.getOwnerUser().equals(manager.getLoggedInUser())) {
                    userDiaries.add(diary);
                }
            }

            ObservableList<Diary> observableUserDiaries = FXCollections.observableArrayList(userDiaries);
            cmb_chooseDiary.setItems(observableUserDiaries);
        }

        if (cmb_chooseDiary.getItems().size() == 0) {
            cmd_openDiary.setDisable(true);
        }
    }

    @FXML
    void openDiaryPaneSelected(ActionEvent event) {
//        cmd_createDiary.setDisable(true);
        
    }

    @FXML
    void openSelectedDiary(ActionEvent event) {
        if (selectedDiary != null) {
            manager.storeObjectToPush(selectedDiary, LibraryScreenController.class);
            manager.changeScreen(ScreensFramework.DIARY_SCREEN);
        }
    }

    @FXML
    void createNewDiaryPaneSelected(ActionEvent event) {
        
    }

    @FXML
    void createNewDiary(ActionEvent event) {
        DAO databaseManager = DAO.getInstance();
        Diary diary = new Diary();
        diary.setOwnerUser(manager.getLoggedInUser());
        databaseManager.write(diary);
    }

    @FXML
    void manageDiaryPaneSelected(ActionEvent event) {

    }

    private void resetSelectionPane() {

    }

    private void resetCreationPane() {

    }

    private void resetManagementPane() {

    }
}
