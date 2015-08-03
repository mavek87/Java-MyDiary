package com.matteoveroni.mydiary.view.screen;

/**
 * @author Matteo Veroni
 */
public enum Screens {
    
    ARTICLE_SCREEN ("Article_Screen", "fxml/ArticleScreen.fxml"),
    TASK_SCREEN ("Action Screen", "fxml/TaskScreen.fxml");

    private final String screenName;
    private final String screenResource;
    
    Screens(String screenName, String screenResource) {
        this.screenName = screenName;
        this.screenResource = screenResource;
    }
    
    public String screenName() { return screenName; }
    public String screenResource() { return screenResource; }
        
}
