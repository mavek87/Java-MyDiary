package com.matteoveroni.mydiary.diary.control;

import com.matteoveroni.mydiary.database.DatabaseManager;
import com.matteoveroni.mydiary.screen.Manageable;
import com.matteoveroni.mydiary.screen.ScreenManager;
import com.matteoveroni.mydiary.screen.ScreenType;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Matteo Veroni
 */
public class DiaryScreenController implements Initializable, Manageable{

	private ScreenManager myScreenManager;
	private final DatabaseManager databaseManager = DatabaseManager.getInstance() ;

	@FXML
	private Button btn_button;

	@FXML
	void goToArticleScreen(ActionEvent event) {
		myScreenManager.useScreen(ScreenType.ARTICLE_SCREEN);
	}

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

	@Override
	public void setScreenManager(ScreenManager screenManager) {
		myScreenManager = screenManager;
	}
}
