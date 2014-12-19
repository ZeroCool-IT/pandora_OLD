/**
 * Project: Pandora
 * File: it.zerocool.pandora.model/TimeCard.java
 * @author Marco Battisti
 */
package it.zerocool.pandora.model;

import java.util.GregorianCalendar;

/**
 * Public class representing opening time and days
 * @author Marco Battisti
 *
 */
public class TimeCard {
	
	protected int id;
	protected GregorianCalendar date;
	protected GregorianCalendar fromAM;
	protected GregorianCalendar toAM;
	protected GregorianCalendar fromPM;
	protected GregorianCalendar toPM;
	

	/**
	 * Public constructor
	 */
	public TimeCard(int id) {
		this.id = id;
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


	/**
	 * @return the date of opening
	 */
	public GregorianCalendar getDate() {
		return date;
	}


	/**
	 * @param date the date of opening to set
	 */
	public void setDate(GregorianCalendar date) {
		this.date = date;
	}


	/**
	 * @return the AM opening hour
	 */
	public GregorianCalendar getFromAM() {
		return fromAM;
	}


	/**
	 * @param fromAM the AM opening hour to set
	 */
	public void setFromAM(GregorianCalendar fromAM) {
		this.fromAM = fromAM;
	}


	/**
	 * @return the AM closing hour
	 */
	public GregorianCalendar getToAM() {
		return toAM;
	}


	/**
	 * @param toAM the AM closing hour to set
	 */
	public void setToAM(GregorianCalendar toAM) {
		this.toAM = toAM;
	}


	/**
	 * @return the PM opening hour
	 */
	public GregorianCalendar getFromPM() {
		return fromPM;
	}


	/**
	 * @param fromPM the PM opening hour to set
	 */
	public void setFromPM(GregorianCalendar fromPM) {
		this.fromPM = fromPM;
	}


	/**
	 * @return the PM closing hour
	 */
	public GregorianCalendar getToPM() {
		return toPM;
	}


	/**
	 * @param toPM the PM closing hour to set
	 */
	public void setToPM(GregorianCalendar toPM) {
		this.toPM = toPM;
	}
	
	


	
}
