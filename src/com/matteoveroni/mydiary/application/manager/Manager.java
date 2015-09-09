package com.matteoveroni.mydiary.application.manager;

import com.matteoveroni.mydiary.patterns.Listener;
import com.matteoveroni.mydiary.screen.ScreenType;
import com.matteoveroni.mydiary.user.model.User;

/**
 *
 * @author Matteo Veroni
 */
public interface Manager {

	public void registerListener(Listener listener);

	public void removeListener(Listener listener);

	public void notifyListeners();

	public void changeScreen(ScreenType screenType);

	public User getLoggedUser();

	public void setCurrentUser(User loggedInUser);

	public void dispose();
}
