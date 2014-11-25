/**
 * Project: Pandora
 * File: it.zerocool.pandora.model/TimeCard.java
 * @author Marco Battisti
 */
package it.zerocool.pandora.model;

import java.util.GregorianCalendar;
import java.util.LinkedList;

/**
 * Public class representing opening time and days
 * @author Marco Battisti
 *
 */
public class TimeCard {
	
	protected GregorianCalendar openFrom;
	protected GregorianCalendar openTo;
	protected LinkedList<GregorianCalendar> days;
	

	/**
	 * Public constructor
	 */
	public TimeCard() {
		// TODO Auto-generated constructor stub
	}

}
