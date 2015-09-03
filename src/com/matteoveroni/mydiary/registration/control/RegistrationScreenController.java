package com.matteoveroni.mydiary.registration.control;

import com.matteoveroni.mydiary.Observer;
import com.matteoveroni.mydiary.screen.ManageableScreen;
import com.matteoveroni.mydiary.screen.ScreenManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;


/**
 * Registration Screen Controller class
 *
 * @author Matteo Veroni
 */
public class RegistrationScreenController implements ManageableScreen, Initializable, Observer {

	private ScreenManager screenManager;

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
	public void update() {
	}

	@Override
	public void setScreenManager(ScreenManager screenManager) {
		this.screenManager = screenManager;
	}
}
