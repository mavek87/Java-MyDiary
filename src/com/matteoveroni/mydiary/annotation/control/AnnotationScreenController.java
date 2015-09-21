package com.matteoveroni.mydiary.annotation.control;

import com.matteoveroni.mydiary.application.manager.Manageable;
import com.matteoveroni.mydiary.application.manager.Manager;
import com.matteoveroni.mydiary.utilities.patterns.Listener;
import com.matteoveroni.mydiary.annotation.model.bean.Annotation;
import com.matteoveroni.mydiary.annotation.model.AnnotationModel;
import com.matteoveroni.mydiary.application.manager.DataObjectMessage;
import com.matteoveroni.mydiary.diary.control.DiaryScreenController;
import com.matteoveroni.mydiary.exceptions.CriticalRuntimeException;
import com.matteoveroni.mydiary.utilities.date.formatter.DateFormatter;
import com.matteoveroni.mydiary.screen.ScreensFramework;
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
public class AnnotationScreenController implements Initializable, Manageable, Listener {

	private Manager manager;
	private final AnnotationModel model = new AnnotationModel();
	private Annotation currentAnnotation = new Annotation();

	private static final Logger LOG = LoggerFactory.getLogger(AnnotationScreenController.class);

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
	private HTMLEditor htmlEditor_message;
	@FXML
	private Button btn_previousAnnotation;
	@FXML
	private Button btn_nextAnnotation;
	@FXML
	private Button btn_back;
	@FXML
	private Button btn_saveAnnotation;
	@FXML
	private TextField txt_annotationNumber;

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
					System.out.println("pushed data " + pushedData.getSenderClass().toString());
					Annotation annotationSended = (Annotation) pushedData.getData();
					currentAnnotation = model.getAnnotation(annotationSended.getId());
					drawCurrentModelOnTheScene();
				}
			} catch (Exception ex) {
				LOG.error("Critical Runtime Exception Occurred -> " + ex.getMessage());
				throw new CriticalRuntimeException(ex, manager);
			}
		}
	}

	@FXML
	void annotationMessageChanged(ActionEvent event) {
	}

	@FXML
	void saveAnnotationButtonPressed(ActionEvent event) {
		currentAnnotation.setTitle(txt_title.getText());
		currentAnnotation.setMessage(htmlEditor_message.getHtmlText());
//        currentAnnotation.setAuthor(manager.getLoggedInUser().toString());
		currentAnnotation.setLastModificationTimestamp(new Date());
		model.updateAnnotation(currentAnnotation);
		drawCurrentModelOnTheScene();
	}

	@FXML
	void previousAnnotationButtonPressed(ActionEvent event) {
		Annotation newAnnotationReaded = model.getPreviousAnnotation(currentAnnotation);
		if (newAnnotationReaded != null) {
			currentAnnotation = newAnnotationReaded;
			drawCurrentModelOnTheScene();
		} else if (model.getLastAnnotation() != null) {
			currentAnnotation = model.getLastAnnotation();
			drawCurrentModelOnTheScene();
		}
	}

	@FXML
	void nextAnnotationButtonPressed(ActionEvent event) {
		Annotation newAnnotationReaded = model.getNextAnnotation(currentAnnotation);
		if (newAnnotationReaded != null) {
			currentAnnotation = newAnnotationReaded;
			drawCurrentModelOnTheScene();
		} else if (model.getFirstAnnotation() != null) {
			currentAnnotation = model.getFirstAnnotation();
			drawCurrentModelOnTheScene();
		}
	}

	@FXML
	void backButtonPressed(ActionEvent event) {
		manager.changeScreen(ScreensFramework.DIARY_SCREEN);
	}

	private void drawCurrentModelOnTheScene() {
		resetCurrentSceneElements();
		txt_annotationNumber.setText(Objects.toString(currentAnnotation.getId(), null));
		if (currentAnnotation.getTitle() != null) {
			txt_title.setText(currentAnnotation.getTitle());
		}
		if (currentAnnotation.getMessage() != null) {
			htmlEditor_message.setHtmlText(currentAnnotation.getMessage());
		}
		if (currentAnnotation.getCreationDate() != null) {
			txt_creationDate.setText(DateFormatter.dateFormat(currentAnnotation.getCreationDate()));
		}
		if (currentAnnotation.getLastModificationTimestamp() != null) {
			txt_lastModificationDate.setText(DateFormatter.timestampFormat(currentAnnotation.getLastModificationTimestamp()));
		}
	}

	private void resetCurrentSceneElements() {
		txt_annotationNumber.setText("");
		txt_title.setText("");
		htmlEditor_message.setHtmlText("");
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
