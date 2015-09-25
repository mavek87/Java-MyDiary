package com.matteoveroni.mydiary.utilities.formatters;

import com.matteoveroni.mydiary.utilities.converters.StackTraceToStringConverter;

/**
 *
 * @author Matteo Veroni
 */
public class ExceptionsFormatter {

    public static final String toString(Exception ex) {
        return "ex_message: " + ex.getMessage() + " | ex_stack_trace: " + StackTraceToStringConverter.convertToString(ex);
    }
}
