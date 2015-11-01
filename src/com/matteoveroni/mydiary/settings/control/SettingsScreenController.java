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
import java.util.ArrayList;
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
import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Settings Screen Controller class
 *
 * @author Matteo Veroni
 */
public class SettingsScreenController implements Manageable, Initializable, Listener {

    private Manager manager;
    private ResourceBundle resourceBundle;
    private Locale currentLocale;
    private String currentLocaleName;

    private final ArrayList<String> supportedLanguagesNames = new ArrayList<>();
    private final ResourceBundleFileHandler resourceBundleFileHandler = new ResourceBundleFileHandler();

    private static final Logger LOG = LoggerFactory.getLogger(SettingsScreenController.class);

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
    private ComboBox<String> cmb_languageSelector;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        currentLocale = resourceBundleFileHandler.getLocale();
        LOG.debug("Current Locale -> " + currentLocale.toString());
        populateLanguageSelectorComboboxWithLanguages(currentLocale);
    }

    private void populateLanguageSelectorComboboxWithLanguages(Locale currentLocale) {
        for (ResourceBundleFramework supportedLanguage : ResourceBundleFramework.values()) {
            if (!supportedLanguage.getLocaleName().equals(ResourceBundleFramework.SUPPORTED_DEFAULT_LOCALE.getLocaleName())) {
                supportedLanguagesNames.add(supportedLanguage.getLocaleName());
                if (currentLocale.toString().equals(supportedLanguage.getLocale().toString())) {
                    currentLocaleName = supportedLanguage.getLocaleName();
                    LOG.debug("Current Locale Name -> " + currentLocaleName);
                    cmb_languageSelector.getSelectionModel().select(currentLocaleName);
                }
            }
        }
        ObservableList<String> listOfSupportedLanguages = FXCollections.observableArrayList(supportedLanguagesNames);
        cmb_languageSelector.getItems().addAll(listOfSupportedLanguages);
        LOG.debug("Combobox for language selection populated -> " + currentLocale.toString());
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
        String selectedLocaleName = cmb_languageSelector.getSelectionModel().getSelectedItem();
        if (!selectedLocaleName.equals(currentLocaleName)) {
            LOG.debug("Selected locale " + selectedLocaleName + " that is different from the previous locale that was " + currentLocaleName);
            int changeLanguageAndExit = JOptionPane.showConfirmDialog(null,
                resourceBundle.getString("differentLanguageSelected"),
                resourceBundle.getString("languageChanged"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            if (changeLanguageAndExit == JOptionPane.YES_OPTION) {
                Locale newSelectedLocale = null;
                for (ResourceBundleFramework resource : ResourceBundleFramework.values()) {
                    if (resource.getLocaleName().equals(selectedLocaleName)) {
                        newSelectedLocale = resource.getLocale();
                        LOG.debug("New selected locale is " + selectedLocaleName + " -> " + newSelectedLocale);
                        break;
                    }
                }
                if (newSelectedLocale != null) {
                    LOG.debug("Saving the new selected locale");
                    resourceBundleFileHandler.setLocale(newSelectedLocale);
                    manager.dispose();
                }
            }
        }
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
