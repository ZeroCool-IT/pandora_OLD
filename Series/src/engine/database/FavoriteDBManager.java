/**
 * File engine.database/SeriesDB.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */

package engine.database;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import model.Actor;
import model.Episode;
import model.Series;

/**
 * Database use-case class
 * 
 * @author Marco Battisti
 * 
 */
public class FavoriteDBManager {

	protected static final String DB_NAME = "SeriesDB";

	protected static final int DB_VERSION = 1;

	protected static final String AUTHORITY = "it.zerocool.series";

	protected static final String TABLE_FAVORITE = "Favorite";
	protected static final String TABLE_EPISODE = "Episode";
	protected static final String TABLE_ACTOR = "Actor";

	protected static final String ID_COLUMN = "ID";
	protected static final String SERIES_NAME_COLUMN = "SeriesName";
	protected static final String GENRE_COLUMN = "Genre";
	protected static final String FIRST_AIRED_COLUMN = "FirstAired";
	protected static final String CONTENT_RATING_COLUMN = "ContentRating";
	protected static final String OVERVIEW_COLUMN = "Overview";
	protected static final String NETWORK_COLUMN = "Network";
	protected static final String RATING_COLUMN = "Rating";
	protected static final String STATUS_COLUMN = "Status";
	protected static final String RUNTIME_COLUMN = "Runtime";
	protected static final String BANNER_COLUMN = "Banner";
	protected static final String IMDB_ID_COLUMN = "IMDb_ID";

	protected static final String SERIES_COLUMN = "Series";
	protected static final String SEASON_NUMBER_COLUMN = "SeasonNumber";
	protected static final String EPISODE_NUMBER_COLUMN = "EpisodeNumber";
	protected static final String EPISODE_NAME_COLUMN = "EpisodeName";
	protected static final String PRODUCTION_CODE_COLUMN = "ProductionCode";
	protected static final String IMAGE_COLUMN = "Image";
	protected static final String GUESTSTARS_COLUMN = "GuestStars";
	protected static final String WRITERS_COLUMN = "Writers";
	protected static final String DIRECTORS_COLUMN = "Directors";

	protected static final String NAME_COLUMN = "Name";
	protected static final String PIC_COLUMN = "Pic";
	protected static final String ROLE_COLUMN = "Role";

	protected static final String NULL_VALUE = "Null";

	/**
	 * Add a series to the favorites list
	 * 
	 * @param db
	 *            is the favorites database
	 * @param s
	 *            is the series to add
	 */
	public static void favoriteSeries(SQLiteDatabase db, Series s) {
		if (s != null) {
			ContentValues values = new ContentValues();
			values.put(ID_COLUMN, s.getId());
			values.put(SERIES_NAME_COLUMN, s.getSeriesName());
			values.put(GENRE_COLUMN, s.getGenre());
			if (s.getFirstAired() != null) {
				values.put(FIRST_AIRED_COLUMN, s.getFirstAired().toString());
			}
			values.put(CONTENT_RATING_COLUMN, s.getContentRating());
			values.put(OVERVIEW_COLUMN, s.getOverview());
			values.put(NETWORK_COLUMN, s.getNetwork());
			values.put(RATING_COLUMN, s.getRating());
			values.put(STATUS_COLUMN, s.getStatus());
			values.put(RUNTIME_COLUMN, s.getRuntime());
			values.put(BANNER_COLUMN, s.getBanner());
			values.put(IMDB_ID_COLUMN, s.getImdb_id());

			db.insert(TABLE_FAVORITE, null, values);
			ArrayList<Episode> episodes = s.getEpisodes();
			Iterator<Episode> it = episodes.iterator();
			db.beginTransaction();
			while (it.hasNext()) {
				Episode e = it.next();
				ContentValues episodeValues = new ContentValues();
				episodeValues.put(SERIES_COLUMN, s.getId());
				episodeValues.put(ID_COLUMN, e.getId());
				episodeValues.put(SEASON_NUMBER_COLUMN, e.getSeasonNumber());
				episodeValues.put(EPISODE_NUMBER_COLUMN, e.getEpisodeNumber());
				episodeValues.put(EPISODE_NAME_COLUMN, e.getEpisodeName());
				if (e.getFirstAired() != null) {
					episodeValues.put(FIRST_AIRED_COLUMN, e.cacheFirstAired());
				}
				episodeValues.put(OVERVIEW_COLUMN, e.getOverview());
				episodeValues
						.put(PRODUCTION_CODE_COLUMN, e.getProductionCode());
				episodeValues.put(RATING_COLUMN, e.getRating());
				episodeValues.put(IMAGE_COLUMN, e.getImage());
				episodeValues.put(IMDB_ID_COLUMN, e.getImdb_id());
				episodeValues.put(GUESTSTARS_COLUMN, e.cacheGuestStars());
				episodeValues.put(WRITERS_COLUMN, e.cacheWriters());
				episodeValues.put(DIRECTORS_COLUMN, e.cacheDirector());
				db.insert(TABLE_EPISODE, null, episodeValues);
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			ArrayList<Actor> cast = s.getCast();
			Iterator<Actor> actIterator = cast.iterator();
			db.beginTransaction();
			while (actIterator.hasNext()) {
				Actor a = actIterator.next();
				ContentValues actorValues = new ContentValues();
				actorValues.put(SERIES_COLUMN, s.getId());
				actorValues.put(NAME_COLUMN, a.getName());
				actorValues.put(PIC_COLUMN, a.getPic());
				actorValues.put(ROLE_COLUMN, a.getRole());
				db.insert(TABLE_ACTOR, null, actorValues);
			}
			db.setTransactionSuccessful();
			db.endTransaction();
		}
	}

	/**
	 * Load series' episodes from db
	 * 
	 * @param db
	 *            is the favorites db
	 * @param s
	 *            is the series to add episodes
	 */
	public static void loadEpisodes(SQLiteDatabase db, Series s) {
		String whereClause = SERIES_COLUMN + "= ?";
		String[] whereArgs = new String[1];
		whereArgs[0] = s.getId();
		Cursor c = db.query(TABLE_EPISODE, null, whereClause, whereArgs, null,
				null, null);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			Episode e = new Episode(c.getString(1));
			e.setSeasonNumber(c.getString(2));
			e.setEpisodeNumber(c.getString(3));
			e.setEpisodeName(c.getString(4));
			String date = c.getString(5);
			e.setFirstAired(date);
			e.setOverview(c.getString(6));
			e.setProductionCode(c.getString(7));
			e.setRating(c.getString(8));
			e.setImage(c.getString(9));
			e.setImdb_id(c.getString(10));
			e.addGuestStarsFromString(c.getString(11));
			e.addWritersFromString(c.getString(12));
			e.addDirectorFromString(c.getString(13));
			s.addEpisode(e);
			c.moveToNext();
		}
	}

	/**
	 * Load series cast from DB
	 * 
	 * @param db
	 *            is the favorites DB
	 * @param s
	 *            is the series to add cast
	 */
	public static void loadCast(SQLiteDatabase db, Series s) {
		String whereClause = SERIES_COLUMN + "= ?";
		String[] whereArgs = new String[1];
		whereArgs[0] = s.getId();
		Cursor c = db.query(TABLE_ACTOR, null, whereClause, whereArgs, null,
				null, null);
		c.moveToFirst();
		ArrayList<Actor> cast = s.getCast();
		while (!c.isAfterLast()) {
			Actor a = new Actor(c.getString(1));
			a.setPic(c.getString(2));
			a.setRole(c.getString(3));
			cast.add(a);
			c.moveToNext();
		}
	}

	/**
	 * Remove a series from the favorites list
	 * 
	 * @param db
	 *            is the favorites database
	 * @param s
	 *            is the series to remove
	 */
	public static void unfavoriteSeries(SQLiteDatabase db, Series s) {
		if (s != null) {
			String whereClause = ID_COLUMN + " = ?";
			String[] whereArgs = new String[1];
			whereArgs[0] = s.getId();
			db.delete(TABLE_FAVORITE, whereClause, whereArgs);
			whereClause = SERIES_COLUMN + " = ?";
			db.delete(TABLE_EPISODE, whereClause, whereArgs);
			db.delete(TABLE_ACTOR, whereClause, whereArgs);
		}

	}

	/**
	 * Returns an ArrayList representing the favorites list
	 * 
	 * @param db
	 *            is the favorites database
	 * @return the favorites list
	 */
	public static ArrayList<Series> listFavorites(SQLiteDatabase db) {
		ArrayList<Series> result = new ArrayList<Series>();

		Cursor c = db.query(TABLE_FAVORITE, null, null, null, null, null, null);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			Series s = new Series(c.getString(0));
			s.setSeriesName(c.getString(1));
			s.setGenre(c.getString(2));
			if (c.getString(3) != null) {
				s.setFirstAired(c.getString(3));
			} else
				s.setFirstAired(NULL_VALUE);
			s.setContentRating(c.getString(4));
			s.setOverview(c.getString(5));
			s.setNetwork(c.getString(6));
			s.setRating(c.getString(7));
			s.setStatus(c.getString(8));
			s.setRuntime(c.getString(9));
			s.setBanner(c.getString(10));
			s.setImdb_id(c.getString(11));
			s.setFavorited(true);
			result.add(s);
			c.moveToNext();
		}

		return result;

	}

	/**
	 * Check if a series is in the favorites list
	 * 
	 * @param db
	 *            is the favorites database
	 * @param s
	 *            is the series to check
	 * @return true if s is in the database, false otherwise
	 */
	public static boolean isFavorited(SQLiteDatabase db, Series s) {
		Series result = null;
		String whereClause = ID_COLUMN + " = ?";
		String[] whereArgs = new String[1];
		whereArgs[0] = s.getId();
		Cursor c = db.query(TABLE_FAVORITE, null, whereClause, whereArgs, null,
				null, null);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			result = new Series(c.getString(0));
		}
		return result != null;
	}

	/**
	 * Clear the favorites database
	 * 
	 * @param db
	 *            is the database
	 */
	public static void clearFavoirtes(SQLiteDatabase db) {
		db.delete(TABLE_FAVORITE, null, null);
		db.delete(TABLE_EPISODE, null, null);
		db.delete(TABLE_ACTOR, null, null);
	}

}
