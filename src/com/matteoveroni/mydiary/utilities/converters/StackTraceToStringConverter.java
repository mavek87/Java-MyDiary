package com.matteoveroni.mydiary.utilities.converters;


import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author Matteo Veroni
 */
public class StackTraceToStringConverter {

    public static final String convertToString(Exception ex) {
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }
}
