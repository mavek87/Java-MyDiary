package com.matteoveroni.mydiary.utilities.formatters;

import com.matteoveroni.mydiary.utilities.converters.StackTraceToStringConverter;

/**
 *
 * @author Matteo Veroni
 */
public class ExceptionsFormatter {

    public static final String toString(Exception ex) {
        return "EX_MESSAGE: " + ex.getMessage() + " | EX_STACK_TRACE: " + StackTraceToStringConverter.convertToString(ex);
    }
}
