/**
 * File engine.utility/RequestUtility.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */

package engine.utility;

import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import android.util.Log;
import model.Actor;
import model.Episode;
import model.Series;

/**
 * Utility class containing parsing utility methods
 * 
 * @author Marco Battisti
 * 
 */
public class ParsingUtility {

	/**
	 * Creates ArrayList of search result from JSON String
	 * 
	 * @param searchRes
	 *            is the search result
	 * @return an ArrayList of Series
	 */
	public static ArrayList<Series> parseSearchResult(String searchRes) {
		ArrayList<Series> result = new ArrayList<Series>();
		try {
			JSONObject reader = XML.toJSONObject(searchRes);
			JSONObject data = reader.getJSONObject("Data");
			JSONArray seriesArray = data.optJSONArray("Series");

			if (seriesArray != null) {
				for (int i = 0; i < seriesArray.length(); i++) {
					JSONObject toBuild = seriesArray.getJSONObject(i);
					Series s = new Series(toBuild.getString("id"));
					s.setLanguage(toBuild.getString("language").toUpperCase(
							Locale.getDefault()));
					s.setSeriesName(toBuild.getString("SeriesName"));
					s.setBanner(toBuild.optString("banner"));
					s.setOverview(toBuild.optString("Overview", "N/A"));
					s.setFirstAired(toBuild.optString("FirstAired"));
					s.setNetwork(toBuild.optString("Network", "N/A"));
					result.add(s);
				}

			} else {
				JSONObject seriesObject = data.optJSONObject("Series");
				if (seriesObject != null) {
					Series s = new Series(seriesObject.getString("id"));
					s.setSeriesName(seriesObject.getString("SeriesName"));
					s.setBanner(seriesObject.optString("banner"));
					s.setOverview(seriesObject.optString("Overview", "N/A"));
					s.setFirstAired(seriesObject.optString("FirstAired", "N/A"));
					s.setNetwork(seriesObject.optString("Network", "N/A"));
					result.add(s);
				}
			}
		} catch (JSONException e) {
			Log.e("JSON Exception", e.getMessage());
		}
		return result;
	}

	/**
	 * Creates object Series from JSON String
	 * 
	 * @param searchRes
	 *            is the String received as result of search
	 * @return a Series with all available fields
	 */
	public static Series parseSeriesDetails(String searchRes) {
		Series result = null;
		try {
			JSONObject reader = new JSONObject(searchRes);
			JSONObject data = reader.getJSONObject("query")
					.getJSONObject("results").getJSONObject("Data");
			JSONObject series = data.optJSONObject("Series");
			if (series != null) {
				result = new Series(series.getString("id"));
				String seriesName = series.optString("SeriesName");
				result.setSeriesName(seriesName);
				String genre = series.optString("Genre", "N/A");
				result.setGenre(genre);
				String firstAired = series.optString("FirstAired");
				result.setFirstAired(firstAired);
				String contentRating = series.optString("ContentRating", "N/A");
				result.setContentRating(contentRating);
				String overview = series.optString("Overview", "N/A");
				result.setOverview(overview);
				String network = series.optString("Network", "N/A");
				result.setNetwork(network);
				String rating = series.optString("Rating", "N/A");
				result.setRating(rating);
				String status = series.optString("Status", "N/A");
				result.setStatus(status);
				String runtime = series.optString("Runtime", "N/A");
				result.setRuntime(runtime);
				String banner = series.optString("banner");
				result.setBanner(banner);
				String imdb_id = series.optString("IMDB_ID", "N/A");
				result.setImdb_id(imdb_id);
			}

		} catch (JSONException e) {
			Log.e("JSON Exception", e.getMessage());
		}
		return result;
	}

	/**
	 * Adds the Episode objects to Series, starting from JSON string
	 * 
	 * @param searchRes
	 *            is the JSON string
	 * @param s
	 *            is the series to add episodes
	 */
	public static void parseSeriesEpisode(String searchRes, Series s) {
		try {
			JSONObject reader = new JSONObject(searchRes);

			JSONArray data = reader.getJSONObject("query")
					.getJSONObject("results").getJSONArray("Data");
			if (data != null) {
				Episode toAdd = null;
				for (int i = 0; i < data.length(); i++) {
					JSONObject ep = data.getJSONObject(i).getJSONObject(
							"Episode");
					toAdd = new Episode(ep.getString("id"));
					toAdd.setEpisodeNumber(ep.optString("EpisodeNumber", "N/A"));
					toAdd.setSeasonNumber(ep.optString("SeasonNumber", "N/A"));
					toAdd.setEpisodeName(ep.optString("EpisodeName", "N/A"));
					toAdd.setFirstAired(ep.optString("FirstAired"));
					toAdd.setOverview(ep.optString("Overview", "N/A"));
					toAdd.setProductionCode(ep.optString("ProductionCode",
							"N/A"));
					toAdd.setRating(ep.optString("Rating", "N/A"));
					toAdd.setImage(ep.optString("filename"));
					toAdd.setImdb_id(ep.optString("IMDB_ID"));
					toAdd.addDirectorFromString(ep.optString("Director"));
					toAdd.addGuestStarsFromString(ep.optString("GuestStars"));
					toAdd.addWritersFromString(ep.optString("Writer"));
					s.addEpisode(toAdd);
				}
			}

		} catch (JSONException e) {
			Log.e("JSON Exception", e.getMessage());
		}
	}

	/**
	 * Adds the Actor objects to Series starting from a String
	 * 
	 * @param searchRes
	 *            is the string
	 * @param s
	 *            is the series to add actors to
	 */
	public static void parseCast(String searchRes, Series s) {
		try {
			JSONObject reader = XML.toJSONObject(searchRes);
			JSONArray actor = reader.getJSONObject("Actors").optJSONArray(
					"Actor");
			if (actor != null) {
				for (int i = 0; i < actor.length(); i++) {
					JSONObject toBuild = actor.getJSONObject(i);
					Actor a = new Actor(toBuild.getString("Name"));
					a.setPic(toBuild.optString("Image"));
					a.setRole(toBuild.optString("Role"));
					s.getCast().add(a);
				}
			}

		} catch (JSONException e) {
			Log.e("JSON EXCEPTION", e.getMessage());
		}
	}

	/**
	 * Parses series with all details starting from a string
	 * 
	 * @param searchResult
	 *            is the string to parse
	 * @return the Series parsed from string
	 */
	public static Series parseCompleteSeriesDetails(String searchResult) {
		Series result = null;
		try {
			JSONObject reader = XML.toJSONObject(searchResult);
			JSONObject data = reader.getJSONObject("Data");
			JSONObject series = data.optJSONObject("Series");
			if (series != null) {
				result = new Series(series.getString("id"));
				String seriesName = series.optString("SeriesName");
				result.setSeriesName(seriesName);
				String genre = series.optString("Genre");
				result.setGenre(genre);
				String firstAired = series.optString("FirstAired");
				result.setFirstAired(firstAired);
				String contentRating = series.optString("ContentRating");
				result.setContentRating(contentRating);
				String overview = series.optString("Overview");
				result.setOverview(overview);
				String network = series.optString("Network");
				result.setNetwork(network);
				String rating = series.optString("Rating");
				result.setRating(rating);
				String status = series.optString("Status");
				result.setStatus(status);
				String runtime = series.optString("Runtime");
				result.setRuntime(runtime);
				String banner = series.optString("banner");
				result.setBanner(banner);
				String imdb_id = series.optString("IMDB_ID");
				result.setImdb_id(imdb_id);

				JSONArray episodes = data.optJSONArray("Episode");
				if (episodes != null) {
					Episode toAdd = null;
					for (int i = 0; i < episodes.length(); i++) {
						JSONObject ep = episodes.getJSONObject(i);
						toAdd = new Episode(ep.getString("id"));
						toAdd.setEpisodeNumber(ep.optString("EpisodeNumber"));
						toAdd.setSeasonNumber(ep.optString("SeasonNumber"));
						toAdd.setEpisodeName(ep.optString("EpisodeName"));
						toAdd.setFirstAired(ep.optString("FirstAired"));
						toAdd.setOverview(ep.optString("Overview"));
						toAdd.setProductionCode(ep.optString("ProductionCode"));
						toAdd.setRating(ep.optString("Rating"));
						toAdd.setImage(ep.optString("filename"));
						toAdd.setImdb_id(ep.optString("IMDB_ID"));
						toAdd.addDirectorFromString(ep.optString("Director"));
						toAdd.addGuestStarsFromString(ep
								.optString("GuestStars"));
						toAdd.addWritersFromString(ep.optString("Writer"));
						result.addEpisode(toAdd);
					}
				}

			}

		} catch (JSONException e) {
			Log.e("JSON Exception", e.getMessage());
		}
		return result;

	}

}
