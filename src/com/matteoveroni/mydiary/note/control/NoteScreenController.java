package com.matteoveroni.mydiary.note.control;

import com.matteoveroni.mydiary.application.manager.Manageable;
import com.matteoveroni.mydiary.application.manager.Manager;
import com.matteoveroni.mydiary.utilities.patterns.Listener;
import com.matteoveroni.mydiary.note.model.bean.Note;
import com.matteoveroni.mydiary.note.model.NoteModel;
import com.matteoveroni.mydiary.application.messages.DataObjectMessage;
import com.matteoveroni.mydiary.diary.control.DiaryScreenController;
import com.matteoveroni.mydiary.exceptions.CriticalRuntimeException;
import com.matteoveroni.mydiary.utilities.formatters.dates.DateFormatter;
import com.matteoveroni.mydiary.screen.framework.ScreensFramework;
import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Annotation Screen Controller class
 *
 * @author Matteo Veroni
 */
public class NoteScreenController implements Initializable, Manageable, Listener {

	private Manager manager;
	private final NoteModel model = new NoteModel();
	private Note currentNote = new Note();

	private static final Logger LOG = LoggerFactory.getLogger(NoteScreenController.class);

	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private TextField txt_title;
	@FXML
	private TextField txt_creationDate;
	@FXML
	private TextField txt_lastModificationDate;
	@FXML
	private HTMLEditor htmlEditor_noteMessage;
	@FXML
	private Button btn_previousNote;
	@FXML
	private Button btn_nextNote;
	@FXML
	private Button btn_back;
	@FXML
	private Button btn_saveNote;
	@FXML
	private TextField txt_noteNumber;

	/**
	 * Initializes the controller class.
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
			try {
				if (pushedData != null && pushedData.getSenderClass().equals(DiaryScreenController.class)) {
					LOG.debug("---> pushed data " + pushedData.getSenderClass().toString());
					Note sendedNote = (Note) pushedData.getData();
					currentNote = model.getNote(sendedNote.getId());
					drawCurrentModelOnTheScene();
				}
			} catch (Exception ex) {
				LOG.error("Critical Runtime Exception Occurred -> " + ex.getMessage());
				throw new CriticalRuntimeException(ex, manager);
			}
		}
	}

	@FXML
	void noteMessageChanged(ActionEvent event) {
	}

	@FXML
	void saveNoteButtonPressed(ActionEvent event) {
		currentNote.setTitle(txt_title.getText());
		currentNote.setMessage(htmlEditor_noteMessage.getHtmlText());
//        currentAnnotation.setAuthor(manager.getLoggedInUser().toString());
		currentNote.setLastModificationTimestamp(new Date());
		model.updateNote(currentNote);
		drawCurrentModelOnTheScene();
	}

	@FXML
	void previousNoteButtonPressed(ActionEvent event) {
		Note newNoteReaded = model.getPreviousNote(currentNote);
		if (newNoteReaded != null) {
			currentNote = newNoteReaded;
			drawCurrentModelOnTheScene();
		} else if (model.getLastNote() != null) {
			currentNote = model.getLastNote();
			drawCurrentModelOnTheScene();
		}
	}

	@FXML
	void nextNoteButtonPressed(ActionEvent event) {
		Note newNoteReaded = model.getNextNote(currentNote);
		if (newNoteReaded != null) {
			currentNote = newNoteReaded;
			drawCurrentModelOnTheScene();
		} else if (model.getFirstNote() != null) {
			currentNote = model.getFirstNote();
			drawCurrentModelOnTheScene();
		}
	}

	@FXML
	void backButtonPressed(ActionEvent event) {
		manager.changeScreen(ScreensFramework.DIARY_SCREEN);
	}

	private void drawCurrentModelOnTheScene() {
		resetCurrentSceneElements();
		txt_noteNumber.setText(Objects.toString(currentNote.getId(), null));
		if (currentNote.getTitle() != null) {
			txt_title.setText(currentNote.getTitle());
		}
		if (currentNote.getMessage() != null) {
			htmlEditor_noteMessage.setHtmlText(currentNote.getMessage());
		}
		if (currentNote.getCreationDate() != null) {
			txt_creationDate.setText(DateFormatter.dateFormat(currentNote.getCreationDate()));
		}
		if (currentNote.getLastModificationTimestamp() != null) {
			txt_lastModificationDate.setText(DateFormatter.timestampFormat(currentNote.getLastModificationTimestamp()));
		}
	}

	private void resetCurrentSceneElements() {
		txt_noteNumber.setText("");
		txt_title.setText("");
		htmlEditor_noteMessage.setHtmlText("");
		txt_creationDate.setText("");
		txt_lastModificationDate.setText("");
	}

//    private void createFirstDefaultAnnotation() {
//        Annotation newAnnotation = AnnotationFactory.createAnnotation(AnnotationType.HIBERNATE);
//        newAnnotation.setTitle("Title");
//        newAnnotation.setAuthor(manager.getLoggedInUser().toString());
//        newAnnotation.setMessage("");
//        Date currentDate = new Date();
//        newAnnotation.setCreationDate(currentDate);
//        newAnnotation.setLastModificationTimestamp(currentDate);
//        model.saveAnnotation(newAnnotation);
//        currentAnnotation = newAnnotation;
//    }
}
