package com.matteoveroni.mydiary.diary.control;

import com.matteoveroni.mydiary.application.manager.Manageable;
import com.matteoveroni.mydiary.application.manager.Manager;
import com.matteoveroni.mydiary.annotation.model.bean.HibernateAnnotationBean;
import com.matteoveroni.mydiary.patterns.Listener;
import com.matteoveroni.mydiary.annotation.model.bean.Annotation;
import com.matteoveroni.mydiary.diary.model.bean.Diary;
import com.matteoveroni.mydiary.diary.model.DiaryModel;
import com.matteoveroni.mydiary.diary.model.HibernateDiaryModel;
import com.matteoveroni.mydiary.diary.model.bean.HibernateDiaryBean;
import com.matteoveroni.mydiary.screen.ScreensFramework;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Matteo Veroni
 */
public class DiaryScreenController implements Initializable, Manageable, Listener {

	private Manager manager;
	private final Diary currentDiary = new HibernateDiaryBean();
	private final DiaryModel model = new HibernateDiaryModel();

	@FXML
	private TableView<Annotation> diaryTable;
	@FXML
	private TableColumn<Annotation, Long> tableColumn_Id;
	@FXML
	private TableColumn<Annotation, String> tableColumn_Title;
	@FXML
	private TableColumn<Annotation, Date> tableColumn_CreationDate;
	@FXML
	private TableColumn<Annotation, Date> tableColumn_LastModificationDate;
	@FXML
	private TableColumn<Annotation, String> tableColumn_Author;
	@FXML
	private Button btn_filter;
	@FXML
	private Button btn_button;
	@FXML
	private Button btn_createNewAnnotation;
	@FXML
	private CheckBox chk_enableFilter;
	@FXML
	private Button btn_removeAnnotation;

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
	public void setManager(Manager manager) {
		this.manager = manager;
		manager.registerListener(this);
	}

	@Override
	public void update() {
		updateScene();
	}

	@FXML
	void goToAnnotationScreen(ActionEvent event) {
		manager.changeScreen(ScreensFramework.ANNOTATION_SCREEN);
	}

	@FXML
	void goToFilterScreen(ActionEvent event) {
	}

	@FXML
	void removeAnnotation(ActionEvent event) {
		Annotation currentAnnotationSelected = diaryTable.getSelectionModel().getSelectedItem();
		if (currentAnnotationSelected != null) {
			model.removeAnnotation(currentAnnotationSelected);
			updateScene();
		}
	}

	@FXML
	void createNewAnnotation(ActionEvent event) {
		Annotation newAnnotation = new HibernateAnnotationBean();
		newAnnotation.setTitle("New Title");
        newAnnotation.setAuthor(manager.getLoggedInUser().toString());
		newAnnotation.setCreationDate(new Date());
		newAnnotation.setLastModificationTimestamp(new Date());
		model.createNewAnnotation(newAnnotation);
		manager.changeScreen(ScreensFramework.ANNOTATION_SCREEN);
	}

	@FXML
	void enableFilter(ActionEvent event) {
	}

	private void updateScene() {
		List<Annotation> annotationsFromDatabase = model.getAllTheAnnotations();

		ObservableList<Annotation> annotations = FXCollections.observableArrayList(annotationsFromDatabase);

		tableColumn_Id.setCellValueFactory(new PropertyValueFactory<Annotation, Long>("id"));
		tableColumn_Title.setCellValueFactory(new PropertyValueFactory<Annotation, String>("title"));
		tableColumn_CreationDate.setCellValueFactory(new PropertyValueFactory<Annotation, Date>("creationdate"));
		tableColumn_LastModificationDate.setCellValueFactory(new PropertyValueFactory<Annotation, Date>("lastmodificationdate"));
		tableColumn_Author.setCellValueFactory(new PropertyValueFactory<Annotation, String>("author"));        
		diaryTable.setItems(annotations);
	}
}
