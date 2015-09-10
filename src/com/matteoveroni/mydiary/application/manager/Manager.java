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

	public User getLoggedInUser();

	public void setLoggedInUser(User loggedInUser);
    
	public void dispose();
}
