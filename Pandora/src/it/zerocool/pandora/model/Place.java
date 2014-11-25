/**
 * File it.zerocool.pandora.model/Place.java
 * @author Marco Battisti
 */
package it.zerocool.pandora.model;

import java.util.LinkedList;

/**
 * Class representing city's places
 * @author Marco Battisti
 *
 */
public class Place {

	protected long id;
	protected String name;
	protected String image;
	protected LinkedList<String> tags;
	protected String description;
	protected ContactCard contact;
	
	
	/**
	 * Public constructor
	 */
	public Place(long id) {
		this.id = id;
	}



	/**
	 * @return the id
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}



	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}



	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}



	/**
	 * @return the tags
	 */
	public LinkedList<String> getTags() {
		return tags;
	}



	/**
	 * @param tags the tags to set
	 */
	public void setTags(LinkedList<String> tags) {
		this.tags = tags;
	}



	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}



	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}



	/**
	 * @return the contact
	 */
	public ContactCard getContact() {
		return contact;
	}



	/**
	 * @param contact the contact to set
	 */
	public void setContact(ContactCard contact) {
		this.contact = contact;
	}
	
	

}
