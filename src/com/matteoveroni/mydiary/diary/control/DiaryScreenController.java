package com.matteoveroni.mydiary.diary.control;

import com.matteoveroni.mydiary.application.manager.Manageable;
import com.matteoveroni.mydiary.application.manager.Manager;
import com.matteoveroni.mydiary.utilities.patterns.Listener;
import com.matteoveroni.mydiary.note.model.bean.Note;
import com.matteoveroni.mydiary.application.messages.DataObjectMessage;
import com.matteoveroni.mydiary.diary.model.DiaryModel;
import com.matteoveroni.mydiary.diary.model.bean.Diary;
import com.matteoveroni.mydiary.library.control.LibraryScreenController;
import com.matteoveroni.mydiary.screen.framework.ScreensFramework;
import com.matteoveroni.mydiary.utilities.exceptions.CriticalRuntimeException;
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
import javax.swing.JOptionPane;
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
					if (diaryTable.getSelectionModel().getSelectedItem() != null) {
						currentSelectedNote = diaryTable.getSelectionModel().getSelectedItem();
						openSelectedNote();
					}
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
	public void update(DataObjectMessage sendedData) {
		if (manager != null && manager.getLoggedInUser() != null) {
			if (sendedData != null && sendedData.getSenderClass() != null) {
				try {
					if (sendedData.getSenderClass().equals(LibraryScreenController.class)) {
						setCurrentDiary((Diary) sendedData.getData());
					}
					drawUpdatedDiaryNotesInsideNotesTable();
					btn_openNote.setDisable(true);
					btn_removeNote.setDisable(true);
				} catch (Exception ex) {
					LOG.error(" ---> Critical Runtime Exception Occurred -> " + ex.getMessage());
					throw new CriticalRuntimeException(ex, manager);
				}
			}
		}
	}

	@FXML
	void goToNoteScreen(ActionEvent event) {
		LOG.debug(" ---> goToNoteScreen FXML call");
		if (currentSelectedNote != null) {
			manager.storeObjectToPush(currentSelectedNote, DiaryScreenController.class);
			manager.changeScreen(ScreensFramework.NOTE_SCREEN);
		}
	}

	@FXML
	void removeNote(ActionEvent event) {
		LOG.debug(" ---> removeNote FXML call");
		if (currentSelectedNote != null) {
			model.removeNoteFromCurrentDiary(currentSelectedNote);
			drawUpdatedDiaryNotesInsideNotesTable();
		}
	}

	@FXML
	void createNewNote(ActionEvent event) {
		LOG.debug(" ---> createNewNote FXML call");
		String noteTitle = JOptionPane.showInputDialog(
			null,
			"Insert a title for the new note and confirm, or press cancel to abort the operation",
			"Insert the Note\'s Title",
			JOptionPane.QUESTION_MESSAGE
		);
		if (noteTitle != null) {
			Note newNote = new Note();
			newNote.setTitle(noteTitle);
			newNote.setCreationDate(new Date());
			newNote.setLastModificationTimestamp(new Date());
			newNote.setDiary(currentDiary);
			if (model.saveNoteIntoCurrentDiary(newNote) == true) {
				manager.storeObjectToPush(newNote, DiaryScreenController.class);
				manager.changeScreen(ScreensFramework.NOTE_SCREEN);
			}
		}
	}

	@FXML
	void goToFilterScreen(ActionEvent event) {
	}

	@FXML
	void enableFilter(ActionEvent event) {
	}

	private void openSelectedNote() {
		LOG.debug(" ---> openSelectedNote call");
		if (currentSelectedNote != null) {
			manager.storeObjectToPush(currentSelectedNote, DiaryScreenController.class);
			manager.changeScreen(ScreensFramework.NOTE_SCREEN);
		}
	}

	private void setCurrentDiary(Diary diary) {
		currentDiary = diary;
		model.setDiary(currentDiary);
		currentSelectedNote = null;
	}

	private void drawUpdatedDiaryNotesInsideNotesTable() {
		LOG.debug(" ---> drawUpdatedDiaryNotesInsideNotesTable call");
		if (currentDiary != null) {
			List<Note> notesFromDiary = model.getNotesFromCurrentDiary();
			if (notesFromDiary != null && notesFromDiary.size() > 0) {
				LOG.debug(" ---> There are notes in this diary. Populating the notes_table with retrieved notes");
				ObservableList<Note> notesForTheTable = FXCollections.observableArrayList(notesFromDiary);
				diaryTable.setItems(notesForTheTable);
			} else {
				LOG.debug(" ---> There are\'t any notes in this diary. Clearing the notes_table");
				diaryTable.getItems().clear();
			}
		}
	}
}
