package com.matteoveroni.mydiary.registration.control;

import com.matteoveroni.mydiary.application.messages.DataObjectMessage;
import com.matteoveroni.mydiary.application.manager.Manageable;
import com.matteoveroni.mydiary.application.manager.Manager;
import com.matteoveroni.mydiary.menu.model.commands.MenuAboutCommand;
import com.matteoveroni.mydiary.menu.model.commands.MenuCloseCommand;
import com.matteoveroni.mydiary.utilities.patterns.Listener;
import com.matteoveroni.mydiary.registration.model.RegistrationModel;
import com.matteoveroni.mydiary.screen.framework.ScreensFramework;
import com.matteoveroni.mydiary.user.model.bean.UserData;
import com.matteoveroni.mydiary.utilities.patterns.Command;
import java.net.URL;
import java.util.ResourceBundle;
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
import javax.swing.JOptionPane;

/**
 * Registration Screen Controller class
 *
 * @author Matteo Veroni
 */
public class RegistrationScreenController implements Manageable, Initializable, Listener {

    private Manager manager;
    private ResourceBundle resourceBundle;
    private final RegistrationModel model = new RegistrationModel();
    private final UserData userToRegister = new UserData();

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
    private Button btn_Cancel;
    @FXML
    private Button btn_Register;
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
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    @Override
    public void update(DataObjectMessage pushedData) {
        if (manager != null && manager.getLoggedInUser() != null) {
            clearAllTheFieldsOnTheForm();
        }
    }

    @Override
    public void setManager(Manager manager) {
        this.manager = manager;
        manager.registerListener(this);
    }

    @FXML
    void cancelRegistration(ActionEvent event) {
        manager.changeScreen(ScreensFramework.LOGIN_SCREEN);
    }

    @FXML
    void registerUser(ActionEvent event) {
        if (isUsernameAndPasswordInserted()) {
            setNewUserInstanceUsingTheDataInsertedOnTheForm();
            if (model.createNewUser(userToRegister)) {
                JOptionPane.showMessageDialog(null,
                    "\n " + resourceBundle.getString("username") + ": " + userToRegister.getUsername()
                    + "\n " + resourceBundle.getString("firstname") + ": " + userToRegister.getFirstName()
                    + "\n " + resourceBundle.getString("lastname") + ": " + userToRegister.getLastName()
                    + "\n " + resourceBundle.getString("age") + ": " + userToRegister.getAge(), resourceBundle.getString("userRegistered"), JOptionPane.INFORMATION_MESSAGE);
                manager.changeScreen(ScreensFramework.LOGIN_SCREEN);
            } else {
                clearUserFieldOnTheForm();
                JOptionPane.showMessageDialog(null, resourceBundle.getString("usernameAlreadyTaken"), manager.getApplicationTitle() + resourceBundle.getString("usernameAlreadyExists"), JOptionPane.ERROR_MESSAGE);
            }
        } else {
            clearUserAndPasswordFieldsOnTheForm();
            JOptionPane.showMessageDialog(null, resourceBundle.getString("usernameAndPasswordShort"), manager.getApplicationTitle() + resourceBundle.getString("usernameOrPasswordInvalid"), JOptionPane.ERROR_MESSAGE);
        }
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
        Command aboutCommand = new MenuAboutCommand(manager, resourceBundle);
        aboutCommand.execute();
    }

    private boolean isUsernameAndPasswordInserted() {
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

    private void setNewUserInstanceUsingTheDataInsertedOnTheForm() {
        userToRegister.setUsername(txt_Username.getText());
        userToRegister.setPassword(psw_Password.getText());
        userToRegister.setFirstName(txt_Name.getText());
        userToRegister.setLastName(txt_Surname.getText());
        try {
            userToRegister.setAge(Integer.parseInt(txt_Age.getText()));
        } catch (NumberFormatException ex) {
        }
    }

    private void clearUserAndPasswordFieldsOnTheForm() {
        txt_Username.setText("");
        psw_Password.setText("");
    }

    private void clearUserFieldOnTheForm() {
        txt_Username.setText("");
    }

    private void clearAllTheFieldsOnTheForm() {
        txt_Username.setText("");
        psw_Password.setText("");
        txt_Name.setText("");
        txt_Surname.setText("");
        txt_Age.setText("");
    }
}
