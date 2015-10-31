package com.matteoveroni.mydiary.settings.control;

import com.matteoveroni.mydiary.application.messages.DataObjectMessage;
import com.matteoveroni.mydiary.application.manager.Manageable;
import com.matteoveroni.mydiary.application.manager.Manager;
import com.matteoveroni.mydiary.bundles.ResourceBundleFileHandler;
import com.matteoveroni.mydiary.bundles.ResourceBundleFramework;
import com.matteoveroni.mydiary.menu.model.commands.MenuAboutCommand;
import com.matteoveroni.mydiary.menu.model.commands.MenuCloseCommand;
import com.matteoveroni.mydiary.utilities.patterns.Listener;
import com.matteoveroni.mydiary.screen.framework.ScreensFramework;
import com.matteoveroni.mydiary.utilities.patterns.Command;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Settings Screen Controller class
 *
 * @author Matteo Veroni
 */
public class SettingsScreenController implements Manageable, Initializable, Listener {

    private Manager manager;
//	private final RegistrationModel model = new SettingsModel();

    @FXML
    private Button btn_Cancel;
    @FXML
    private Menu menu_file;
    @FXML
    private Menu menu_help;
    @FXML
    private MenuItem menu_close;
    @FXML
    private MenuBar menu;
    @FXML
    private Button btn_Save;
    @FXML
    private MenuItem menu_about;
    @FXML
    private MenuItem menu_settings;
    @FXML
    private ComboBox<Locale> cmb_languageSelector;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Locale currentLocale = readCurrentLocale();
        populateLanguageSelectorComboboxWithLanguages();
        cmb_languageSelector.getSelectionModel().select(currentLocale);
    }

    private void populateLanguageSelectorComboboxWithLanguages() {
        ObservableList<Locale> options = FXCollections.observableArrayList(ResourceBundleFramework.SUPPORTED_ENGLISH_LOCALE.getLocale());
        cmb_languageSelector.getItems().addAll(options);
    }

    private Locale readCurrentLocale() {
        ResourceBundleFileHandler resourceBundleFileHandler = new ResourceBundleFileHandler();
        return resourceBundleFileHandler.getLocale();
    }

    @Override
    public void update(DataObjectMessage pushedData) {
    }

    @Override
    public void setManager(Manager manager) {
        this.manager = manager;
        manager.registerListener(this);
    }

    @FXML
    void saveSettings(ActionEvent event) {

    }

    @FXML
    void cancelSettings(ActionEvent event) {
        if (!manager.usePreviousScreen()) {
            manager.changeScreen(ScreensFramework.LOGIN_SCREEN);
        }
    }

    @FXML
    void menuSettingsClicked(ActionEvent event) {
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

    @FXML
    void cancelRegistration(ActionEvent event) {
        manager.changeScreen(ScreensFramework.LOGIN_SCREEN);
    }

    @FXML
    void comboBoxLanguageSelected(ActionEvent event) {

    }

}
