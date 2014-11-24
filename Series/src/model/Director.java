/**
 * File model/Language.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */
package model;

/**
 * Model class representing directors extending Person
 * 
 * @author Marco
 * 
 */
public class Director extends Person {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8934373712203248559L;

	/**
	 * @param firstName
	 *            is the first name
	 * @param middleName
	 *            is the middle name
	 * @param lastName
	 *            is the last name
	 */
	public Director(String name) {
		super(name);
	}

}
