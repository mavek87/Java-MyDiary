package com.matteoveroni.mydiary.article.control;

import com.matteoveroni.mydiary.application.manager.Manageable;
import com.matteoveroni.mydiary.application.manager.Manager;
import com.matteoveroni.mydiary.article.model.hibernate.PersistentHibernateArticle;
import com.matteoveroni.mydiary.patterns.Listener;
import com.matteoveroni.mydiary.article.model.Article;
import com.matteoveroni.mydiary.article.model.ArticleModel;
import com.matteoveroni.mydiary.article.model.hibernate.HibernateArticleModel;
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
 * Article Screen Controller class
 *
 * @author Matteo Veroni
 */
public class ArticleScreenController implements Initializable, Manageable, Listener {

	private Manager manager;
	private final ArticleModel model = new HibernateArticleModel();
	private Article currentArticle = new PersistentHibernateArticle();

	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private TextField txt_articleTitle;
	@FXML
	private TextField txt_articleAuthor;
	@FXML
	private TextField txt_articleData;
	@FXML
	private TextField txt_articleLastModification;
	@FXML
	private HTMLEditor htmlEditor_articleMessage;
	@FXML
	private Button btn_previousArticle;
	@FXML
	private Button btn_nextArticle;
	@FXML
	private Button btn_backArticle;
	@FXML
	private Button btn_saveArticle;

	@FXML
	private TextField txt_articleNumber;

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
		currentArticle.setTitle(txt_articleTitle.getText());
		currentArticle.setMessage(htmlEditor_articleMessage.getHtmlText());
		currentArticle.setAuthor(manager.getLoggedInUser().getName());
		System.out.println(manager.getLoggedInUser().toString());
		model.saveCurrentArticle(currentArticle);
	}

	@FXML
	void previousArticleButtonPressed(ActionEvent event) {
		Article newArticleReaded = model.getPreviousArticle(currentArticle);
		if (newArticleReaded != null) {
			currentArticle = newArticleReaded;
			drawCurrentModelOnTheScene();
		}
	}

	@FXML
	void nextArticleButtonPressed(ActionEvent event) {
		Article newArticleReaded = model.getNextArticle(currentArticle);
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
		txt_articleNumber.setText(Objects.toString(currentArticle.getId(), null));
		txt_articleTitle.setText(currentArticle.getTitle());
		htmlEditor_articleMessage.setHtmlText(currentArticle.getMessage());
		txt_articleAuthor.setText(currentArticle.getAuthor());
		if (currentArticle.getCreationDate() != null) {
			txt_articleData.setText(currentArticle.getCreationDate().toString());
		}
	}

	private void resetCurrentSceneElements() {
		txt_articleNumber.setText("");
		txt_articleTitle.setText("");
		htmlEditor_articleMessage.setHtmlText("");
		txt_articleAuthor.setText("");
		txt_articleData.setText("");
	}

	private void createFirstDefaultArticle() {
		Article articleToCreate = new PersistentHibernateArticle();
		articleToCreate.setTitle("Title");
		articleToCreate.setAuthor(manager.getLoggedInUser().toString());
		articleToCreate.setMessage("");
		articleToCreate.setCreationDate(new Date());
		articleToCreate.setLastModificationTimestamp(new Date());
		currentArticle = model.createNewArticle(articleToCreate);
	}
}
