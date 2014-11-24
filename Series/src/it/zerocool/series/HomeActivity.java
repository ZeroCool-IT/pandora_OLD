/**
 * File it.zerocool.series/HomeActivity.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */

package it.zerocool.series;

import java.io.File;
import java.util.ArrayList;

import model.Series;
import engine.database.FavoriteDBHelper;
import engine.database.FavoriteDBManager;
import it.zerocool.series.dialog.EraseAlertDialog;
import it.zerocool.series.fragment.HomeFragment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

/**
 * Home Activity Class. This is the launcher Activity, which is also a container
 * for Home Fragments. Here is designed the options menu. This Activity trim the
 * cache when application goes onDestroy()
 * 
 * @author Marco Battisti
 * 
 */
public class HomeActivity extends Activity {

	//Git Push Test
	//Git Push Test 2
	protected ArrayList<Series> favorites;
	FavoriteDBHelper dbHelper;
	SQLiteDatabase db;
	protected Context context;
	private static final long M_BACK_PRESS_THRESHOLD = 3500;
	private long mLastBackPress;
	protected Toast pressBackToast;

	/**
	 * Create Home Activity and initialize the favorites database, if not
	 * exists.
	 */
	@SuppressLint("ShowToast")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		pressBackToast = Toast.makeText(this, R.string.tback,
				Toast.LENGTH_SHORT);
		context = this;
		// Open database, or create it if not exists
		openDatabase();

	}

	/**
	 * Create the ActionBar options menu and set the SearchView
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.option_menu, menu);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.search)
				.getActionView();

		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(false); // Do not iconify the widget;
													// expand it by default
		return true;
	}

	/**
	 * Listener for ActionBar menus selected Here start preferences menu or
	 * clear favorites action
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			// Start PreferencesActivity
			Intent intent = new Intent(this.getApplicationContext(),
					PreferencesActivity.class);
			startActivity(intent);
		}
		if (id == R.id.clearFavorites) {
			// Start Dialog for favorites erase confirmation
			FavoriteDBHelper help = FavoriteDBHelper.getInstance(this);
			EraseAlertDialog dialog = new EraseAlertDialog(help.getWritableDB());
			dialog.show(getFragmentManager(), "Alert");

		}
		if (id == R.id.about) {
			Intent intent = new Intent(this.getApplicationContext(),
					AboutActivity.class);
			startActivity(intent);

		}
		return super.onOptionsItemSelected(item);

	}

	/**
	 * Close the databases when activity is destroyed. Also trim cache files
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			trimCache(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbHelper.close();
	}

	/**
	 * Re-open database on Activity resume
	 */
	@Override
	public void onResume() {
		super.onResume();
		openDatabase();
	}

	/**
	 * Re-open database on Activity restart
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
		openDatabase();
	}
	
	/**
	 * Re-open database on Activity restart
	 */
	@Override
	protected void onStart() {
		super.onStart();
		openDatabase();
	}

	/**
	 * Listener for back button pressed
	 */
	@Override
	public void onBackPressed() {
		long currentTime = System.currentTimeMillis();
		if (Math.abs(currentTime - mLastBackPress) > M_BACK_PRESS_THRESHOLD) {
			pressBackToast.show();
			mLastBackPress = currentTime;
		} else {
			pressBackToast.cancel();
			super.onBackPressed(); // Exit the application if back pressed
									// threshold is smaller than
									// M_BACK_PRESSED_TRESHOLD
		}
	}

	/**
	 * Aux method for database open
	 */
	private void openDatabase() {
		DatabaseOpenerAsyncTask task = new DatabaseOpenerAsyncTask();
		task.execute();
	}

	/**
	 * AsyncTask class for database creating or opening
	 * 
	 * @author Marco
	 * 
	 */
	private class DatabaseOpenerAsyncTask extends
			AsyncTask<Void, Void, SQLiteDatabase> {

		/**
		 * Action performed in background
		 */
		@Override
		protected SQLiteDatabase doInBackground(Void... params) {
			// Open favorite database if exists, build it otherwise
			dbHelper = FavoriteDBHelper.getInstance(context);
			SQLiteDatabase result = dbHelper.getWritableDB();

			// Retrieve favorites
			favorites = FavoriteDBManager.listFavorites(result);
			return result;
		}

		/**
		 * Action performed on task post execute
		 */
		@Override
		protected void onPostExecute(SQLiteDatabase result) {
			db = result;

			// Start a new fragment with favorites list, if exists
			HomeFragment fragment = new HomeFragment();
			Bundle args = new Bundle();
			args.putSerializable("Favorites", favorites);
			fragment.setArguments(args);
			getFragmentManager().beginTransaction()
					.replace(R.id.container, fragment).commit();
		}
	}

	/**
	 * Trim cache files when exit application
	 * 
	 * @param context
	 *            is the context of application
	 */
	private void trimCache(Context context) {
		try {
			File dir = context.getCacheDir();
			if (dir != null && dir.isDirectory()) {
				deleteDir(dir);
			}
		} catch (Exception e) {
			Log.e("CACHE TRIM ERROR", e.getMessage());
		}
	}

	/**
	 * Delete dir
	 * 
	 * @param dir
	 *            is the path
	 * @return true ii success, false otherwise
	 */
	private static boolean deleteDir(File dir) {
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return dir.delete();
	}

}
