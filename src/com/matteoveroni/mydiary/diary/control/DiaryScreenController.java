package com.matteoveroni.mydiary.diary.control;

import com.matteoveroni.mydiary.article.model.hibernate.PersistentHibernateArticle;
import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.screen.ManageableScreen;
import com.matteoveroni.mydiary.Observer;
import com.matteoveroni.mydiary.screen.ScreenManager;
import com.matteoveroni.mydiary.screen.ScreenType;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Matteo Veroni
 */
public class DiaryScreenController implements Initializable, ManageableScreen, Observer {

	private ScreenManager screenManager;
	private final DAO databaseManager = DAO.getInstance();

	@FXML
	private TableView<PersistentHibernateArticle> diaryTable;
	@FXML
	private TableColumn<PersistentHibernateArticle, Long> tableColumn_Id;
	@FXML
	private TableColumn<PersistentHibernateArticle, String> tableColumn_Title;
	@FXML
	private TableColumn<PersistentHibernateArticle, Date> tableColumn_Date;
	@FXML
	private TableColumn<PersistentHibernateArticle, Date> tableColumn_Time;
	@FXML
	private TableColumn<PersistentHibernateArticle, String> tableColumn_Author;
	@FXML
	private Button btn_filter;
	@FXML
	private Button btn_button;
	@FXML
	private Button btn_createNewNote;
	@FXML
	private CheckBox chk_enableFilter;
	@FXML
	private Button btn_removeNotes;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

	@Override
	public void setScreenManager(ScreenManager screenManager) {
		this.screenManager = screenManager;
		screenManager.registerObserver(this);
	}

	@Override
	public void update() {
		drawAllTheArticlesInTheDiaryTable();
	}

	@FXML
	void goToArticleScreen(ActionEvent event) {
		screenManager.useScreen(ScreenType.ARTICLE_SCREEN);
	}

	@FXML
	void goToFilterScreen(ActionEvent event) {

	}

	@FXML
	void removeNotes(ActionEvent event) {

	}

	@FXML
	void createNewNote(ActionEvent event) {
		PersistentHibernateArticle newArticle = new PersistentHibernateArticle();
		newArticle.setTitle("New Title");
		newArticle.setDate(new Date());
		databaseManager.write(newArticle);
		screenManager.useScreen(ScreenType.ARTICLE_SCREEN);
	}

	@FXML
	void enableFilter(ActionEvent event) {

	}

	private void drawAllTheArticlesInTheDiaryTable() {
		List<PersistentHibernateArticle> articlesFromDatabase = databaseManager.readAll(PersistentHibernateArticle.class);

		ObservableList<PersistentHibernateArticle> articles = FXCollections.observableArrayList(articlesFromDatabase);

		tableColumn_Id.setCellValueFactory(new PropertyValueFactory<PersistentHibernateArticle, Long>("id"));
		tableColumn_Title.setCellValueFactory(new PropertyValueFactory<PersistentHibernateArticle, String>("title"));
		tableColumn_Date.setCellValueFactory(new PropertyValueFactory<PersistentHibernateArticle, Date>("date"));
		tableColumn_Author.setCellValueFactory(new PropertyValueFactory<PersistentHibernateArticle, String>("author"));

		diaryTable.setItems(articles);
	}
}
