package com.matteoveroni.mydiary.exceptions;

import com.matteoveroni.mydiary.application.manager.Manager;

/**
 *
 * @author Matteo Veroni
 */
public class CriticalRuntimeException extends RuntimeException {

    public CriticalRuntimeException(Exception ex, Manager manager) {
        super(ex);
        manager.dispose();
    }
}
