package com.matteoveroni.mydiary.utilities.patterns;

import com.matteoveroni.mydiary.application.messages.DataObjectMessage;

/**
 *
 * @author Matteo Veroni
 */
public interface Listener {
	public void update(DataObjectMessage pushedObject);
}
