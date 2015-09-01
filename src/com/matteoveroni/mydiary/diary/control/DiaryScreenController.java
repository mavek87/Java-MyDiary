package com.matteoveroni.mydiary.diary.control;

import com.matteoveroni.mydiary.article.model.Article;
import com.matteoveroni.mydiary.database.DAO;
import com.matteoveroni.mydiary.screen.ManageableScreen;
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
public class DiaryScreenController implements Initializable, ManageableScreen {

    private ScreenManager myScreenManager;
    private final DAO databaseManager = DAO.getInstance();

    @FXML
    private TableView<Article> diaryTable;

    @FXML
    private TableColumn<Article, Long> tableColumn_Id;

    @FXML
    private TableColumn<Article, String> tableColumn_Title;

    @FXML
    private TableColumn<Article, Date> tableColumn_Date;

    @FXML
    private TableColumn<Article, Date> tableColumn_Time;

    @FXML
    private TableColumn<Article, String> tableColumn_Author;

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

    @FXML
    void goToArticleScreen(ActionEvent event) {
        myScreenManager.useScreen(ScreenType.ARTICLE_SCREEN);
    }

    @FXML
    void goToFilterScreen(ActionEvent event) {

    }

    @FXML
    void removeNotes(ActionEvent event) {

    }

    @FXML
    void createNewNote(ActionEvent event) {
        Article newArticle = new Article();
        newArticle.setTitle("New Title");
        newArticle.setDate(new Date());
        databaseManager.write(newArticle);
        myScreenManager.useScreen(ScreenType.ARTICLE_SCREEN);
    }

    @FXML
    void enableFilter(ActionEvent event) {

    }

    /**
     * Initializes the controller class.
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
        drawAllTheArticlesInTheDiaryTable();
    }

    private void drawAllTheArticlesInTheDiaryTable() {
        List<Article> articlesFromDatabase = databaseManager.readAll(Article.class);

        ObservableList<Article> articles = FXCollections.observableArrayList(articlesFromDatabase);

        tableColumn_Id.setCellValueFactory(new PropertyValueFactory<Article, Long>("id"));
        tableColumn_Title.setCellValueFactory(new PropertyValueFactory<Article, String>("title"));
        tableColumn_Date.setCellValueFactory(new PropertyValueFactory<Article, Date>("date"));
        tableColumn_Author.setCellValueFactory(new PropertyValueFactory<Article, String>("author"));

        diaryTable.setItems(articles);
    }
}
