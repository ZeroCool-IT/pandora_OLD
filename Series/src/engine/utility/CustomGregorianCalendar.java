/**
 * File engine.utility/CustomGregorianCalendar.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */

package engine.utility;

import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Class extending GregorianCalendar for toString() overriding
 * 
 * @author Marco Battisti
 * 
 */
public class CustomGregorianCalendar extends GregorianCalendar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1215736953147074442L;

	public CustomGregorianCalendar() {
		super();
	}

	public CustomGregorianCalendar(Locale locale) {
		super(locale);
		// TODO Auto-generated constructor stub
	}

	public CustomGregorianCalendar(TimeZone timezone) {
		super(timezone);
		// TODO Auto-generated constructor stub
	}

	public CustomGregorianCalendar(TimeZone timezone, Locale locale) {
		super(timezone, locale);
		// TODO Auto-generated constructor stub
	}

	public CustomGregorianCalendar(int year, int month, int day) {
		super(year, month, day);
		// TODO Auto-generated constructor stub
	}

	public CustomGregorianCalendar(int year, int month, int day, int hour,
			int minute) {
		super(year, month, day, hour, minute);
		// TODO Auto-generated constructor stub
	}

	public CustomGregorianCalendar(int year, int month, int day, int hour,
			int minute, int second) {
		super(year, month, day, hour, minute, second);
		// TODO Auto-generated constructor stub
	}

	public String toString() {
		return this.get(GregorianCalendar.YEAR) + "-"
				+ (Integer.valueOf(this.get(GregorianCalendar.MONTH) + 1))
				+ "-" + this.get(GregorianCalendar.DAY_OF_MONTH);

	}

}
