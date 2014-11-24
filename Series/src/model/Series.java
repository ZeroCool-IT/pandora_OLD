/**
 * File model/Series.java
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
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

import engine.utility.CustomGregorianCalendar;
import android.text.TextUtils;
import android.util.Log;

/**
 * Model class representing TV Series
 * 
 * @author Marco Battisti
 */
public class Series implements Serializable {

	private static final String NULL_VALUE = "null";
	private static final String EMPTY_VALUE = "";
	private static final String NOT_AVAILABLE = "N/A";

	/**
	 * 
	 */
	private static final long serialVersionUID = -4202479136558327834L;
	protected String id;
	protected String seriesName;
	protected String genre;
	protected CustomGregorianCalendar firstAired;
	protected String contentRating;
	protected String overview;
	protected String network;
	protected String rating;
	protected String status;
	protected String runtime;
	protected String banner;
	protected String imdb_id;
	protected String zap2it_id;
	protected ArrayList<Actor> cast;
	protected ArrayList<Episode> episodes;
	protected String language;
	protected boolean favorited;

	/**
	 * @param id
	 *            is the series' id on the DB
	 */
	public Series(String id) {
		this.id = id;
		this.favorited = false;
		this.cast = new ArrayList<Actor>();
		this.episodes = new ArrayList<Episode>();
	}

	/**
	 * @return the id
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
	 * @return the language if not null empty value otherwise
	 */
	public String getLanguage() {
		if (language != null && !language.equals(NULL_VALUE)) {
			return " (" + language + ")";
		} else
			return EMPTY_VALUE;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the name of the series
	 */
	public String getSeriesName() {
		return seriesName;
	}

	/**
	 * @param seriesName
	 *            the series name to set
	 */
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	/**
	 * @return the genre of the series Uses a StringTokenizer for separate
	 *         distinct genres then join it in a csv
	 */
	public String getGenre() {
		String result = null;
		ArrayList<String> list = new ArrayList<String>();
		if (genre != null && !genre.isEmpty()) {
			StringTokenizer tokenizer = new StringTokenizer(genre, "|");
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
	 * @param genre
	 *            the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * @return the date of series first airing
	 */
	public CustomGregorianCalendar getFirstAired() {
		return firstAired;
	}

	/**
	 * @param firstAired
	 *            the date of first airing to set
	 */
	public void setFirstAired(CustomGregorianCalendar firstAired) {
		this.firstAired = firstAired;
	}

	/**
	 * Create a date starting from a String and set it to the field
	 * 
	 * @param firstAired
	 *            is the String in format yyyy-mm-dd
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
		}
	}

	/**
	 * @return the MPAA content rating of the series if not null, N/A otherwise
	 */
	public String getContentRating() {
		if (contentRating != null && !contentRating.equals(NULL_VALUE)
				&& !contentRating.equals(EMPTY_VALUE))
			return contentRating;
		else
			return NOT_AVAILABLE;
	}

	/**
	 * @param contentRating
	 *            the content rating to set
	 */
	public void setContentRating(String contentRating) {
		this.contentRating = contentRating;
	}

	/**
	 * @return the overview of the series if not null, N/A otherwise
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
	 * @return the series' broadcasting network if not null, N/A otherwise
	 */
	public String getNetwork() {
		if (network != null && !network.equals(NULL_VALUE)
				&& !network.equals(EMPTY_VALUE)) {
			return network;
		} else
			return NOT_AVAILABLE;
	}

	/**
	 * @param network
	 *            the network to set
	 */
	public void setNetwork(String network) {
		this.network = network;
	}

	/**
	 * @return the rating of the audience if not null, N/A otherwise
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
	 * @return the current status of the series if not null, N/A otherwise
	 */
	public String getStatus() {
		if (status != null && !status.equals(NULL_VALUE)
				&& !status.equals(EMPTY_VALUE)) {
			return status;
		} else
			return NOT_AVAILABLE;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the approx runtime of the series' episodes if not null, N/A
	 *         otherwise
	 */
	public String getRuntime() {
		if (runtime != null && !runtime.equals(NULL_VALUE)
				&& !runtime.equals(EMPTY_VALUE)) {
			return runtime;
		} else
			return NOT_AVAILABLE;
	}

	/**
	 * @param runtime
	 *            the runtime to set
	 */
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	/**
	 * @return the banner of the series
	 */
	public String getBanner() {
		if (banner != null && !banner.equals(NULL_VALUE)
				&& !banner.equals(EMPTY_VALUE)) {
			return banner;
		} else
			return null;
	}

	/**
	 * @param banner
	 *            the banner to set
	 */
	public void setBanner(String banner) {
		this.banner = banner;
	}

	/**
	 * @return the series id on IMDB
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
	 * @return a list containing the cast of the series
	 */
	public ArrayList<Actor> getCast() {
		return cast;
	}

	/**
	 * @param cast
	 *            the cast to set
	 */
	public void setCast(ArrayList<Actor> cast) {
		this.cast = cast;
	}

	/**
	 * @return a list containing the episodes of the series
	 */
	public ArrayList<Episode> getEpisodes() {
		return episodes;
	}

	/**
	 * @param episodes
	 *            the episodes to set
	 */
	public void setEpisodes(ArrayList<Episode> episodes) {
		this.episodes = episodes;
	}

	/**
	 * @return a String representing series
	 */
	public String toString() {
		return seriesName + getLanguage();

	}

	/**
	 * Add the actors to the list starting from a String using a StringTokenizer
	 * 
	 * @param cast
	 *            is the String representing series cast
	 */
	public void addCastFromString(String cast) {
		if (cast != null) {
			StringTokenizer tokenizer = new StringTokenizer(cast, "|");
			while (tokenizer.hasMoreTokens()) {
				Actor a = new Actor(tokenizer.nextToken());
				getCast().add(a);
			}
		}

	}

	/**
	 * Add episode to list
	 * 
	 * @param e
	 *            is the episode to add
	 */
	public void addEpisode(Episode e) {
		getEpisodes().add(e);
	}

	/**
	 * Check if this Series id user favorited
	 * 
	 * @return the true if this is in the favorited DB false otherwise
	 */
	public boolean isFavorited() {
		return favorited;
	}

	/**
	 * @param set
	 *            this series favorite
	 */
	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	/**
	 * Create a clone of this object
	 * 
	 * @return a clone
	 */
	public Series clone() {
		Series result = new Series(id);
		result.setBanner(banner);
		result.setCast(cast);
		result.setContentRating(contentRating);
		result.setEpisodes(episodes);
		result.setFavorited(favorited);
		result.setFirstAired(firstAired);
		result.setGenre(genre);
		result.setImdb_id(imdb_id);
		result.setLanguage(language);
		result.setNetwork(network);
		result.setOverview(overview);
		result.setRating(rating);
		result.setRuntime(runtime);
		result.setSeriesName(seriesName);
		result.setStatus(status);
		return result;
	}

	/**
	 * equals override
	 * 
	 * @param o
	 *            is the object to compare
	 * @return true if this object is equals to o false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		Series s = (Series) o;
		if (s != null && s.getId().equals(this.getId()))
			return true;
		return false;
	}

	/**
	 * Return the list of episodes grouped by season
	 * 
	 * @return the episodes list gropued by season
	 */
	public LinkedHashMap<String, ArrayList<Episode>> groupBySeason() {
		LinkedHashMap<String, ArrayList<Episode>> result = new LinkedHashMap<String, ArrayList<Episode>>();
		String currentString = "0";
		ArrayList<Episode> currentArray = new ArrayList<Episode>();
		for (int i = 0; i < episodes.size(); i++) {

			Episode current = episodes.get(i);
			if (currentString.equals(current.getSeasonNumber())) {
				currentArray.add(current);
			} else {
				result.put(currentString, currentArray);
				currentString = current.getSeasonNumber();
				currentArray = new ArrayList<Episode>();
				currentArray.add(current);
			}
		}
		result.put(currentString, currentArray);
		return result;

	}

}
