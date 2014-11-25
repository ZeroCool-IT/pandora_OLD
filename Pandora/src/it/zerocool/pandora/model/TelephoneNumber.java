/**
 * File: it.zerocool.pandora.model/TelephoneNumber.java
 * @author Marco Battisti
 *
 */

package it.zerocool.pandora.model;
/**
 * Class representing phone numbers
 * @author Marco
 *
 */
public class TelephoneNumber {

	private String intPrefix;
	private String prefix;
	private String number;
	
	/**
	 * Public constructor of a phone number
	 * @param intPrefix is the international prefix
	 * @param prefix is the regional prefix
	 * @param number is the phone number
	 */
	public TelephoneNumber(String intPrefix, String prefix, String number) {
		this.intPrefix = intPrefix;
		this.prefix = prefix;
		this.number = number;
	}

	/**
	 * @return the International Prefix
	 */
	public String getIntPrefix() {
		return intPrefix;
	}

	/**
	 * @param intPrefix the International Prefix to set
	 */
	public void setIntPrefix(String intPrefix) {
		this.intPrefix = intPrefix;
	}

	/**
	 * @return the regional prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix the regional prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return the phone number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the phone number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	
	/**
	 * Phone number String representation
	 */
	public String toString() {
		return intPrefix + "-" + prefix + "-" + number; 
	}

}
