package com.matteoveroni.mydiary.login.control;

import com.matteoveroni.mydiary.database.DatabaseManager;
import com.matteoveroni.mydiary.screen.ManageableScreen;
import com.matteoveroni.mydiary.screen.ScreenManager;
import com.matteoveroni.mydiary.screen.ScreenType;
import com.matteoveroni.mydiary.user.ApplicationUser;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Login Screen Controller class
 *
 * @author Matteo Veroni
 */
public class LoginScreenController implements Initializable, ManageableScreen {

    @FXML
    private TextField txt_username;

    @FXML
    private PasswordField psw_password;

    @FXML
    private Label lbl_loginFailedMessage;

    private ScreenManager screenManager;
    private final DatabaseManager databaseManager = DatabaseManager.getInstance();
    private ApplicationUser applicationUser;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbl_loginFailedMessage.setVisible(false);
        addListenerToUsernameThatRemoveLoginErrorMessageOnFocus();
        addListenerToPasswordThatRemoveLoginErrorMessageOnFocus();
    }

    @Override
    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    @FXML
    void tryToLogin(ActionEvent event) {
        applicationUser = (ApplicationUser) databaseManager.readFirstObject(ApplicationUser.class);

        if (applicationUser != null) {
            if (txt_username.getText().equals(applicationUser.getName()) && psw_password.getText().equals(applicationUser.getPassword())) {
                loginSuccessfullSoAccessApplication();
            } else {
                loginFailedPrintError();
            }
        }
    }

    private void loginFailedPrintError() {
        txt_username.setText("");
        psw_password.setText("");
        lbl_loginFailedMessage.setVisible(true);
    }

    private void loginSuccessfullSoAccessApplication() {
        screenManager.useScreen(ScreenType.DIARY_SCREEN);
    }

    @FXML
    void actionOnUsernameTextField(ActionEvent event) {
        lbl_loginFailedMessage.setVisible(false);
    }

    @FXML
    void actionOnPasswordTextField(ActionEvent event) {
        lbl_loginFailedMessage.setVisible(false);
    }

    private void addListenerToUsernameThatRemoveLoginErrorMessageOnFocus() {
        txt_username.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                lbl_loginFailedMessage.setVisible(false);
            }
        });
    }

    private void addListenerToPasswordThatRemoveLoginErrorMessageOnFocus() {
        psw_password.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                lbl_loginFailedMessage.setVisible(false);
            }
        });
    }
}
