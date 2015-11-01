package com.matteoveroni.mydiary.menu.model;

import com.matteoveroni.mydiary.application.manager.Manager;
import java.util.ResourceBundle;
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

    public void aboutAction(ResourceBundle resourceBundle) {
        JOptionPane.showMessageDialog(null, "" + resourceBundle.getString("author") + ": Matteo Veroni\n" + resourceBundle.getString("website") + ": www.matteoveroni.com\nBlog: www.infoeinternet.com", resourceBundle.getString("about") + " - " + manager.getApplicationTitle(), JOptionPane.INFORMATION_MESSAGE);
    }

    public void closeAction() {
        manager.dispose();
    }
}
