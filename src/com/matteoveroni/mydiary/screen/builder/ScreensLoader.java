package com.matteoveroni.mydiary.screen.builder;

import com.matteoveroni.mydiary.screen.Screen;
import com.matteoveroni.mydiary.screen.framework.ScreensFramework;
import com.matteoveroni.mydiary.screen.factory.ScreenFactory;
import com.matteoveroni.mydiary.screen.manager.ScreenManager;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class ScreensLoader {

    private final ScreenManager screenManager;
    private static final Logger LOG = LoggerFactory.getLogger(ScreensLoader.class);

    public ScreensLoader(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    public List<Screen> loadScreensInScreenManager(Locale locale) {
        int indexOfScreenToBuild = 0;
        try {
            for (ScreensFramework screenTypeToBuild : ScreensFramework.values()) {
                Screen newScreen = ScreenFactory.createScreen(screenTypeToBuild, locale);
                screenManager.loadScreen(newScreen);
                indexOfScreenToBuild++;
            }
        } catch (Exception ex) {
            LOG.error(" ---> IMPOSSIBLE TO BUILD SCREEN -> \'" + ScreensFramework.values()[indexOfScreenToBuild] + "\'!\n\n");
            LOG.error(" ---> Exception occurred: \n" + ex);
            throw new RuntimeException(ex);
        }
        return null;
    }
}
