package com.matteoveroni.mydiary.registration.control;

import com.matteoveroni.mydiary.Observer;
import com.matteoveroni.mydiary.registration.model.RegistrationModel;
import com.matteoveroni.mydiary.registration.model.hibernate.HibernateRegistrationModel;
import com.matteoveroni.mydiary.screen.ManageableScreen;
import com.matteoveroni.mydiary.screen.ScreenManager;
import com.matteoveroni.mydiary.screen.ScreenType;
import com.matteoveroni.mydiary.user.model.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Registration Screen Controller class
 *
 * @author Matteo Veroni
 */
public class RegistrationScreenController implements ManageableScreen, Initializable, Observer {

    private ScreenManager screenManager;
    private RegistrationModel model = new HibernateRegistrationModel();
    private User userToRegistrate;

    @FXML
    private Label lbl_Username;
    @FXML
    private TextField txt_Username;
    @FXML
    private Label lbl_Password;
    @FXML
    private TextField txt_Password;
    @FXML
    private Label lbl_Name;
    @FXML
    private TextField txt_Name;
    @FXML
    private Label lbl_Surname;
    @FXML
    private TextField txt_Surname;
    @FXML
    private Label lbl_Age;
    @FXML
    private TextField txt_Age;
    @FXML
    private Label lbl_Title;
    @FXML
    private Button cmd_Cancel;
    @FXML
    private Button cmd_Register;

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

    @FXML
    void cancelRegistration(ActionEvent event) {
        screenManager.useScreen(ScreenType.LOGIN_SCREEN);
    }

    @FXML
    void registerUser(ActionEvent event) {
        if (areRequiredDataInsertedValid()) {
            model.createNewUser(userToRegistrate);
            screenManager.useScreen(ScreenType.LOGIN_SCREEN);
        }
    }

    private boolean areRequiredDataInsertedValid() {
        String insertedUsername = txt_Username.getText();
        String insertedPassword = txt_Password.getText();
        if (isUsernameValid(insertedUsername) && isPasswordValid(insertedPassword)) {
            return true;
        }
        return false;
    }

    private boolean isUsernameValid(String username) {
        final int MIN_USERNAME_LENGTH = 6;
        return (!username.trim().equals("") && username.length() >= MIN_USERNAME_LENGTH);
    }

    private boolean isPasswordValid(String password) {
        final int MIN_PASSWORD_LENGTH = 6;
        return (!password.trim().equals("") && password.length() >= MIN_PASSWORD_LENGTH);
    }
}
