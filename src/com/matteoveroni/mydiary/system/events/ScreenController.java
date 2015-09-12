package com.matteoveroni.mydiary.system.events;

import com.matteoveroni.mydiary.application.manager.Manager;

/**
 *
 * @author Matteo Veroni
 */
public interface ScreenController {

    public void activateScreen(Manager manager);

    public String getControlledScreenName();

    public void setActions();

    public void removeAction();

    public void impostaInputProcessor();
}
