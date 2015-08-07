package com.matteoveroni.mydiary.screen;

/**
 * @author Matteo Veroni
 */
public enum ScreenType {

    ARTICLE_SCREEN("Article Screen", "/com/matteoveroni/mydiary/article/view/ArticleScreenView.fxml"),
    DIARY_SCREEN("Diary Screen", "/com/matteoveroni/mydiary/diary/view/DiaryScreenView.fxml");

    private final String screenName;
    private final String screenResourcePath;

    ScreenType(String screenName, String screenResourcePath) {
        this.screenName = screenName;
        this.screenResourcePath = screenResourcePath;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getScreenResourcePath() {
        return screenResourcePath;
    }

}
