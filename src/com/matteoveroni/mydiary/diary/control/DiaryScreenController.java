package com.matteoveroni.mydiary.diary.control;

import com.matteoveroni.mydiary.application.manager.Manageable;
import com.matteoveroni.mydiary.application.manager.Manager;
import com.matteoveroni.mydiary.utilities.patterns.Listener;
import com.matteoveroni.mydiary.note.model.bean.Note;
import com.matteoveroni.mydiary.application.manager.DataObjectMessage;
import com.matteoveroni.mydiary.diary.model.DiaryModel;
import com.matteoveroni.mydiary.diary.model.bean.Diary;
import com.matteoveroni.mydiary.exceptions.CriticalRuntimeException;
import com.matteoveroni.mydiary.library.control.LibraryScreenController;
import com.matteoveroni.mydiary.screen.framework.ScreensFramework;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DiaryScreenController Controller class
 *
 * @author Matteo Veroni
 */
public class DiaryScreenController implements Initializable, Manageable, Listener {

    private Manager manager;
    private final DiaryModel model = new DiaryModel();
    private Diary currentDiary = new Diary();
    private Note currentSelectedNote;

    private static final Logger LOG = LoggerFactory.getLogger(DiaryScreenController.class);

    @FXML
    private TableView<Note> diaryTable;
    @FXML
    private TableColumn<Note, Long> tableColumn_Id;
    @FXML
    private TableColumn<Note, String> tableColumn_Title;
    @FXML
    private TableColumn<Note, Date> tableColumn_CreationDate;
    @FXML
    private TableColumn<Note, Date> tableColumn_LastModificationTimestamp;
    @FXML
    private TableColumn<Note, String> tableColumn_Author;
    @FXML
    private Button btn_filterNote;
    @FXML
    private Button btn_openNote;
    @FXML
    private Button btn_createNewNote;
    @FXML
    private CheckBox chk_enableFilter;
    @FXML
    private Button btn_removeNote;

    /**
     * Initializes the DiaryScreenController class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        diaryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableColumn_Id.setCellValueFactory(new PropertyValueFactory<Note, Long>("id"));
        tableColumn_Title.setCellValueFactory(new PropertyValueFactory<Note, String>("title"));
        tableColumn_CreationDate.setCellValueFactory(new PropertyValueFactory<Note, Date>("creationDate"));
        tableColumn_LastModificationTimestamp.setCellValueFactory(new PropertyValueFactory<Note, Date>("lastModificationTimestamp"));
        tableColumn_Author.setCellValueFactory(new PropertyValueFactory<Note, String>("author"));

        diaryTable.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue) {
                    // diaryTable's focus ON
                    if (diaryTable.getSelectionModel().getSelectedItem() != null) {
                        currentSelectedNote = diaryTable.getSelectionModel().getSelectedItem();
                        btn_openNote.setDisable(false);
                        btn_removeNote.setDisable(false);
                    }
                }
            }
        });

        diaryTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    currentSelectedNote = diaryTable.getSelectionModel().getSelectedItem();
                    openSelectedNote();
                }
            }
        });
    }

    @Override
    public void setManager(Manager manager) {
        this.manager = manager;
        manager.registerListener(this);
    }

    @Override
    public void update(DataObjectMessage pushedData) {
        if (manager.getLoggedInUser() != null) {
            try {
                if (pushedData != null && pushedData.getSenderClass().equals(LibraryScreenController.class)) {
                    currentDiary = (Diary) pushedData.getData();
                    model.setDiary(currentDiary);
                    currentSelectedNote = null;

                    List<Note> notesFromDatabase = model.getAllTheNotes();
                    System.out.println("notes size" + notesFromDatabase.size());

                    if (notesFromDatabase.size() > 0) {
                        ObservableList<Note> annotationsForTable = FXCollections.observableArrayList(notesFromDatabase);
                        diaryTable.setItems(annotationsForTable);
                    }
                    btn_openNote.setDisable(true);
                    btn_removeNote.setDisable(true);
                }
            } catch (Exception ex) {
                LOG.error("Critical Runtime Exception Occurred -> " + ex.getMessage());
                throw new CriticalRuntimeException(ex, manager);
            }
        }
    }

    @FXML
    void goToNoteScreen(ActionEvent event) {
        if (currentSelectedNote != null) {
            manager.storeObjectToPush(currentSelectedNote, DiaryScreenController.class);
            manager.changeScreen(ScreensFramework.NOTE_SCREEN);
        }
    }

    @FXML
    void removeNote(ActionEvent event) {
        if (currentSelectedNote != null) {
            model.removeAnnotation(currentSelectedNote);
            update(null);
        }
    }

    @FXML
    void createNewNote(ActionEvent event) {
        Note newNote = new Note();
        newNote.setTitle("New Title");
//        newAnnotation.setAuthor(manager.getLoggedInUser().toString());
        newNote.setCreationDate(new Date());
        newNote.setLastModificationTimestamp(new Date());
        model.createNewNote(newNote);
        manager.changeScreen(ScreensFramework.NOTE_SCREEN);
    }

    @FXML
    void goToFilterScreen(ActionEvent event) {
    }

    @FXML
    void enableFilter(ActionEvent event) {
    }

    private void openSelectedNote() {
        if (currentSelectedNote != null) {
            manager.storeObjectToPush(currentSelectedNote, DiaryScreenController.class);
            manager.changeScreen(ScreensFramework.NOTE_SCREEN);
        }
    }
}
