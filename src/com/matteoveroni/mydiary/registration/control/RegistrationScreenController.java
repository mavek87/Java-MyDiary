package com.matteoveroni.mydiary.registration.control;

import com.matteoveroni.mydiary.Observer;
import com.matteoveroni.mydiary.registration.model.RegistrationModel;
import com.matteoveroni.mydiary.registration.model.hibernate.HibernateRegistrationModel;
import com.matteoveroni.mydiary.screen.ManageableScreen;
import com.matteoveroni.mydiary.screen.ScreenManager;
import com.matteoveroni.mydiary.screen.ScreenType;
import com.matteoveroni.mydiary.user.model.User;
import com.matteoveroni.mydiary.user.model.hibernate.PersistentHibernateUser;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Registration Screen Controller class
 *
 * @author Matteo Veroni
 */
public class RegistrationScreenController implements ManageableScreen, Initializable, Observer {

    private ScreenManager screenManager;
    private RegistrationModel model = new HibernateRegistrationModel();
    private final User userToRegistrate = new PersistentHibernateUser();

    private final int MIN_USERNAME_LENGTH = 6;
    private final int MIN_PASSWORD_LENGTH = 6;

    @FXML
    private Label lbl_Username;
    @FXML
    private TextField txt_Username;
    @FXML
    private Label lbl_Password;
    @FXML
    private PasswordField psw_Password;
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
        resetAllTheFieldsOnTheForm();
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
            initializeUserToRegistrate();
            model.createNewUser(userToRegistrate);
            screenManager.useScreen(ScreenType.LOGIN_SCREEN);
        } else {
            resetUserAndPasswordFieldsOnTheForm();
        }
    }

    private boolean areRequiredDataInsertedValid() {
        String insertedUsername = txt_Username.getText();
        String insertedPassword = psw_Password.getText();
        return (isUsernameValid(insertedUsername) && isPasswordValid(insertedPassword));
    }

    private boolean isUsernameValid(String username) {
        return (!username.trim().equals("") && username.length() >= MIN_USERNAME_LENGTH);
    }

    private boolean isPasswordValid(String password) {
        return (!password.trim().equals("") && password.length() >= MIN_PASSWORD_LENGTH);
    }

    private void initializeUserToRegistrate() {
        userToRegistrate.setUsername(txt_Username.getText());
        userToRegistrate.setPassword(psw_Password.getText());
        userToRegistrate.setName(txt_Name.getText());
        userToRegistrate.setSurname(txt_Surname.getText());
        try {
            userToRegistrate.setAge(Integer.parseInt(txt_Age.getText()));
        } catch (Exception ex) {
            userToRegistrate.setAge(0);
        }
    }

    private void resetUserAndPasswordFieldsOnTheForm() {
        txt_Username.setText("");
        psw_Password.setText("");
    }

    private void resetAllTheFieldsOnTheForm() {
        txt_Username.setText("");
        psw_Password.setText("");
        txt_Name.setText("");
        txt_Surname.setText("");
        txt_Age.setText("");
    }
}
