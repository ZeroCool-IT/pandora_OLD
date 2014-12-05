/**
 * Project: Pandora
 * File: it.zerocool.pandora.model/Event
 * @author Marco Battisti
 */
package it.zerocool.pandora.model;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.StringTokenizer;

import android.location.Location;

/**
 * Public class representing city's events
 * @author Marco Battisti
 */
public class Event {
	
	protected int id;
	protected String name;
	protected GregorianCalendar date;
	protected GregorianCalendar until;
	protected String image;
	protected LinkedList<String> tags;
	protected String description;
	protected Location location;
	protected ContactCard contact;
	
	
	/**
	 * Public constructor
	 * @param id is the unique id for the event
	 */
	public Event(int id) {
		this.id = id;
		this.tags = new LinkedList<String>();
		date = new GregorianCalendar();
		until = new GregorianCalendar();
	}


	/**
	 * @return the id of the event
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
	 * @return the name of the event
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name of the event to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the date of the event
	 */
	public GregorianCalendar getDate() {
		return date;
	}


	/**
	 * @param date the date of the event to set
	 */
	public void setDate(GregorianCalendar date) {
		this.date = date;
	}


	/**
	 * @return the until
	 */
	public GregorianCalendar getUntil() {
		return until;
	}


	/**
	 * @param until the until to set
	 */
	public void setUntil(GregorianCalendar until) {
		this.until = until;
	}


	/**
	 * @return the image of the event
	 */
	public String getImage() {
		return image;
	}


	/**
	 * @param image the image  of the event to set
	 */
	public void setImage(String image) {
		this.image = image;
	}


	/**
	 * @return the tags list of the event
	 */
	public LinkedList<String> getTags() {
		return tags;
	}


	/**
	 * @param tags the tags list of the event to set
	 */
	public void setTags(LinkedList<String> tags) {
		this.tags = tags;
	}
	
	/**
	 * Add the tags to tags' list from a string in CSV format
	 * @param t is the string in CSV format
	 */
	public void setTagsFromCSV(String csv) {
		if (csv != null) {
			StringTokenizer tokenizer = new StringTokenizer(csv, ",");
			while (tokenizer.hasMoreTokens()) {
				String toAdd = tokenizer.nextToken();
				getTags().add(toAdd);
			}
		}
	}


	/**
	 * @return the description of the event
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description of the event to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the location of the event 
	 */
	public Location getLocation() {
		return location;
	}


	/**
	 * @param location the location of the event to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}


	/**
	 * @return the contact card of the event
	 */
	public ContactCard getContact() {
		return contact;
	}


	/**
	 * @param contact the contact card of the event to set
	 */
	public void setContact(ContactCard contact) {
		this.contact = contact;
	}
	
	//REVIEW
	public void setDateFromString(GregorianCalendar gc, String d) {
		if (d != null) {
			StringTokenizer tokenizer = new StringTokenizer(d, "-");
			while (tokenizer.hasMoreTokens()) {
				String toSet = tokenizer.nextToken();
				gc.set(GregorianCalendar.YEAR, Integer.parseInt(toSet));
				toSet = tokenizer.nextToken();
				gc.set(GregorianCalendar.MONTH, Integer.parseInt(toSet)-1);
				tokenizer.nextToken();
				gc.set(GregorianCalendar.DAY_OF_MONTH, Integer.parseInt(toSet));
			}	
		}
		
	}
	
	
}
