package com.matteoveroni.mydiary.library.control;

import com.matteoveroni.mydiary.application.manager.Manageable;
import com.matteoveroni.mydiary.application.manager.Manager;
import com.matteoveroni.mydiary.utilities.patterns.Listener;
import com.matteoveroni.mydiary.application.messages.DataObjectMessage;
import com.matteoveroni.mydiary.diary.model.bean.Diary;
import com.matteoveroni.mydiary.library.model.LibraryModel;
import com.matteoveroni.mydiary.menu.model.commands.MenuAboutCommand;
import com.matteoveroni.mydiary.menu.model.commands.MenuCloseCommand;
import com.matteoveroni.mydiary.screen.framework.ScreensFramework;
import com.matteoveroni.mydiary.utilities.patterns.Command;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LibraryScreenController Controller class
 *
 * @author Matteo Veroni
 */
public class LibraryScreenController implements Initializable, Manageable, Listener {

	private Manager manager;
	private ResourceBundle resourceBundle;
	private Diary selectedDiary = new Diary();
	private final LibraryModel model = new LibraryModel();
	List<Diary> userDiaries = new ArrayList<>();
	List<String> userDiariesStringsForCombobox = new ArrayList<>();
	private static final Logger LOG = LoggerFactory.getLogger(LibraryScreenController.class);

	@FXML
	private TabPane tabPane;
	@FXML
	private Tab tab_manageDiary;
	@FXML
	private Tab tab_selectDiary;
	@FXML
	private Tab tab_createNewDiary;
	@FXML
	private Button btn_createDiary;
	@FXML
	private Button btn_openDiary;
	@FXML
	private ComboBox<String> cmb_chooseDiary;
	@FXML
	private TextField txt_newDiaryName;
	@FXML
	private MenuBar menu;
	@FXML
	private Menu menu_file;
	@FXML
	private MenuItem menu_settings;
	@FXML
	private MenuItem menu_logout;
	@FXML
	private MenuItem menu_close;
	@FXML
	private Menu menu_help;
	@FXML
	private MenuItem menu_about;

	/**
	 * Initializes the DiaryScreenController class.
	 *
	 * @param url
	 * @param resourceBundle
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		this.resourceBundle = resourceBundle;

		cmb_chooseDiary.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {
					int selectedDiaryIndex = cmb_chooseDiary.getSelectionModel().getSelectedIndex();
					selectedDiary = userDiaries.get(selectedDiaryIndex);
					btn_openDiary.setDisable(false);
				} catch (Exception ex) {
					if (!btn_openDiary.isDisable()) {
						btn_openDiary.setDisable(true);
					}
					selectedDiary = null;
				}
			}
		});
	}

	@Override
	public void setManager(Manager manager) {
		this.manager = manager;
		manager.registerListener(this);
	}

	@Override
	public void update(DataObjectMessage pushedData) {
		if (manager != null && manager.getLoggedInUser() != null) {
			updateDiaryComboBox();
			btn_openDiary.setDisable(true);
			if (cmb_chooseDiary.getItems().size() == 0) {
				cmb_chooseDiary.setPromptText(resourceBundle.getString("empty"));
			} else {
				cmb_chooseDiary.setPromptText(resourceBundle.getString("selectADiary"));
				if (cmb_chooseDiary.getSelectionModel().getSelectedIndex() > 0) {
					btn_openDiary.setDisable(false);
				}
			}
		}
	}

	@FXML
	void openSelectedDiary(ActionEvent event) {
		if (selectedDiary != null) {
			manager.storeObjectToPush(selectedDiary, LibraryScreenController.class);
			manager.changeScreen(ScreensFramework.DIARY_SCREEN);
		}
	}

	@FXML
	void createNewDiary(ActionEvent event) {
		if (txt_newDiaryName.getText() != null && !txt_newDiaryName.getText().trim().equals("")) {
			Diary diary = new Diary();
			diary.setName(txt_newDiaryName.getText());
			if (model.createNewDiary(diary, manager.getLoggedInUser())) {
				update(null);
				JOptionPane.showMessageDialog(null, resourceBundle.getString("newDiary") + " \'" + diary.getName() + "\' " + resourceBundle.getString("created"));
			} else {
				JOptionPane.showMessageDialog(null, resourceBundle.getString("errorDuringTheCreationOf") + " \'" + diary.getName());
			}
			txt_newDiaryName.setText("");
		}
	}

	@FXML
	void tabSelectDiaryActive() {
		update(null);
	}

	@FXML
	void tabCreateNewDiaryActive() {
		txt_newDiaryName.setText("");
	}

	@FXML
	void tabManageDiaryActive() {
	}

	@FXML
	void menuSettingsClicked(ActionEvent event) {
		manager.changeScreen(ScreensFramework.SETTINGS_SCREEN);
	}

	@FXML
	void menuLogoutClicked(ActionEvent event) {
		resetLibraryScreenInputFields();
		manager.setLoggedInUser(null);
		manager.changeScreen(ScreensFramework.LOGIN_SCREEN);
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

	private void updateDiaryComboBox() {
		List<Diary> findedUserDiaries = model.getUserDiaries(manager.getLoggedInUser());
		if (findedUserDiaries != null && findedUserDiaries.size() > 0) {
			userDiaries.clear();
			userDiariesStringsForCombobox.clear();
			for (Diary diary : findedUserDiaries) {
				userDiaries.add(diary);
				userDiariesStringsForCombobox.add(diary.getId() + " - " + diary.getName() + " - " + manager.getLoggedInUser());
			}
			ObservableList<String> observableUserDiaries = FXCollections.observableArrayList(userDiariesStringsForCombobox);
			cmb_chooseDiary.setItems(observableUserDiaries);
		}
	}

	private void resetLibraryScreenInputFields() {
		cmb_chooseDiary.getItems().removeAll(cmb_chooseDiary.getItems());
		txt_newDiaryName.setText("");
		tabPane.getSelectionModel().select(tab_selectDiary);
	}
}
