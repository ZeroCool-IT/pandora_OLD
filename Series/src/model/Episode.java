/**
 * File model/Episode.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */

package model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import engine.utility.CustomGregorianCalendar;
import android.text.TextUtils;
import android.util.Log;

/**
 * Model class representing tv series episode
 * 
 * @author Marco Battisti
 * 
 */
public class Episode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7249133668456925821L;

	private static final String NULL_VALUE = "null";
	private static final String NOT_AVAILABLE = "N/A";
	private static final String EMPTY_VALUE = "";

	protected String id;
	protected String seasonNumber;
	protected String episodeNumber;
	protected String episodeName;
	protected CustomGregorianCalendar firstAired;
	protected String overview;
	protected String productionCode;
	protected String rating;
	protected String image;
	protected String imdb_id;
	protected ArrayList<Actor> guestStars;
	protected ArrayList<Director> director;
	protected ArrayList<Writer> writers;

	/**
	 * @param id
	 *            is the episode id from DB
	 */
	public Episode(String id) {
		this.id = id;
		this.guestStars = new ArrayList<Actor>();
		this.director = new ArrayList<Director>();
		this.writers = new ArrayList<Writer>();
	}

	/**
	 * @return the the episode id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the episode season number
	 */
	public String getSeasonNumber() {
		return seasonNumber;
	}

	/**
	 * @param seasonNumber
	 *            the season number to set
	 */
	public void setSeasonNumber(String seasonNumber) {
		this.seasonNumber = seasonNumber;
	}

	/**
	 * @return the episode number in the season if not null, N/A otherwise
	 */
	public String getEpisodeNumber() {
		if (episodeNumber != null && !episodeNumber.equals(NULL_VALUE)
				&& !episodeNumber.equals(EMPTY_VALUE)) {
			return episodeNumber;
		} else
			return NOT_AVAILABLE;
	}

	/**
	 * @param episodeNumber
	 *            the episode number to set
	 */
	public void setEpisodeNumber(String episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	/**
	 * @return the episode name if not null, N/A otherwise
	 */
	public String getEpisodeName() {
		if (episodeName != null && !episodeName.equals(NULL_VALUE)
				&& !episodeName.equals(EMPTY_VALUE)) {
			return episodeName;
		} else
			return NOT_AVAILABLE;
	}

	/**
	 * @param episodeName
	 *            the episode name to set
	 */
	public void setEpisodeName(String episodeName) {
		this.episodeName = episodeName;
	}

	/**
	 * @return the date of episode first airing
	 */
	public CustomGregorianCalendar getFirstAired() {
		return firstAired;
	}

	/**
	 * @param firstAired
	 *            the date to set
	 */
	public void setFirstAired(CustomGregorianCalendar firstAired) {
		this.firstAired = firstAired;
	}

	/**
	 * @param firstAired
	 *            the date to set
	 */
	public void setFirstAired(String firstAired) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = df.parse(firstAired);
			CustomGregorianCalendar cal = new CustomGregorianCalendar();
			cal.setTime(date);
			setFirstAired(cal);
		} catch (ParseException e) {
			this.firstAired = null;
			Log.w("DATE PARSE ERROR", e.getMessage());

		} catch (NullPointerException e) {
			this.firstAired = null;
			Log.w("DATE NULL ERROR", "Date null");
		}
	}

	/**
	 * Return the field in cache compliant format
	 * 
	 * @return field in cache compliant format
	 */
	public String cacheFirstAired() {
		if (firstAired != null) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.format(firstAired.getTime());
		} else
			return NULL_VALUE;
	}

	/**
	 * @return the overview of the episode if not null, N/A otherwise
	 */
	public String getOverview() {
		if (overview != null && !overview.equals(NULL_VALUE)
				&& !overview.equals(EMPTY_VALUE)) {
			return overview;
		} else
			return NOT_AVAILABLE;
	}

	/**
	 * @param overview
	 *            the overview to set
	 */
	public void setOverview(String overview) {
		this.overview = overview;
	}

	/**
	 * @return the episode production code if not null, N/A otherwise
	 */
	public String getProductionCode() {
		if (productionCode != null && !productionCode.equals(NULL_VALUE)
				&& !productionCode.equals(EMPTY_VALUE)) {
			return productionCode;
		} else
			return NOT_AVAILABLE;
	}

	/**
	 * @param productionCode
	 *            the production code to set
	 */
	public void setProductionCode(String productionCode) {
		this.productionCode = productionCode;
	}

	/**
	 * @return the rating from the audience if not null, N/A otherwise
	 */
	public String getRating() {
		if (rating != null && !rating.equals(NULL_VALUE)
				&& !rating.equals(EMPTY_VALUE)) {
			return rating;
		} else
			return NOT_AVAILABLE;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}

	/**
	 * @return the episode image link
	 */
	public String getImage() {
		if (image != null && !image.equals(NULL_VALUE)
				&& !image.equals(EMPTY_VALUE)) {
			return image;
		} else
			return null;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the id on the IMDB database
	 */
	public String getImdb_id() {
		if (imdb_id != null && !imdb_id.equals(NULL_VALUE)
				&& !imdb_id.equals(EMPTY_VALUE)) {
			return imdb_id;
		} else
			return null;
	}

	/**
	 * @param imdb_id
	 *            the imdb_id to set
	 */
	public void setImdb_id(String imdb_id) {
		this.imdb_id = imdb_id;
	}

	/**
	 * @return the list of guest stars
	 */
	public ArrayList<Actor> getGuestStars() {
		return guestStars;
	}

	/**
	 * @return the String representing the guest stars list if not null, empty
	 *         value otherwise
	 */
	public String getGuestStarsToString() {
		if (guestStars != null && !guestStars.isEmpty()) {
			String result = null;
			result = TextUtils.join(", ", guestStars);
			return result;
		} else
			return EMPTY_VALUE;
	}

	/**
	 * @param guestStars
	 *            the list of guest stars to set
	 */
	public void setGuestStars(ArrayList<Actor> guestStars) {
		this.guestStars = guestStars;
	}

	/**
	 * Return the field in cache compliant format
	 * 
	 * @return field in cache compliant format
	 */
	public String cacheGuestStars() {
		if (guestStars != null && !guestStars.isEmpty()) {
			String result = null;
			result = TextUtils.join("|", guestStars);
			return result;
		} else
			return EMPTY_VALUE;
	}

	/**
	 * @return the director of episode
	 */
	public ArrayList<Director> getDirector() {
		return director;
	}

	/**
	 * @return the String representing the writers list if not null, N/A
	 *         otherwise
	 */
	public String getDirectorToString() {
		if (director != null && !director.isEmpty()) {
			String result = null;
			result = TextUtils.join(", ", director);
			return result;
		} else
			return NOT_AVAILABLE;
	}

	/**
	 * Return the field in cache compliant format
	 * 
	 * @return field in cache compliant format
	 */
	public String cacheDirector() {
		if (director != null && !director.isEmpty()) {
			String result = null;
			result = TextUtils.join("|", director);
			return result;
		} else
			return NOT_AVAILABLE;
	}

	/**
	 * @param director
	 *            the director to set
	 */
	public void setDirector(ArrayList<Director> director) {
		this.director = director;
	}

	/**
	 * @return the writers' list of episode
	 */
	public ArrayList<Writer> getWriters() {
		return writers;
	}

	/**
	 * @return the String representing the writers list in csv if not empty, N/A
	 *         otherwise
	 */
	public String getWritersToString() {
		if (writers != null && !writers.isEmpty()) {
			String result = null;
			result = TextUtils.join(", ", writers);
			return result;
		} else
			return NOT_AVAILABLE;
	}

	/**
	 * Return the field in cache compliant format
	 * 
	 * @return field in cache compliant format
	 */
	public String cacheWriters() {
		if (writers != null && !writers.isEmpty()) {
			String result = null;
			result = TextUtils.join("|", writers);
			return result;
		} else
			return NOT_AVAILABLE;
	}

	/**
	 * @param writers
	 *            the writers to set
	 */
	public void setWriters(ArrayList<Writer> writers) {
		this.writers = writers;
	}

	public String toString() {
		return Integer.valueOf(episodeNumber).toString();
	}

	/**
	 * Add the writers to the list starting from a String using a
	 * StringTokenizer
	 * 
	 * @param cast
	 *            is the String representing episode writers
	 */
	public void addWritersFromString(String writers) {
		if (writers != null) {
			StringTokenizer tokenizer = new StringTokenizer(writers, "|");
			while (tokenizer.hasMoreTokens()) {
				String t = tokenizer.nextToken();
				if (!t.equals(NULL_VALUE)) {
					Writer a = new Writer(t);
					getWriters().add(a);
				}
			}
		}

	}

	/**
	 * Add the writers to the list starting from a String using a
	 * StringTokenizer
	 * 
	 * @param cast
	 *            is the String representing episode writers
	 */
	public void addGuestStarsFromString(String guestStars) {
		if (writers != null) {
			StringTokenizer tokenizer = new StringTokenizer(guestStars, "|");
			while (tokenizer.hasMoreTokens()) {
				String t = tokenizer.nextToken();
				if (!t.equals(NULL_VALUE)) {
					Actor a = new Actor(t);
					getGuestStars().add(a);
				}
			}
		}

	}

	/**
	 * Add the writers to the list starting from a String using a
	 * StringTokenizer
	 * 
	 * @param cast
	 *            is the String representing episode writers
	 */
	public void addDirectorFromString(String directors) {
		if (writers != null) {
			StringTokenizer tokenizer = new StringTokenizer(directors, "|");
			while (tokenizer.hasMoreTokens()) {
				String t = tokenizer.nextToken();
				if (!t.equals(NULL_VALUE)) {
					Director a = new Director(t);
					getDirector().add(a);
				}
			}
		}

	}

}
