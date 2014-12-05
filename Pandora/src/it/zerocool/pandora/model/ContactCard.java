/**
 * Project: Pandora
 * File: it.zerocool.pandora.model/ContactCard.java
 * @author Marco Battisti
 */
package it.zerocool.pandora.model;

/**
 * Class representing places' contact info
 * @author Marco
 *
 */
public class ContactCard {
	
	protected int id;
	protected String address;
	protected TelephoneNumber telephone;
	protected String email;
	protected String url;

	/**
	 * Public constructor
	 */
	public ContactCard(int id) {
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
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the telephone
	 */
	public TelephoneNumber getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(TelephoneNumber telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
