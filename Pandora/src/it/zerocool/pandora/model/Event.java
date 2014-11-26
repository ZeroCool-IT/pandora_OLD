/**
 * Project: Pandora
 * File: it.zerocool.pandora.model/Event
 * @author Marco Battisti
 */
package it.zerocool.pandora.model;

import java.util.GregorianCalendar;
import java.util.LinkedList;

import android.location.Location;

/**
 * Public class representing city's events
 * @author Marco Battisti
 */
public class Event {
	
	protected long id;
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
	public Event(long id) {
		this.id = id;
		this.tags = new LinkedList<String>();
	}


	/**
	 * @return the id of the event
	 */
	public long getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
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
	
	
}
