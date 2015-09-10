package com.matteoveroni.mydiary.annotation.control;

import com.matteoveroni.mydiary.application.manager.Manageable;
import com.matteoveroni.mydiary.application.manager.Manager;
import com.matteoveroni.mydiary.annotation.model.hibernate.PersistentHibernateAnnotation;
import com.matteoveroni.mydiary.patterns.Listener;
import com.matteoveroni.mydiary.annotation.model.Annotation;
import com.matteoveroni.mydiary.annotation.model.AnnotationModel;
import com.matteoveroni.mydiary.annotation.model.hibernate.HibernateAnnotationModel;
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
	private Annotation currentArticle = new PersistentHibernateAnnotation();

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
			currentArticle = model.getLastArticle();
			if (currentArticle == null) {
				createFirstDefaultArticle();
			}
			drawCurrentModelOnTheScene();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@FXML
	void articleMessageChanged(ActionEvent event) {
	}

	@FXML
	void saveArticleButtonPressed(ActionEvent event) {
		currentArticle.setTitle(txt_title.getText());
		currentArticle.setMessage(htmlEditor_message.getHtmlText());
		currentArticle.setAuthor(manager.getLoggedInUser().getName());
		System.out.println(manager.getLoggedInUser().toString());
		model.saveCurrentArticle(currentArticle);
	}

	@FXML
	void previousArticleButtonPressed(ActionEvent event) {
		Annotation newArticleReaded = model.getPreviousArticle(currentArticle);
		if (newArticleReaded != null) {
			currentArticle = newArticleReaded;
			drawCurrentModelOnTheScene();
		}
	}

	@FXML
	void nextArticleButtonPressed(ActionEvent event) {
		Annotation newArticleReaded = model.getNextArticle(currentArticle);
		if (newArticleReaded != null) {
			currentArticle = newArticleReaded;
			drawCurrentModelOnTheScene();
		}
	}

	@FXML
	void backButtonPressed(ActionEvent event) {
		manager.changeScreen(ScreenType.DIARY_SCREEN);
	}

	private void drawCurrentModelOnTheScene() {
		resetCurrentSceneElements();
		txt_annotationNumber.setText(Objects.toString(currentArticle.getId(), null));
		txt_title.setText(currentArticle.getTitle());
		htmlEditor_message.setHtmlText(currentArticle.getMessage());
		txt_author.setText(currentArticle.getAuthor());
		if (currentArticle.getCreationDate() != null) {
			txt_creationDate.setText(currentArticle.getCreationDate().toString());
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
		Annotation articleToCreate = new PersistentHibernateAnnotation();
		articleToCreate.setTitle("Title");
		articleToCreate.setAuthor(manager.getLoggedInUser().toString());
		articleToCreate.setMessage("");
		articleToCreate.setCreationDate(new Date());
		articleToCreate.setLastModificationTimestamp(new Date());
		currentArticle = model.createNewArticle(articleToCreate);
	}
}
