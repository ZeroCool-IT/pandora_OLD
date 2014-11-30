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
	
	protected LinkedList<GregorianCalendar> days;
	

	/**
	 * Public constructor
	 */
	public TimeCard() {
		days = new LinkedList<GregorianCalendar>();
	}


	public LinkedList<GregorianCalendar> getDays() {
		return days;
	}


	public void setDays(LinkedList<GregorianCalendar> days) {
		this.days = days;
	}

}
