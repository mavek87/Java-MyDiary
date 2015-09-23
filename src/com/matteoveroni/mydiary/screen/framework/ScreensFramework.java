package com.matteoveroni.mydiary.screen.framework;

/**
 * @author Matteo Veroni
 */
public enum ScreensFramework {

	NOTE_SCREEN("Note Screen", "/com/matteoveroni/mydiary/note/view/NoteScreenView.fxml"),
	DIARY_SCREEN("Diary Screen", "/com/matteoveroni/mydiary/diary/view/DiaryScreenView.fxml"),
    LIBRARY_SCREEN("Library Screen", "/com/matteoveroni/mydiary/library/view/LibraryScreenView.fxml"),
	LOGIN_SCREEN("Login Screen", "/com/matteoveroni/mydiary/login/view/LoginScreenView.fxml"),
	REGISTRATION_SCREEN("Registration Screen", "/com/matteoveroni/mydiary/registration/view/RegistrationScreenView.fxml");

	private final String name;
	private final String resourcePath;

	ScreensFramework(String screenName, String screenResourcePath) {
		this.name = screenName;
		this.resourcePath = screenResourcePath;
	}

	public String getScreenName() {
		return name;
	}

	public String getScreenResourcePath() {
		return resourcePath;
	}

}
