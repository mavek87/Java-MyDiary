package com.matteoveroni.mydiary.article.control;

import com.matteoveroni.mydiary.article.model.Article;
import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.database.DAO.ElementOnWhichOperate;
import com.matteoveroni.mydiary.screen.ManageableScreen;
import com.matteoveroni.mydiary.screen.ScreenManager;
import com.matteoveroni.mydiary.screen.ScreenType;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;

/**
 * FXML Controller class
 *
 * @author Matteo Veroni
 */
public class ArticleScreenController implements Initializable, ManageableScreen {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField articleData_txt;

	@FXML
	private Button backArticle_btn;

	@FXML
	private Button previousArticle_btn;

	@FXML
	private TextField articleNumber_txt;

	@FXML
	private TextField articleAuthor_txt;

	@FXML
	private Button nextArticle_btn;

	@FXML
	private TextField articleTitle_txt;

	@FXML
	private Button saveArticle_btn;

	@FXML
	private HTMLEditor articleMessage_htmlEditor;

	private Article currentArticle;
	private ScreenManager myScreenManager;
	private final DAO databaseManager = DAO.getInstance();

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
	public void setScreenManager(ScreenManager screenManager) {
		myScreenManager = screenManager;
	}

	@Override
	public void updateScreen() {
		try {
			currentArticle = (Article) databaseManager.read(Article.class, ElementOnWhichOperate.LAST);
			drawCurrentArticleOnTheScene();
		} catch (Exception ex) {
			throw new RuntimeException();
		}
	}

	@FXML
	void articleMessageChanged(ActionEvent event) {
		System.out.println("articolo messaggio cambiato!");
	}

	@FXML
	void saveArticleButtonPressed(ActionEvent event) {

		// Devo ottenere tutti i dati del messaggio attuali temporanei e salvarli nel db
		currentArticle.setTitle(articleTitle_txt.getText());
		currentArticle.setMessage(articleMessage_htmlEditor.getHtmlText());
		System.out.println(articleMessage_htmlEditor.getHtmlText());

		databaseManager.update(currentArticle);
	}

	@FXML
	void previousArticleButtonPressed(ActionEvent event) {
		Article newArticleReaded = (Article) databaseManager.read(Article.class, currentArticle.getId() - 1);
		if (newArticleReaded != null) {
			currentArticle = newArticleReaded;
			drawCurrentArticleOnTheScene();
		}
	}

	@FXML
	void nextArticleButtonPressed(ActionEvent event) {
		Article newArticleReaded = (Article) databaseManager.read(Article.class, currentArticle.getId() + 1);
		if (newArticleReaded != null) {
			currentArticle = newArticleReaded;
			drawCurrentArticleOnTheScene();
		}
	}

	@FXML
	void backButtonPressed(ActionEvent event) {
		myScreenManager.useScreen(ScreenType.DIARY_SCREEN);
	}

	private void drawCurrentArticleOnTheScene() {
		resetCurrentSceneElements();

		articleNumber_txt.setText(Objects.toString(currentArticle.getId(), null));
		articleTitle_txt.setText(currentArticle.getTitle());
		articleMessage_htmlEditor.setHtmlText(currentArticle.getMessage());
		articleAuthor_txt.setText(currentArticle.getAuthor());

		if (currentArticle.getDate() != null) {
			articleData_txt.setText(currentArticle.getDate().toString());
		}
	}

	private void resetCurrentSceneElements() {
		articleNumber_txt.setText("");
		articleTitle_txt.setText("");
		articleMessage_htmlEditor.setHtmlText("");
		articleAuthor_txt.setText("");
		articleData_txt.setText("");
	}
}
