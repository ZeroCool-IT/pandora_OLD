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
	
	protected int id;
	protected LinkedList<GregorianCalendar> days;
	
	

	/**
	 * Public constructor
	 */
	public TimeCard(int id) {
		this.id = id;
		days = new LinkedList<GregorianCalendar>();
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	public LinkedList<GregorianCalendar> getDays() {
		return days;
	}


	public void setDays(LinkedList<GregorianCalendar> days) {
		this.days = days;
	}

}
