/**
 * File model/Language.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */

package model;

import java.io.Serializable;

/**
 * Model class representing person
 * 
 * @author Marco Battisti
 */
public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -458479188042299477L;
	protected String name;
	protected String pic;
	private static final String NULL_VALUE = "null";

	/**
	 * Public constructor for persons
	 * 
	 * @param name
	 *            is the name
	 */
	public Person(String name) {
		this.name = name;

	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param firstName
	 *            the name to set
	 */
	public void setFirstName(String name) {
		this.name = name;
	}

	/**
	 * @return the picture
	 */
	public String getPic() {
		if (pic != null && !pic.equals(NULL_VALUE)) {
			return pic;
		} else
			return null;
	}

	/**
	 * @param pic
	 *            is the picture representing the Person
	 */
	public void setPic(String pic) {
		this.pic = pic;
	}

	/**
	 * @return A string representing person
	 */
	public String toString() {
		return name;
	}

}
