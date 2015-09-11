package com.matteoveroni.mydiary.annotation.control;

import com.matteoveroni.mydiary.application.manager.Manageable;
import com.matteoveroni.mydiary.application.manager.Manager;
import com.matteoveroni.mydiary.annotation.model.bean.HibernateAnnotationBean;
import com.matteoveroni.mydiary.patterns.Listener;
import com.matteoveroni.mydiary.annotation.model.bean.Annotation;
import com.matteoveroni.mydiary.annotation.model.AnnotationModel;
import com.matteoveroni.mydiary.annotation.model.HibernateAnnotationModel;
import com.matteoveroni.mydiary.screen.ScreensFramework;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;

/**
 * Annotation Screen Controller class
 *
 * @author Matteo Veroni
 */
public class AnnotationScreenController implements Initializable, Manageable, Listener {

	private Manager manager;
	private final AnnotationModel model = new HibernateAnnotationModel();
	private Annotation currentAnnotation = new HibernateAnnotationBean();
	private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private final DateFormat timestampFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

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
	public void update() {
		try {
			currentAnnotation = model.getLastAnnotation();
			if (currentAnnotation == null) {
				createFirstDefaultAnnotation();
			}
			drawCurrentModelOnTheScene();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@FXML
	void annotationMessageChanged(ActionEvent event) {
	}

	@FXML
	void saveAnnotationButtonPressed(ActionEvent event) {
		currentAnnotation.setTitle(txt_title.getText());
		currentAnnotation.setMessage(htmlEditor_message.getHtmlText());
		currentAnnotation.setAuthor(manager.getLoggedInUser().toString());
		currentAnnotation.setLastModificationTimestamp(new Date());
		model.saveCurrentAnnotation(currentAnnotation);
		drawCurrentModelOnTheScene();
	}

	@FXML
	void previousAnnotationButtonPressed(ActionEvent event) {
		Annotation newAnnotationReaded = model.getPreviousAnnotation(currentAnnotation);
		if (newAnnotationReaded != null) {
			currentAnnotation = newAnnotationReaded;
			drawCurrentModelOnTheScene();
		}
	}

	@FXML
	void nextAnnotationButtonPressed(ActionEvent event) {
		Annotation newAnnotationReaded = model.getNextAnnotation(currentAnnotation);
		if (newAnnotationReaded != null) {
			currentAnnotation = newAnnotationReaded;
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
			txt_creationDate.setText(dateFormat.format(currentAnnotation.getCreationDate()));
		}
		if (currentAnnotation.getLastModificationTimestamp() != null) {
			txt_lastModificationDate.setText(timestampFormat.format(currentAnnotation.getLastModificationTimestamp()));
		}
	}

	private void resetCurrentSceneElements() {
		txt_annotationNumber.setText("");
		txt_title.setText("");
		htmlEditor_message.setHtmlText("");
		txt_creationDate.setText("");
		txt_lastModificationDate.setText("");
	}

	private void createFirstDefaultAnnotation() {
		Annotation annotationToCreate = new HibernateAnnotationBean();
		annotationToCreate.setTitle("Title");
		annotationToCreate.setAuthor(manager.getLoggedInUser().toString());
		annotationToCreate.setMessage("");
		annotationToCreate.setCreationDate(new Date());
		annotationToCreate.setLastModificationTimestamp(new Date());
		currentAnnotation = model.saveNewAnnotation(annotationToCreate);
	}
}
