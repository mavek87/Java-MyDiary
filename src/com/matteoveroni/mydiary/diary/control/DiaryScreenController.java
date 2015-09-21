package com.matteoveroni.mydiary.diary.control;

import com.matteoveroni.mydiary.application.manager.Manageable;
import com.matteoveroni.mydiary.application.manager.Manager;
import com.matteoveroni.mydiary.utilities.patterns.Listener;
import com.matteoveroni.mydiary.annotation.model.bean.Annotation;
import com.matteoveroni.mydiary.application.manager.DataObjectMessage;
import com.matteoveroni.mydiary.diary.model.DiaryModel;
import com.matteoveroni.mydiary.diary.model.bean.Diary;
import com.matteoveroni.mydiary.exceptions.CriticalRuntimeException;
import com.matteoveroni.mydiary.library.control.LibraryScreenController;
import com.matteoveroni.mydiary.screen.ScreensFramework;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DiaryScreenController Controller class
 *
 * @author Matteo Veroni
 */
public class DiaryScreenController implements Initializable, Manageable, Listener {

	private Manager manager;
	private final DiaryModel model = new DiaryModel();
	private Diary currentDiary = new Diary();
	private Annotation currentSelectedAnnotation;

	private static final Logger LOG = LoggerFactory.getLogger(DiaryScreenController.class);

	@FXML
	private TableView<Annotation> diaryTable;
	@FXML
	private TableColumn<Annotation, Long> tableColumn_Id;
	@FXML
	private TableColumn<Annotation, String> tableColumn_Title;
	@FXML
	private TableColumn<Annotation, Date> tableColumn_CreationDate;
	@FXML
	private TableColumn<Annotation, Date> tableColumn_LastModificationTimestamp;
	@FXML
	private TableColumn<Annotation, String> tableColumn_Author;
	@FXML
	private Button btn_filterAnnotations;
	@FXML
	private Button btn_openAnnotation;
	@FXML
	private Button btn_createNewAnnotation;
	@FXML
	private CheckBox chk_enableFilter;
	@FXML
	private Button btn_removeAnnotation;

	/**
	 * Initializes the DiaryScreenController class.
	 *
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		diaryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		tableColumn_Id.setCellValueFactory(new PropertyValueFactory<Annotation, Long>("id"));
		tableColumn_Title.setCellValueFactory(new PropertyValueFactory<Annotation, String>("title"));
		tableColumn_CreationDate.setCellValueFactory(new PropertyValueFactory<Annotation, Date>("creationDate"));
		tableColumn_LastModificationTimestamp.setCellValueFactory(new PropertyValueFactory<Annotation, Date>("lastModificationTimestamp"));
		tableColumn_Author.setCellValueFactory(new PropertyValueFactory<Annotation, String>("author"));

		diaryTable.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
				if (newPropertyValue) {
					// diaryTable's focus ON
					if (diaryTable.getSelectionModel().getSelectedItem() != null) {
						currentSelectedAnnotation = diaryTable.getSelectionModel().getSelectedItem();
						btn_openAnnotation.setDisable(false);
						btn_removeAnnotation.setDisable(false);
					}
				}
			}
		});

		diaryTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					currentSelectedAnnotation = diaryTable.getSelectionModel().getSelectedItem();
					openSelectedAnnotation();
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
		if (manager.getLoggedInUser() != null) {
			try {
				if (pushedData != null && pushedData.getSenderClass().equals(LibraryScreenController.class)) {
					currentDiary = (Diary) pushedData.getData();
					model.setDiary(currentDiary);
					currentSelectedAnnotation = null;
                    
                    
					List<Annotation> annotationsFromDatabase = model.getAllTheAnnotations();

					
					
					ObservableList<Annotation> annotationsForTable = FXCollections.observableArrayList(annotationsFromDatabase);
					diaryTable.setItems(annotationsForTable);
					btn_openAnnotation.setDisable(true);
					btn_removeAnnotation.setDisable(true);
				}
			} catch (Exception ex) {
				LOG.error("Critical Runtime Exception Occurred -> " + ex.getMessage());
				throw new CriticalRuntimeException(ex, manager);
			}
		}
	}

	@FXML
	void goToAnnotationScreen(ActionEvent event) {
		if (currentSelectedAnnotation != null) {
			manager.storeObjectToPush(currentSelectedAnnotation, DiaryScreenController.class);
			manager.changeScreen(ScreensFramework.ANNOTATION_SCREEN);
		}
	}

	@FXML
	void removeAnnotation(ActionEvent event) {
		if (currentSelectedAnnotation != null) {
			model.removeAnnotation(currentSelectedAnnotation);
			update(null);
		}
	}

	@FXML
	void createNewAnnotation(ActionEvent event) {
		Annotation newAnnotation = new Annotation();
		newAnnotation.setTitle("New Title");
//        newAnnotation.setAuthor(manager.getLoggedInUser().toString());
		newAnnotation.setCreationDate(new Date());
		newAnnotation.setLastModificationTimestamp(new Date());
		model.createNewAnnotation(newAnnotation);
		manager.changeScreen(ScreensFramework.ANNOTATION_SCREEN);
	}

	@FXML
	void goToFilterScreen(ActionEvent event) {
	}

	@FXML
	void enableFilter(ActionEvent event) {
	}

	private void openSelectedAnnotation() {
		if (currentSelectedAnnotation != null) {
			manager.storeObjectToPush(currentSelectedAnnotation, DiaryScreenController.class);
			manager.changeScreen(ScreensFramework.ANNOTATION_SCREEN);
		}
	}
}
