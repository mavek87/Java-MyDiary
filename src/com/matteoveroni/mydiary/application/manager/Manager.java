package com.matteoveroni.mydiary.application.manager;

import com.matteoveroni.mydiary.utilities.patterns.Listener;
import com.matteoveroni.mydiary.screen.ScreensFramework;
import com.matteoveroni.mydiary.user.model.bean.User;

/**
 *
 * @author Matteo Veroni
 */
public interface Manager {

    public void registerListener(Listener listener);

    public void removeListener(Listener listener);

    public void notifyListeners(DataObjectMessage dataToPush);

    public void changeScreen(ScreensFramework screenType);

    public User getLoggedInUser();

    public void setLoggedInUser(User loggedInUser);

    public void storeObjectToPush(Object dataToPush, Class senderClass);

    public void dispose();
}
