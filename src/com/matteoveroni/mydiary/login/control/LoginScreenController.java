package com.matteoveroni.mydiary.login.control;

import com.matteoveroni.mydiary.application.messages.DataObjectMessage;
import com.matteoveroni.mydiary.application.manager.Manageable;
import com.matteoveroni.mydiary.application.manager.Manager;
import com.matteoveroni.mydiary.utilities.patterns.Listener;
import com.matteoveroni.mydiary.login.model.LoginModel;
import com.matteoveroni.mydiary.menu.model.commands.MenuAboutCommand;
import com.matteoveroni.mydiary.menu.model.MenuModel;
import com.matteoveroni.mydiary.menu.model.commands.MenuCloseCommand;
import com.matteoveroni.mydiary.screen.framework.ScreensFramework;
import com.matteoveroni.mydiary.user.model.bean.UserData;
import com.matteoveroni.mydiary.utilities.patterns.Command;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Login Screen Controller class
 *
 * @author Matteo Veroni
 */
public class LoginScreenController implements Manageable, Initializable, Listener {

	private Manager manager;
//	private MenuModel menuModel;
	private UserData user;
	private final LoginModel model = new LoginModel();

	@FXML
	private PasswordField psw_password;
	@FXML
	private Button btn_register;
	@FXML
	private Label lbl_loginFailedMessage;
	@FXML
	private Button btn_login;
	@FXML
	private TextField txt_username;
	@FXML
	private MenuBar menu;
	@FXML
	private Menu menu_file;
	@FXML
	private MenuItem menu_settings;
	@FXML
	private MenuItem menu_close;
	@FXML
	private Menu menu_help;
	@FXML
	private MenuItem menu_about;

	/**
	 * Initializes the controller class.
	 *
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		addListenerToAllThePagesElementsThatRemoveLoginErrorMessageOnFocus();
	}

	@Override
	public void update(DataObjectMessage pushedData) {
		if (manager != null && manager.getLoggedInUser() != null) {
			resetAllTheFormElements();
		}
	}

	@Override
	public void setManager(Manager manager) {
		this.manager = manager;
		manager.registerListener(this);
//		menuModel = new MenuModel(manager);
	}

	@FXML
	void login(ActionEvent event) {
		String insertedUsername = txt_username.getText();
		String insertedPassword = psw_password.getText();
		user = model.searchUserWithUsernameAndPassword(insertedUsername, insertedPassword);
		if (user != null) {
			loginSuccessfullSoAccessApplication();
		} else {
			loginFailedPrintError();
		}
	}

	@FXML
	void register(ActionEvent event) {
		manager.changeScreen(ScreensFramework.REGISTRATION_SCREEN);
	}

	@FXML
	void actionOnUsernameTextField(ActionEvent event) {
		lbl_loginFailedMessage.setVisible(false);
	}

	@FXML
	void actionOnPasswordTextField(ActionEvent event) {
		lbl_loginFailedMessage.setVisible(false);
	}

	@FXML
	void menuSettingsClicked(ActionEvent event) {
		manager.changeScreen(ScreensFramework.SETTINGS_SCREEN);
	}

	@FXML
	void menuCloseClicked(ActionEvent event) {
		Command closeCommand = new MenuCloseCommand(manager);
		closeCommand.execute();
	}

	@FXML
	void menuAboutClicked(ActionEvent event) {
		Command aboutCommand = new MenuAboutCommand(manager);
		aboutCommand.execute();
	}

	private void loginSuccessfullSoAccessApplication() {
		manager.setLoggedInUser(user);
		manager.changeScreen(ScreensFramework.LIBRARY_SCREEN);
	}

	private void loginFailedPrintError() {
		txt_username.setText("");
		psw_password.setText("");
		lbl_loginFailedMessage.setVisible(true);
	}

	private void addListenerToAllThePagesElementsThatRemoveLoginErrorMessageOnFocus() {
		txt_username.focusedProperty().addListener(listenerThatRemoveLoginErrorMessageWhenAnElementGainFocus());
		psw_password.focusedProperty().addListener(listenerThatRemoveLoginErrorMessageWhenAnElementGainFocus());
		btn_register.focusedProperty().addListener(listenerThatRemoveLoginErrorMessageWhenAnElementGainFocus());
	}

	private ChangeListener<Boolean> listenerThatRemoveLoginErrorMessageWhenAnElementGainFocus() {
		ChangeListener changeListener = new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
				lbl_loginFailedMessage.setVisible(false);
			}
		};
		return changeListener;
	}

	private void resetAllTheFormElements() {
		txt_username.setText("");
		psw_password.setText("");
		lbl_loginFailedMessage.setVisible(false);
	}
}
