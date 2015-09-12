package com.matteoveroni.mydiary.utilities.patterns;

import com.matteoveroni.mydiary.application.manager.DataObjectMessage;

/**
 *
 * @author Matteo Veroni
 */
public interface Listenable {
	public void registerListener(Listener listener);
	public void removeListener(Listener listener);
	public void notifyListeners(DataObjectMessage objectToPush);
}
