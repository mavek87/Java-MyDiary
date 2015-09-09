package com.matteoveroni.mydiary.screen.factory;

import com.matteoveroni.mydiary.screen.Screen;
import com.matteoveroni.mydiary.screen.ScreenType;
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

	public Screen createScreen(ScreenType typeOfTheScreenToCreate) throws IOException {
		Screen screen = new Screen(typeOfTheScreenToCreate);
		return screen;
	}

}
