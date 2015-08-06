package com.matteoveroni.mydiary.screen;

import com.matteoveroni.mydiary.screen.model.Screen;
import java.io.IOException;

/**
 *
 * @author Matteo Veroni
 */
public class ScreensFactory {

    private static ScreensFactory screenFactory;

    private ScreensFactory() {
    }

    public static ScreensFactory getInstance() {
        if (screenFactory == null) {
            screenFactory = new ScreensFactory();
        }
        return screenFactory;
    }

    public Screen createScreen(ScreenType screen) throws IOException{
        Screen screenToCreate;
        switch (screen) {
            case ARTICLE_SCREEN:
                System.out.println("Creo schermo: " + screen.getScreenName() + " " + screen.getScreenResourcePath());
                screenToCreate = new Screen(screen);
                break;
            default:
                screenToCreate = null;
                break;
        }
        return screenToCreate;
    }

}
