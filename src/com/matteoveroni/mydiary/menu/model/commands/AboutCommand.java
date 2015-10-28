package com.matteoveroni.mydiary.menu.model.commands;

import com.matteoveroni.mydiary.application.manager.Manager;
import com.matteoveroni.mydiary.menu.model.MenuModel;
import com.matteoveroni.mydiary.utilities.patterns.Command;

/**
 *
 * @author Matteo Veroni
 */
public class AboutCommand implements Command {

	private final MenuModel menuModel;

	public AboutCommand(Manager manager) {
		menuModel = new MenuModel(manager);
	}

	@Override
	public void execute() {
		menuModel.aboutAction();
	}
}
