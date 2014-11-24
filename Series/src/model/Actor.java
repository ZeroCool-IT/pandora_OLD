/**
 * File model/Actor.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */
package model;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.text.TextUtils;

/**
 * Model class representing actors extending Person
 * 
 * @author Marco Battisti
 * 
 */
public class Actor extends Person {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6935787693061191489L;
	private static final String NULL_VALUE = "null";
	private static final String NOT_AVAILABLE = "N/A";

	private String role;

	public Actor(String name) {
		super(name);
	}

	/**
	 * @return the role of the actor
	 */
	public String getRole() {
		String result = null;
		ArrayList<String> list = new ArrayList<String>();
		if (role != null && !role.equals(NULL_VALUE)) {
			StringTokenizer tokenizer = new StringTokenizer(role, "|");
			while (tokenizer.hasMoreTokens()) {
				String s = tokenizer.nextToken();
				list.add(s);
			}
			result = TextUtils.join(", ", list);
			return result;
		} else
			return NOT_AVAILABLE;
	}

	/**
	 * @param role
	 *            the role of the actor to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

}
