package com.matteoveroni.mydiary.application.manager;

import com.matteoveroni.mydiary.application.messages.DataObjectMessage;
import com.matteoveroni.mydiary.screen.Screen;
import com.matteoveroni.mydiary.utilities.patterns.Listener;
import com.matteoveroni.mydiary.screen.framework.ScreensFramework;
import com.matteoveroni.mydiary.user.model.bean.UserData;

/**
 *
 * @author Matteo Veroni
 */
public interface Manager {

    public void registerListener(Listener listener);

    public void removeListener(Listener listener);

    public void notifyListeners(DataObjectMessage dataToPush);

    public void changeScreen(ScreensFramework screenType);
	
	public boolean usePreviousScreen();

    public Screen getCurrentScreen();

    public UserData getLoggedInUser();
    
    public String getApplicationName();
        
    public String getApplicationVersion();
    
    public String getApplicationTitle();
    
    public void setLoggedInUser(UserData loggedInUser);

    public void storeObjectToPush(Object dataToPush, Class senderClass);

    public void dispose();
}
