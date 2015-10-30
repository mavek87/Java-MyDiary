package com.matteoveroni.mydiary.screen.factory;

import com.matteoveroni.mydiary.screen.Screen;
import com.matteoveroni.mydiary.screen.framework.ScreensFramework;
import java.io.IOException;
import java.util.Locale;

/**
 *
 * @author Matteo Veroni
 */
public class ScreenFactory {

	public static Screen createScreen(ScreensFramework typeOfTheScreenToCreate, Locale locale) throws IOException {
		Screen screen = new Screen(typeOfTheScreenToCreate, locale);
		return screen;
	}

}
