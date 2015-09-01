package com.matteoveroni.mydiary.article.control;

import com.matteoveroni.mydiary.article.model.hibernate.PersistentHibernateArticle;
import com.matteoveroni.mydiary.screen.ManageableScreen;
import com.matteoveroni.mydiary.Observer;
import com.matteoveroni.mydiary.article.model.Article;
import com.matteoveroni.mydiary.article.model.ArticleModel;
import com.matteoveroni.mydiary.article.model.hibernate.HibernateArticleModel;
import com.matteoveroni.mydiary.screen.ScreenManager;
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
 * FXML Controller class
 *
 * @author Matteo Veroni
 */
public class ArticleScreenController implements Initializable, ManageableScreen, Observer {
    
    private ScreenManager screenManager;
    private ArticleModel model;
    private Article currentArticle;
    
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

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new HibernateArticleModel();
        currentArticle = new PersistentHibernateArticle();
    }
    
    @Override
    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
        screenManager.registerObserver(this);
    }
    
    @Override
    public void update() {
        try {
            currentArticle = model.getLastArticle();
            drawCurrentModelOnTheScene();
        } catch (Exception exNoArticlesReaded) {
            try {
                Article articleToCreate = new PersistentHibernateArticle();
                articleToCreate.setTitle("Title");
                articleToCreate.setDate(new Date());
                currentArticle = model.createNewArticle(articleToCreate);
            } catch (Exception exInCreationOfANewArticle) {
                throw new RuntimeException(exInCreationOfANewArticle);
            }
        }
    }
    
    @FXML
    void articleMessageChanged(ActionEvent event) {
    }
    
    @FXML
    void saveArticleButtonPressed(ActionEvent event) {
        currentArticle.setTitle(articleTitle_txt.getText());
        currentArticle.setMessage(articleMessage_htmlEditor.getHtmlText());
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
        screenManager.useScreen(ScreenType.DIARY_SCREEN);
    }
    
    private void drawCurrentModelOnTheScene() {
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
