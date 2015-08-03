package com.matteoveroni.mydiary.view.screen;

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

    public Screen createScreen(Screens screen) throws IOException{
        Screen screenToCreate;
        switch (screen) {
            case ARTICLE_SCREEN:
                System.out.println("Creo schermo: " + screen.screenName() + " " + screen.screenResource());
                screenToCreate = new Screen(screen.screenName(), screen.screenResource());
                break;
            default:
                screenToCreate = null;
                break;
        }
        return screenToCreate;
    }

}
