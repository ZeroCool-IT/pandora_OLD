/**
 * Project: Pandora
 * File it.zerocool.pandora.utility/ParsingUtilities.java
 * @author Marco Battisti
 */
package it.zerocool.pandora.utility;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.util.Log;
import it.zerocool.pandora.model.*;
import it.zerocool.pandora.utility.Constraints;

/**
 * Utility class for data parsing
 * @author Marco Battisti
 *
 */
public class ParsingUtilities {
	
	/**
	 * Parse a String representing date (YYYY-mm-DD format) filling a
	 * GregorianCalendar object
	 * 
	 * @param s is the string to parse
	 * @param g is the GregorianCalendar object to fill
	 */
	public static GregorianCalendar dateFromString(String d, String h) {
		if (d != null && h != null) {
			GregorianCalendar g = new GregorianCalendar();
			parseDate(d, g);
			parseHour(h, g);
			return g;
		}
		else
			return null;
		
	}
	
	public static void parseDate(String d, GregorianCalendar g) {
		if (d != null) {
			StringTokenizer tokenizer = new StringTokenizer(d, "-");
			while (tokenizer.hasMoreTokens()) {
				String toSet = tokenizer.nextToken();
				g.set(GregorianCalendar.YEAR, Integer.parseInt(toSet));
				toSet = tokenizer.nextToken();
				g.set(GregorianCalendar.MONTH, Integer.parseInt(toSet)-1);
				tokenizer.nextToken();
				g.set(GregorianCalendar.DAY_OF_MONTH, Integer.parseInt(toSet));
			}
		}
	}
	
	public static void parseHour(String h, GregorianCalendar g) {
		StringTokenizer tokenizer = new StringTokenizer(h, ":");
		while (tokenizer.hasMoreTokens()) {
			String toSet = tokenizer.nextToken();
			g.set(GregorianCalendar.HOUR_OF_DAY, Integer.parseInt(toSet));
			toSet = tokenizer.nextToken();
			g.set(GregorianCalendar.MINUTE, Integer.parseInt(toSet));
		}
	}
	
	public static ArrayList<Place> parsePlaceFromJSON(String json) {
		ArrayList<Place> result = new ArrayList<Place>();
		try {
			JSONObject reader = new JSONObject(json);
			JSONArray data = reader.getJSONArray("LUOGHI");
			if (data != null) {
				for (int i = 0; i < data.length(); i++) {
					JSONObject toBuild = data.getJSONObject(i);
					int type = Integer.parseInt(toBuild.getString("TYPE"));
					int id = Integer.parseInt((toBuild.getString("LUOGO_ID")));
					Place p = null;
					switch (type) {
					case Constraints.TYPE_TOSEE:
						p = new ToSee(id);
						break;
					case Constraints.TYPE_EAT:
						p = new Eat(id);
						break;
					case Constraints.TYPE_SLEEP:
						p = new Sleep(id);
						break;
					case Constraints.TYPE_SERVICE:
						p = new Service(id);
						break;
					case Constraints.TYPE_SHOP:
						p = new Shop(id);
						break;
					default:
						break;
					}
					p.setName(toBuild.getString("NAME"));
					p.setDescription("DESCRIPTION");
					p.setImage(toBuild.getString("IMAGE"));
					p.setTagsFromCSV(toBuild.getString("TAGS"));
					ContactCard c = new ContactCard();
					c.setAddress(toBuild.getString("ADDRESS"));
					c.setEmail(toBuild.getString("EMAIL"));
					c.setUrl(toBuild.getString("URL"));
					c.setTelephone(toBuild.getString("TELEPHONENUMBER"));
					p.setContact(c);
					Location l = new Location("");
					String latitude = toBuild.getString("LATITUDE");
					String longitude = toBuild.getString("LONGITUDE");
					l.setLatitude(Location.convert(latitude));
					l.setLongitude(Location.convert(longitude));
					p.setLocation(l);
					//TO DO TimeCard
					result.add(p);
					
					
					
				}
			}
			return result;
			
		} catch (JSONException e) {
			Log.e("JSON Exception", e.getMessage());
		}
		return result;
		
	}

	/**
	 * There isn't a public constructor, it's an utility class!
	 */
	private ParsingUtilities() {
		// TODO Auto-generated constructor stub
	}

}
