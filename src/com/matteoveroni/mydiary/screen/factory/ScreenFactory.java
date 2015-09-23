package com.matteoveroni.mydiary.screen.factory;

import com.matteoveroni.mydiary.screen.Screen;
import com.matteoveroni.mydiary.screen.framework.ScreensFramework;
import java.io.IOException;

/**
 *
 * @author Matteo Veroni
 */
public class ScreenFactory {

	public static Screen createScreen(ScreensFramework typeOfTheScreenToCreate) throws IOException {
		Screen screen = new Screen(typeOfTheScreenToCreate);
		return screen;
	}

}
