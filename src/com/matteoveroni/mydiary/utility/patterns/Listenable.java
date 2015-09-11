package com.matteoveroni.mydiary.utility.patterns;

/**
 *
 * @author Matteo Veroni
 */
public interface Listenable {
	public void registerListener(Listener listener);
	public void removeListener(Listener listener);
	public void notifyListeners();
}
