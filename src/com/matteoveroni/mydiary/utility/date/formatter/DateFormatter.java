package com.matteoveroni.mydiary.utility.date.formatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Matteo Veroni
 */
public class DateFormatter {
	
	private final static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private final static DateFormat timestampFormat = new SimpleDateFormat("dd/MM/yyyy | HH:mm:ss ");
	
	public final static String dateFormat(Date dateToFormat) {
		return dateFormat.format(dateToFormat);
	}
	
	public final static String timestampFormat(Date timestampToFormat) {
		return timestampFormat.format(timestampToFormat);
	}
}
