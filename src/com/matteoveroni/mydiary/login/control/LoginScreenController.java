package com.matteoveroni.mydiary.login.control;

import com.matteoveroni.mydiary.application.manager.ApplicationManager;
import com.matteoveroni.mydiary.application.manager.Manageable;
import com.matteoveroni.mydiary.patterns.Listener;
import com.matteoveroni.mydiary.login.model.LoginModel;
import com.matteoveroni.mydiary.login.model.hibernate.HibernateLoginModel;
import com.matteoveroni.mydiary.screen.ScreenType;
import com.matteoveroni.mydiary.user.model.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Login Screen Controller class
 *
 * @author Matteo Veroni
 */
public class LoginScreenController implements Manageable, Initializable, Listener {

    private ApplicationManager manager;
    private User registeredUser;
    private final LoginModel model = new HibernateLoginModel();

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
    public void update() {
        resetAllTheFormElements();
    }

    @Override
    public void setManager(ApplicationManager manager) {
        this.manager = manager;
        manager.registerListener(this);
    }

    @FXML
    void login(ActionEvent event) {
        String insertedUsername = txt_username.getText();
        registeredUser = model.getUser(insertedUsername);
        if (registeredUser != null) {
            if (txt_username.getText().equals(registeredUser.getUsername()) && psw_password.getText().equals(registeredUser.getPassword())) {
                loginSuccessfullSoAccessApplication();
            }
        } else {
            loginFailedPrintError();
        }
    }

    @FXML
    void register(ActionEvent event) {
        manager.changeScreen(ScreenType.REGISTRATION_SCREEN);
    }

    @FXML
    void actionOnUsernameTextField(ActionEvent event) {
        lbl_loginFailedMessage.setVisible(false);
    }

    @FXML
    void actionOnPasswordTextField(ActionEvent event) {
        lbl_loginFailedMessage.setVisible(false);
    }

    private void loginSuccessfullSoAccessApplication() {
        manager.changeScreen(ScreenType.DIARY_SCREEN);
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
