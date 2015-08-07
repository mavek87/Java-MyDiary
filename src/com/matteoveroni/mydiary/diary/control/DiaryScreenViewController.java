package com.matteoveroni.mydiary.diary.control;

import com.matteoveroni.mydiary.screen.ManageableScreen;
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
public class DiaryScreenViewController implements Initializable, ManageableScreen {

	private ScreenManager myScreenManager;

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
