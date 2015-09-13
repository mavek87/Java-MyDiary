package com.matteoveroni.mydiary.library.control;

import com.matteoveroni.mydiary.application.manager.Manageable;
import com.matteoveroni.mydiary.application.manager.Manager;
import com.matteoveroni.mydiary.utilities.patterns.Listener;
import com.matteoveroni.mydiary.application.manager.DataObjectMessage;
import com.matteoveroni.mydiary.diary.model.bean.Diary;
import com.matteoveroni.mydiary.library.model.LibraryModel;
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
    private final LibraryModel model = new LibraryModel();

    @FXML
    private Tab tab_manageDiary;
    @FXML
    private Tab tab_selectDiary;
    @FXML
    private Tab tab_createNewDiary;
    @FXML
    private Button btn_createDiary;
    @FXML
    private Button btn_openDiary;
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
//        if (manager.getLoggedInUser() != null) {
//
//            if (cmb_chooseDiary.getItems().size() == 0) {
//                btn_openDiary.setDisable(true);
//            }
//        }
    }

    @FXML
    void openSelectedDiary(ActionEvent event) {
        if (selectedDiary != null) {
            manager.storeObjectToPush(selectedDiary, LibraryScreenController.class);
            manager.changeScreen(ScreensFramework.DIARY_SCREEN);
        }
    }

    @FXML
    void createNewDiary(ActionEvent event) {
        if (txt_newDiaryName.getText() != null && !txt_newDiaryName.getText().trim().equals("")) {
            Diary diary = new Diary();
            diary.setName(txt_newDiaryName.getText());
            diary.setOwnerUser(manager.getLoggedInUser());
            model.createNewDiary(diary);
            update(null);
        }
    }

    @FXML
    void tabSelectDiaryActive() {
//        resetCreateNewDiaryTab();
//        resetManageDiaryTab();
        updateDiaryComboBox();
        if (cmb_chooseDiary.getItems().size() == 0) {
            btn_openDiary.setDisable(true);
        } else {
            btn_openDiary.setDisable(false);
        }
    }

    @FXML
    void tabCreateNewDiaryActive() {
//        resetSelectDiaryTab();
//        resetManageDiaryTab();
    }

    @FXML
    void tabManageDiaryActive() {
//        resetSelectDiaryTab();
//        resetCreateNewDiaryTab();
    }

    private void updateDiaryComboBox() {
        List<Diary> allTheDiaries = model.readAllTheDiaries();
        List<Diary> userDiaries = new ArrayList<>();

        for (Diary diary : allTheDiaries) {
            if (diary.getOwnerUser().equals(manager.getLoggedInUser())) {
                userDiaries.add(diary);
            }
        }

        ObservableList<Diary> observableUserDiaries = FXCollections.observableArrayList(userDiaries);
        cmb_chooseDiary.setItems(observableUserDiaries);
    }

    private void resetSelectDiaryTab() {
//        btn_createDiary.setDisable(true);
//        btn_openDiary.setDisable(false);
    }

    private void resetCreateNewDiaryTab() {
//        txt_newDiaryName.setText("");
//        btn_createDiary.setDisable(false);
    }

    private void resetManageDiaryTab() {
//        btn_createDiary.setDisable(true);
//        btn_openDiary.setDisable(true);
    }
}
