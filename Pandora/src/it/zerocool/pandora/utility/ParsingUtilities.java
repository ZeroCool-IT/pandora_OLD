/**
 * Project: Pandora
 * File it.zerocool.pandora.utility/ParsingUtilities.java
 * @author Marco Battisti
 */
package it.zerocool.pandora.utility;

import java.util.GregorianCalendar;
import java.util.StringTokenizer;

/**
 * Utility class for data parsing
 * @author Marco Battisti
 *
 */
public class ParsingUtilities {
	
	/**
	 * Parse a String representing date (YYYY-mm-DD format) filling a
	 * GregorianCalendar object
	 * 
	 * @param s is the string to parse
	 * @param g is the GregorianCalendar object to fill
	 */
	public static GregorianCalendar dateFromString(String d, String h) {
		if (d != null && h != null) {
			GregorianCalendar g = new GregorianCalendar();
			parseDate(d, g);
			parseHour(h, g);
			return g;
		}
		else
			return null;
		
	}
	
	public static void parseDate(String d, GregorianCalendar g) {
		if (d != null) {
			StringTokenizer tokenizer = new StringTokenizer(d, "-");
			while (tokenizer.hasMoreTokens()) {
				String toSet = tokenizer.nextToken();
				g.set(GregorianCalendar.YEAR, Integer.parseInt(toSet));
				toSet = tokenizer.nextToken();
				g.set(GregorianCalendar.MONTH, Integer.parseInt(toSet)-1);
				tokenizer.nextToken();
				g.set(GregorianCalendar.DAY_OF_MONTH, Integer.parseInt(toSet));
			}
		}
	}
	
	public static void parseHour(String h, GregorianCalendar g) {
		StringTokenizer tokenizer = new StringTokenizer(h, ":");
		while (tokenizer.hasMoreTokens()) {
			String toSet = tokenizer.nextToken();
			g.set(GregorianCalendar.HOUR_OF_DAY, Integer.parseInt(toSet));
			toSet = tokenizer.nextToken();
			g.set(GregorianCalendar.MINUTE, Integer.parseInt(toSet));
		}
	}

	/**
	 * There isn't a public constructor, it's an utility class!
	 */
	private ParsingUtilities() {
		// TODO Auto-generated constructor stub
	}

}
