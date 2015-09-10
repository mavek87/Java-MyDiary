package com.matteoveroni.mydiary.annotation.control;

import com.matteoveroni.mydiary.application.manager.Manageable;
import com.matteoveroni.mydiary.application.manager.Manager;
import com.matteoveroni.mydiary.annotation.model.bean.HibernateAnnotationBean;
import com.matteoveroni.mydiary.patterns.Listener;
import com.matteoveroni.mydiary.annotation.model.bean.Annotation;
import com.matteoveroni.mydiary.annotation.model.AnnotationModel;
import com.matteoveroni.mydiary.annotation.model.HibernateAnnotationModel;
import com.matteoveroni.mydiary.screen.ScreenType;
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

/**
 * Annotation Screen Controller class
 *
 * @author Matteo Veroni
 */
public class AnnotationScreenController implements Initializable, Manageable, Listener {

	private Manager manager;
	private final AnnotationModel model = new HibernateAnnotationModel();
	private Annotation currentAnnotation = new HibernateAnnotationBean();

	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private TextField txt_title;
	@FXML
	private TextField txt_author;
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
				createFirstDefaultArticle();
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
		currentAnnotation.setAuthor(manager.getLoggedInUser().getName());
		System.out.println(manager.getLoggedInUser().toString());
		model.saveCurrentAnnotation(currentAnnotation);
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
		manager.changeScreen(ScreenType.DIARY_SCREEN);
	}

	private void drawCurrentModelOnTheScene() {
		resetCurrentSceneElements();
		txt_annotationNumber.setText(Objects.toString(currentAnnotation.getId(), null));
		txt_title.setText(currentAnnotation.getTitle());
		htmlEditor_message.setHtmlText(currentAnnotation.getMessage());
		txt_author.setText(currentAnnotation.getAuthor());
		if (currentAnnotation.getCreationDate() != null) {
			txt_creationDate.setText(currentAnnotation.getCreationDate().toString());
		}
		if (currentAnnotation.getLastModifationTimestamp() != null) {
			txt_lastModificationDate.setText(currentAnnotation.getLastModifationTimestamp().toString());
		}
	}

	private void resetCurrentSceneElements() {
		txt_annotationNumber.setText("");
		txt_title.setText("");
		htmlEditor_message.setHtmlText("");
		txt_author.setText("");
		txt_creationDate.setText("");
	}

	private void createFirstDefaultArticle() {
		Annotation articleToCreate = new HibernateAnnotationBean();
		articleToCreate.setTitle("Title");
		articleToCreate.setAuthor(manager.getLoggedInUser().toString());
		articleToCreate.setMessage("");
		articleToCreate.setCreationDate(new Date());
		articleToCreate.setLastModificationTimestamp(new Date());
		currentAnnotation = model.createNewAnnotation(articleToCreate);
	}
}
