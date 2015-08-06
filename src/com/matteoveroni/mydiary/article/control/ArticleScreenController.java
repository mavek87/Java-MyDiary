package com.matteoveroni.mydiary.article.control;

import com.matteoveroni.mydiary.article.model.Article;
import com.matteoveroni.mydiary.article.model.ArticleModel;
import com.matteoveroni.mydiary.model.database.DatabaseManager;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Matteo Veroni
 */
public class ArticleScreenController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField articleData_txt;

    @FXML
    private Button cancelArticle_btn;

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
    private ArticleModel model;
    private final DatabaseManager db = new DatabaseManager();
    private Stage stage;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Carico il primo articolo del database (id = 1)
        currentArticle = (Article) db.read(Article.class, 1L);

        if (currentArticle != null) {
            drawCurrentArticleOnScene();
        }

    }

    @FXML
    void articleMessageChanged(ActionEvent event) {
        System.out.println("articolo messaggio cambiato!");
    }

    @FXML
    void saveArticlePressed(ActionEvent event) {

        // Devo ottenere tutti i dati del messaggio attuali temporanei e salvarli nel db
        currentArticle.setTitle(articleTitle_txt.getText());
        currentArticle.setMessage(articleMessage_htmlEditor.getHtmlText());
        System.out.println(articleMessage_htmlEditor.getHtmlText());

        db.update(currentArticle);

    }

    @FXML
    void previousArticlePressed(ActionEvent event) {
        Article newArticleReaded = (Article) db.read(Article.class, currentArticle.getId() - 1);
        if (newArticleReaded != null) {
            currentArticle = newArticleReaded;
            drawCurrentArticleOnScene();
        }
    }

    @FXML
    void nextArticlePressed(ActionEvent event) {
        Article newArticleReaded = (Article) db.read(Article.class, currentArticle.getId() + 1);
        if (newArticleReaded != null) {
            currentArticle = newArticleReaded;
            drawCurrentArticleOnScene();
        }
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
    }

    private void drawCurrentArticleOnScene() {
        articleNumber_txt.setText(Objects.toString(currentArticle.getId(), null));
        articleTitle_txt.setText(currentArticle.getTitle());
        articleMessage_htmlEditor.setHtmlText(currentArticle.getMessage());
        //articleAuthor_txt.setText(currentArticle.getAuthor);
        //articleData_txt = currentArticle.getData();
    }
}
