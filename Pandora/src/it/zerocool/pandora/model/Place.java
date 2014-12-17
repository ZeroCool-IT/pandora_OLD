/**
 * Project: Pandora
 * File it.zerocool.pandora.model/Place.java
 * @author Marco Battisti
 */
package it.zerocool.pandora.model;

import java.util.LinkedList;
import java.util.StringTokenizer;

import android.location.Location;

/**
 * Class representing city's places
 * @author Marco Battisti
 *
 */
public class Place {

	protected int id;
	protected String name;
	protected String image;
	protected String fsqrLink;
	protected LinkedList<String> tags;
	protected String description;
	protected ContactCard contact;
	protected TimeCard timeCard;
	protected Location location;
	
	
	/**
	 * Public constructor
	 */
	public Place(int id) {
		this.id = id;
		tags =  new LinkedList<String>();
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
	 * @return the name of the place
	 */
	public String getName() {
		return name;
	}



	/**
	 * @param name the name  of the place to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	/**
	 * @return the image of the place
	 */
	public String getImage() {
		return image;
	}



	/**
	 * @param image the image of the place to set
	 */
	public void setImage(String image) {
		this.image = image;
	}



	/**
	 * @return the link to 4square
	 */
	public String getFsqrLink() {
		return fsqrLink;
	}



	/**
	 * @param fsqrLink the 4square link to set
	 */
	public void setFsqrLink(String fsqrLink) {
		this.fsqrLink = fsqrLink;
	}



	/**
	 * @return the tags list
	 */
	public LinkedList<String> getTags() {
		return tags;
	}



	/**
	 * @param tags the tags list to set
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
				toAdd = toAdd.trim();
				getTags().add(toAdd);
			}
		}
	}



	/**
	 * @return the description of the place
	 */
	public String getDescription() {
		return description;
	}



	/**
	 * @param description the description of the place to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}



	/**
	 * @return the contact card of the place
	 */
	public ContactCard getContact() {
		return contact;
	}



	/**
	 * @param contact the contact card of the place to set
	 */
	public void setContact(ContactCard contact) {
		this.contact = contact;
	}



	/**
	 * @return the Time Card representing opening time of the place
	 */
	public TimeCard getTimeCard() {
		return timeCard;
	}



	/**
	 * @param openingTime the Time Card to set
	 */
	public void setTimeCard(TimeCard openingTime) {
		this.timeCard = openingTime;
	}



	public Location getLocation() {
		return location;
	}



	public void setLocation(Location location) {
		this.location = location;
	}

}
