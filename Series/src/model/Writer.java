/**
 * File model/Writer.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */

package model;

/**
 * Model class representing writers extending Person
 * 
 * @author Marco
 * 
 */
public class Writer extends Person {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7443513219879753163L;

	/**
	 * @param firstName
	 *            is the first name
	 * @param middleName
	 *            is the middle name
	 * @param lastName
	 *            is the last name
	 */
	public Writer(String name) {
		super(name);
	}
}
