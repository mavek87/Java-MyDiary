package com.matteoveroni.mydiary.menu.model.commands;

import com.matteoveroni.mydiary.application.manager.Manager;
import com.matteoveroni.mydiary.menu.model.MenuModel;
import com.matteoveroni.mydiary.utilities.patterns.Command;
import java.util.ResourceBundle;

/**
 *
 * @author Matteo Veroni
 */
public class MenuAboutCommand implements Command {

	private final MenuModel menuModel;
    private final ResourceBundle resourceBundle;

	public MenuAboutCommand(Manager manager, ResourceBundle resourceBundle) {
		menuModel = new MenuModel(manager);
        this.resourceBundle = resourceBundle;
	}

	@Override
	public void execute() {
		menuModel.aboutAction(resourceBundle);
	}
}
