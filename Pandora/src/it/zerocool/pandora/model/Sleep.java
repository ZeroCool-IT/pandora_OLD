/**
 * Project: Pandora
 * File: it.zerocool.pandora.model/Sleep.java
 * @author Marco Battisti
 */
package it.zerocool.pandora.model;

/**
 * Public class representing sleeping places
 * @author Marco Battisti
 *
 */
public class Sleep extends Place {
	
	protected int stars;

	/**
	 * Public constructor
	 * @param id is the id of the place
	 */
	public Sleep(long id) {
		super(id);
		stars = 0;
	}

	/**
	 * @return the stars
	 */
	public int getStars() {
		return stars;
	}

	/**
	 * @param stars the stars to set
	 */
	public void setStars(int stars) {
		this.stars = stars;
	}
}
