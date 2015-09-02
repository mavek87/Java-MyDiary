package com.matteoveroni.mydiary.screen;

/**
 * @author Matteo Veroni
 */
public enum ScreenType {

    ARTICLE_SCREEN("Article Screen", "/com/matteoveroni/mydiary/article/view/ArticleScreenView.fxml"),
    DIARY_SCREEN("Diary Screen", "/com/matteoveroni/mydiary/diary/view/DiaryScreenView.fxml"),
    LOGIN_SCREEN("Login Screen", "/com/matteoveroni/mydiary/login/view/fxml/LoginScreenView.fxml");

    private final String name;
    private final String resourcePath;

    ScreenType(String screenName, String screenResourcePath) {
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
