package com.matteoveroni.mydiary.screen;

/**
 * @author Matteo Veroni
 */
public enum ScreenType {

    ARTICLE_SCREEN("Article Screen", "/com/matteoveroni/mydiary/article/view/ArticleScreenView.fxml"),
    TASK_SCREEN("Task Screen", "/com/matteoveroni/mydiary/task/view/TaskScreenView.fxml");

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
