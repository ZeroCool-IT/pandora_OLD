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
	public TimeCard(GregorianCalendar openFrom, GregorianCalendar openTo) {
		this.openFrom = openFrom;
		this.openTo = openTo;
		days = new LinkedList<GregorianCalendar>();
	}


	public GregorianCalendar getOpenFrom() {
		return openFrom;
	}


	public void setOpenFrom(GregorianCalendar openFrom) {
		this.openFrom = openFrom;
	}


	public GregorianCalendar getOpenTo() {
		return openTo;
	}


	public void setOpenTo(GregorianCalendar openTo) {
		this.openTo = openTo;
	}


	public LinkedList<GregorianCalendar> getDays() {
		return days;
	}


	public void setDays(LinkedList<GregorianCalendar> days) {
		this.days = days;
	}

}
