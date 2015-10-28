package com.matteoveroni.mydiary.menu.model;

import com.matteoveroni.mydiary.application.manager.Manager;
import javax.swing.JOptionPane;

/**
 *
 * @author Matteo Veroni
 */
public class MenuModel {

	private final Manager manager;

	public MenuModel(Manager manager) {
		this.manager = manager;
	}

	public void settingsAction() {

	}

	public void aboutAction() {
		JOptionPane.showMessageDialog(null, "" + manager.getApplicationTitle() + "\nAuthor: Matteo Veroni\nWebsite: www.matteoveroni.com\nBlog: www.infoeinternet.com", "About - " + manager.getApplicationTitle(), JOptionPane.INFORMATION_MESSAGE);
	}

	public void closeAction() {
		manager.dispose();
	}
}
