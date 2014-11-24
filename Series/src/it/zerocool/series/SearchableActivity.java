/**
 * File it.zerocool.series/SearchableActivity.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */

package it.zerocool.series;

import it.zerocool.series.adapter.SearchAdapter;
import it.zerocool.series.dialog.WarningDialog;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import model.Series;
import engine.database.FavoriteDBHelper;
import engine.database.FavoriteDBManager;
import engine.utility.ParsingUtility;
import engine.utility.RequestUtility;
import engine.utility.ZipUtility;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

/**
 * Activity class that performs search of tv series by name. Called by
 * SearchView in HomeActivity The target of the search is to show a list of
 * possibilities to user and retrieve TV Series id number for subsequent
 * research
 * 
 * @author Marco Battisti
 * 
 */
public class SearchableActivity extends ListActivity {

	private ArrayList<Series> searchResults;
	private Context context;
	private SearchAdapter seriesAdapter;
	private ArrayList<Series> favorites;
	private FavoriteDBHelper dbHelper;
	private RetrieveSeriesDetailsTask detailsTask;

	/**
	 * Creation of the activity. When the activity is created, it will
	 * automatically perform a search, filtering search intent from
	 * HomeActivity. If device is offline, shows a FragmentDialog to user
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchable);

		context = this;

		// Retrieve favorites list
		retrieveFavorites();

		searchResults = null;

		// Filtering the intent from HomeActivity
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			// Grab the search query
			String query = intent.getStringExtra(SearchManager.QUERY);

			// Connection check
			if (isOnline(this)) {
				performSearchByName(query);
			} else {
				// Show WarningDialog to user
				String message = getResources().getString(
						R.string.dialog_message_no_connection);
				String title = getResources().getString(
						R.string.dialog_title_warning);
				Drawable icon = getResources().getDrawable(
						R.drawable.ic_disconnected);
				WarningDialog dialog = new WarningDialog(title, message, icon,
						true);
				dialog.show(getFragmentManager(), "No Connection warning");

			}
		}

	}

	/**
	 * On back pressed a new home activity is started. This is performed for
	 * home activity refresh
	 */
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, HomeActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		super.onBackPressed();
	}

	/**
	 * Stop the task when activity goes on stop
	 */
	@Override
	public void onStop() {
		if (detailsTask != null && detailsTask.getStatus() == Status.RUNNING) {
			detailsTask.cancel(true);
		}
		super.onStop();
	}

	/**
	 * Listener for list items click. On click, details of the series are
	 * searched
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		searchDetails(seriesAdapter.getItem(position));

	}

	@Override
	public void onResume() {
		super.onResume();
		retrieveFavorites();
	}

	@Override
	public void onRestart() {
		super.onRestart();
		retrieveFavorites();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	/**
	 * Aux method for launching details Activity
	 * 
	 * @param s
	 *            is Series passed as parameter to the activity
	 */
	private void launchDetailsActivity(Series s) {
		Intent intent = new Intent(this, DetailsActivity.class);
		s.getEpisodes().clear();
		// s.getCast().clear();
		intent.putExtra("Series", s);
		startActivity(intent);
	}

	/**
	 * Aux method that create the URI search and call the search task Search
	 * language is retrieved from shared preferences
	 * 
	 * @param target
	 *            is the target series to search
	 */
	private void searchDetails(Series target) {
		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(this);
		String lang = sharedPref.getString("lang", "en");

		// Series' episodes search URI
		String seriesURI = getResources().getString(
				R.string.thetvdb_search_uri1)
				+ getResources().getString(R.string.api_key)
				+ getResources().getString(R.string.thetvdb_search_uri2)
				+ target.getId()
				+ getResources().getString(R.string.thetvdb_search_uri3)
				+ lang
				+ getResources().getString(R.string.thetvdb_search_uri4);

		// Series' cast search URI
		String castURI = getResources().getString(R.string.search_series_cast1)
				+ getResources().getString(R.string.api_key)
				+ getResources().getString(R.string.search_series_cast2)
				+ target.getId()
				+ getResources().getString(R.string.search_series_cast3);

		// Check if device is online
		if (isOnline(getApplicationContext())) {
			detailsTask = new RetrieveSeriesDetailsTask();
			detailsTask.execute(seriesURI, castURI, target.getId());
		} else {
			// Show WarningDialog to user
			String message = getResources().getString(
					R.string.dialog_message_no_connection);
			String title = getResources().getString(
					R.string.dialog_title_warning);
			Drawable icon = getResources().getDrawable(
					R.drawable.ic_disconnected);
			WarningDialog dialog = new WarningDialog(title, message, icon, true);
			dialog.show(getFragmentManager(), "No Connection warning");
		}
	}

	/**
	 * Aux method for retrieve favorites list
	 */
	private void retrieveFavorites() {
		OpenDatabaseTask task = new OpenDatabaseTask();
		task.execute();
	}

	/**
	 * Check if device is connected to Internet
	 * 
	 * @param context
	 *            is the context of SearchActivity
	 * @return true if device is connected, false otherwise
	 */
	private boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null
				&& activeNetwork.isConnectedOrConnecting();
		return isConnected;
	}

	/**
	 * AUX method that build query URI and call search task
	 * 
	 * @param query
	 *            is query to search for (i.e. TV Series name)
	 */
	private void performSearchByName(String query) {
		String uri = getResources().getString(R.string.search_series_id1);
		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(this);
		String lang = sharedPref.getString("lang", "en");
		String language = getResources().getString(R.string.search_series_id2)
				+ lang;
		String toSearch = query.replaceAll(" ", "%20");
		String finalURI = uri + toSearch + language;
		RetrieveSeriesIDAsyncTask task = new RetrieveSeriesIDAsyncTask();
		task.execute(finalURI);
	}

	/**
	 * Build the results list, if results exist, show WarningDialog to user
	 * otherwise
	 */
	private void updateUI() {
		if (!searchResults.isEmpty()) {
			seriesAdapter = new SearchAdapter(this,
					R.layout.search_series_result_adapter, R.id.tvCastAdapter,
					searchResults);
			this.setListAdapter(seriesAdapter);
		} else {
			String title = getResources().getString(
					R.string.dialog_title_no_results);
			Drawable icon = getResources().getDrawable(
					R.drawable.ic_action_error);
			WarningDialog dialog = new WarningDialog(title, null, icon, true);
			dialog.show(getFragmentManager(), "No Result");
		}

	}

	/**
	 * Private inner class for searching task
	 * 
	 * @author Marco Battisti
	 * 
	 */
	private class RetrieveSeriesIDAsyncTask extends
			AsyncTask<String, Void, Void> {

		// Progress dialog shown while search is performing
		private ProgressDialog progressDialog;

		/**
		 * Actions performed to search
		 */
		@Override
		protected Void doInBackground(String... params) {
			InputStream is = null;
			try {
				// Requesting InputStream from server
				is = RequestUtility.requestInputStream(params[0]);
				String requestResult = RequestUtility.inputStreamToString(is);
				searchResults = ParsingUtility.parseSearchResult(requestResult);
			} catch (IOException e) {
				// If request failed, show WarningDialog to user
				this.cancel(true);
				String title = getResources().getString(
						R.string.dialog_title_error);
				String message = getResources().getString(
						R.string.dialog_message_server_error);
				Drawable icon = getResources().getDrawable(
						R.drawable.ic_action_warning);
				WarningDialog dialog = new WarningDialog(title, message, icon,
						true);
				dialog.show(getFragmentManager(), "Request Error");
				Log.e("HTTP REQUEST ERROR", e.getMessage());
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				this.cancel(true);
				String title = getResources().getString(
						R.string.dialog_title_error);
				String message = getResources().getString(
						R.string.dialog_message_illegal_arguments);
				Drawable icon = getResources().getDrawable(
						R.drawable.ic_action_warning);
				WarningDialog dialog = new WarningDialog(title, message, icon,
						true);
				dialog.show(getFragmentManager(), "Request Error");
				Log.e("HTTP REQUEST ERROR", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * Build and show ProgressDialog while searching
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(context,
					AlertDialog.THEME_HOLO_DARK);
			progressDialog.setIndeterminate(true);
			progressDialog.setMessage(getResources().getString(
					R.string.searching));
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setCancelable(false);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.show();
		}

		/**
		 * Dismiss ProgressDialog and build results list
		 */
		@Override
		protected void onPostExecute(Void result) {
			progressDialog.dismiss();
			updateUI();
		}

		/**
		 * Dismiss ProgressDialog when request fails
		 */
		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
			progressDialog.dismiss();
		}

	}

	/**
	 * Private inner class for retireving series details
	 * 
	 * @author Marco Battisti
	 * 
	 */
	private class RetrieveSeriesDetailsTask extends
			AsyncTask<String, Void, Series> {

		// Progress dialog shown while search is performing
		private ProgressDialog progressDialog;

		/**
		 * Actions performed to search
		 */
		@Override
		protected Series doInBackground(String... params) {
			InputStream is = null;
			Series result = null;

			try {

				if (!this.isCancelled()) {
					// Requesting InputStream from server
					is = RequestUtility.requestInputStream(params[0]);
				} else
					return null;
				String seriesResult = null;
				if (!this.isCancelled()) {
					// Unpack ZIP and get the string
					SharedPreferences sharedPref = PreferenceManager
							.getDefaultSharedPreferences(context);
					String lang = sharedPref.getString("lang", "en");
					ZipUtility.unpackZip(context, params[2], is);
					seriesResult = ZipUtility.getStringFromFile(context
							.getCacheDir()
							+ "/"
							+ params[2]
							+ "/"
							+ lang
							+ ".xml");
				} else
					return null;
				// Parse informations
				if (!this.isCancelled()) {
					result = ParsingUtility
							.parseCompleteSeriesDetails(seriesResult);
				} else
					return null;

				// Do same thing with cast file
				String castResult = ZipUtility.getStringFromFile(context
						.getCacheDir() + "/" + params[2] + "/" + "actors.xml");
				ParsingUtility.parseCast(castResult, result);

				Log.i("EPISODES URI", params[0]);
				Log.i("CAST URI", params[1]);

				// set a series "favorite" for further reference
				// if it's in favorite list
				if (favorites.contains(result))
					result.setFavorited(true);

			} catch (IOException e) {
				// If request failed, show WarningDialog to user
				this.cancel(true);
				String title = getResources().getString(
						R.string.dialog_title_error);
				String message = getResources().getString(
						R.string.dialog_message_server_error);
				Drawable icon = getResources().getDrawable(
						R.drawable.ic_action_warning);
				WarningDialog dialog = new WarningDialog(title, message, icon,
						true);
				dialog.show(getFragmentManager(), "Request Error");
				Log.e("HTTP REQUEST ERROR", e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				Log.e("REQUEST ERROR", e.getMessage());

			}
			return result;
		}

		/**
		 * Build and show ProgressDialog while searching
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(context,
					AlertDialog.THEME_HOLO_DARK);
			progressDialog.setIndeterminate(true);
			progressDialog.setMessage(getResources().getString(
					R.string.retrieving));
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setCancelable(false);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.show();
		}

		/**
		 * Dismiss ProgressDialog and launch details activity if result is not
		 * null. Otherwise, it start an error dialog
		 */
		@Override
		protected void onPostExecute(Series res) {
			progressDialog.dismiss();
			if (res != null) {
				launchDetailsActivity(res);
			} else {
				String title = getResources().getString(
						R.string.dialog_title_error);
				String message = getResources().getString(
						R.string.dialog_message_no_data);
				Drawable icon = getResources().getDrawable(
						R.drawable.ic_action_warning);
				WarningDialog dialog = new WarningDialog(title, message, icon,
						true);
				dialog.show(getFragmentManager(), "Request Error");
				Log.e("DATA ERROR", "SERIES NULL!");
			}
		}

		/**
		 * Dismiss ProgressDialog when request fails
		 */
		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
			progressDialog.dismiss();
		}

	}

	/**
	 * Private inner class representing a task that open database for retrieving
	 * favorites list
	 * 
	 * @author Marco Battisti
	 * 
	 */
	private class OpenDatabaseTask extends
			AsyncTask<Void, Void, SQLiteDatabase> {

		@Override
		protected SQLiteDatabase doInBackground(Void... params) {
			// Open database if exists, build it otherwise
			dbHelper = FavoriteDBHelper.getInstance(context);

			SQLiteDatabase result = dbHelper.getWritableDB();
			favorites = FavoriteDBManager.listFavorites(result);

			return result;
		}

		@Override
		protected void onPostExecute(SQLiteDatabase result) {
			super.onPostExecute(result);
		}
	}
}
