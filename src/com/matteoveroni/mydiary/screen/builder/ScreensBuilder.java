package com.matteoveroni.mydiary.screen.builder;

import com.matteoveroni.mydiary.screen.Screen;
import com.matteoveroni.mydiary.screen.ScreensFramework;
import com.matteoveroni.mydiary.screen.factory.ScreenFactory;
import com.matteoveroni.mydiary.screen.manager.ScreenManager;
import java.util.List;

/**
 *
 * @author Matteo Veroni
 */
public class ScreensBuilder {

    private ScreensBuilder screensBuilder;
    private ScreenManager screenManager;

    private ScreensBuilder(){}
    
    public ScreensBuilder getInstance(){
        if(screensBuilder==null){
            screensBuilder = new ScreensBuilder();
        }
        return screensBuilder;
    }
    
    public void injectManager(ScreenManager screenManager){
        this.screenManager = screenManager;
    }
    
    public List<Screen> loadScreensInScreenManager() {
		
        return null;
    }
}
