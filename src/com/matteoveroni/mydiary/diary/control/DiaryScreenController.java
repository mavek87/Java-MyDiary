package com.matteoveroni.mydiary.diary.control;

import com.matteoveroni.mydiary.application.manager.Manageable;
import com.matteoveroni.mydiary.application.manager.Manager;
import com.matteoveroni.mydiary.utilities.patterns.Listener;
import com.matteoveroni.mydiary.note.model.bean.Note;
import com.matteoveroni.mydiary.application.messages.DataObjectMessage;
import com.matteoveroni.mydiary.diary.model.DiaryModel;
import com.matteoveroni.mydiary.diary.model.bean.Diary;
import com.matteoveroni.mydiary.library.control.LibraryScreenController;
import com.matteoveroni.mydiary.menu.model.commands.MenuAboutCommand;
import com.matteoveroni.mydiary.menu.model.commands.MenuCloseCommand;
import com.matteoveroni.mydiary.screen.framework.ScreensFramework;
import com.matteoveroni.mydiary.utilities.patterns.Command;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
	private ResourceBundle resourceBundle;
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
	private Button btn_openNote;
	@FXML
	private Button btn_createNewNote;
	@FXML
	private CheckBox chk_enableFilter;
	@FXML
	private Button btn_removeNote;
	@FXML
	private Label lbl_diaryOwnerTitle;
	@FXML
	private Label lbl_diaryOwnerValue;
	@FXML
	private Label lbl_numberOfNotesTitle;
	@FXML
	private Label lbl_numberOfNotes;
	@FXML
	private MenuBar menu;
	@FXML
	private Menu menu_file;
	@FXML
	private MenuItem menu_settings;
	@FXML
	private MenuItem menu_logout;
	@FXML
	private MenuItem menu_close;
	@FXML
	private Menu menu_help;
	@FXML
	private MenuItem menu_about;

	/**
	 * Initializes the DiaryScreenController class.
	 *
	 * @param url
	 * @param resourceBundle
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
		diaryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		tableColumn_Id.setCellValueFactory(new PropertyValueFactory<Note, Long>("id"));
		tableColumn_Title.setCellValueFactory(new PropertyValueFactory<Note, String>("title"));
		tableColumn_CreationDate.setCellValueFactory(new PropertyValueFactory<Note, Date>("creationDate"));
		tableColumn_LastModificationTimestamp.setCellValueFactory(new PropertyValueFactory<Note, Date>("lastModificationTimestamp"));

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
				if (sendedData.getSenderClass().equals(LibraryScreenController.class)) {
					setCurrentDiary((Diary) sendedData.getData());
				}
				drawUpdatedDiaryNotesInsideNotesTable();
				btn_openNote.setDisable(true);
				btn_removeNote.setDisable(true);
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
			resourceBundle.getString("insertATitleForTheNoteMessage"),
			resourceBundle.getString("insertATitleForTheNote"),
			JOptionPane.QUESTION_MESSAGE
		);
		if (noteTitle != null) {
			Note newNote = new Note();
			newNote.setTitle(noteTitle);
			newNote.setCreationDate(new Date());
			newNote.setLastModificationTimestamp(new Date());
			newNote.setDiary(currentDiary);
			if (model.saveNoteIntoCurrentDiary(newNote) == true) {
				LOG.info("NEW NOTE CREATED!");
				LOG.info("Note id: " + newNote.getId());
				LOG.info("Note title: " + newNote.getTitle());
				LOG.info("Note message: " + newNote.getMessage());
				LOG.info("Note creation date: " + newNote.getCreationDate());
				manager.storeObjectToPush(newNote, DiaryScreenController.class);
				manager.changeScreen(ScreensFramework.NOTE_SCREEN);
			}
		}
	}

	@FXML
	void menuSettingsClicked(ActionEvent event) {
		manager.changeScreen(ScreensFramework.SETTINGS_SCREEN);
	}

	@FXML
	void menuLogoutClicked(ActionEvent event) {
		resetDiaryScreenInputFields();
		manager.setLoggedInUser(null);
		manager.changeScreen(ScreensFramework.LOGIN_SCREEN);
	}

	@FXML
	void menuCloseClicked(ActionEvent event) {
		Command closeCommand = new MenuCloseCommand(manager);
		closeCommand.execute();
	}

	@FXML
	void menuAboutClicked(ActionEvent event) {
		Command aboutCommand = new MenuAboutCommand(manager, resourceBundle);
		aboutCommand.execute();
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
			lbl_diaryOwnerValue.setText(model.getDiaryOwnerUsername(currentDiary));
			lbl_numberOfNotes.setText(String.valueOf(model.getNumberOfNotesOfADiary(currentDiary)));
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

	private void resetDiaryScreenInputFields() {
		lbl_diaryOwnerValue.setText("");
		lbl_numberOfNotes.setText("");
		clearDiaryTable();
		btn_removeNote.setDisable(true);
		btn_openNote.setDisable(true);

//		cmb_chooseDiary.getItems().removeAll(cmb_chooseDiary.getItems());
//		txt_newDiaryName.setText("");
//		tabPane.getSelectionModel().select(tab_selectDiary);
	}

	private void clearDiaryTable() {
		final List<Note> items = diaryTable.getItems();
		if (items == null || items.isEmpty()) {
			return;
		}

		final Note item = diaryTable.getItems().get(0);
		items.remove(0);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				items.add(0, item);
			}
		});
	}
}
