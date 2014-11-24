/**
 * File it.zerocool.series/DetailsActivity.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */

package it.zerocool.series;

import java.io.IOException;

import engine.database.FavoriteDBHelper;
import engine.database.FavoriteDBManager;
import engine.utility.ParsingUtility;
import engine.utility.ZipUtility;
import model.Series;
import it.zerocool.series.adapter.SwipePagerAdapter;
import it.zerocool.series.dialog.WarningDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ShareActionProvider;
import android.widget.Toast;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;

/**
 * Class representing TV Series details Activity This class is a container for
 * details fragment tabs. Here are managed operations to create fragments. This
 * class also manage the ActionBar actions, like favorite or share actions
 * 
 * @author Marco Battisti
 * 
 */
public class DetailsActivity extends FragmentActivity implements
		ActionBar.TabListener, ViewPager.OnPageChangeListener {

	private static final int ADD = 1;
	private static final int REMOVE = 0;

	private ViewPager viewPager;
	private SwipePagerAdapter adapter;
	private String[] tabs;
	private ActionBar mActionBar;
	private Series series;
	private boolean isFave;
	private Context context;
	private SQLiteDatabase favoritesDB;
	// private SQLiteDatabase cache;
	private String lang;
	private ManageFavoriteTask task;
	private FavoriteDBHelper dbHelper;

	/**
	 * Action performed on ActivityDetails create
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_details);

		// Initialize swipe adapter
		adapter = new SwipePagerAdapter(getSupportFragmentManager());
		final ActionBar actionBar = getActionBar();
		mActionBar = actionBar;

		context = this;

		// Get target series from intent extras
		series = (Series) getIntent().getExtras().getSerializable("Series");
		isFave = series.isFavorited();
		// loadFromFile();

		// Set up button and navigation mode
		actionBar.setHomeButtonEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(this);

		// Build tabs name from array
		tabs = getResources().getStringArray(R.array.tabs);

		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		// Star from central tab
		viewPager.setCurrentItem(1);

		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(context);
		lang = sharedPref.getString("lang", "en");

		// Open database to manage favorites
		openDatabase();
	}

	/**
	 * Creates the ActionBar option menus
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}

	/**
	 * Switch the favorite icon
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem fave = menu.findItem(R.id.fav);
		MenuItem unfave = menu.findItem(R.id.unfav);
		fave.setVisible(isFave);
		unfave.setVisible(!isFave);
		return true;
	}

	/**
	 * Listener for ActionBar menu selected On icon pressed, adding or removing
	 * of the series from favorites list is performed
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.fav:
			series.setFavorited(false);
			isFave = series.isFavorited();
			updateFav(REMOVE);
			supportInvalidateOptionsMenu();
			return true;
		case R.id.unfav:
			series.setFavorited(true);
			isFave = series.isFavorited();
			updateFav(ADD);
			supportInvalidateOptionsMenu();
			return true;
		case R.id.menu_item_share:
			new ShareActionProvider(this);
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			String message = getResources().getString(
					R.string.share_series_message)
					+ "\n"
					+ series.getSeriesName()
					+ "\n"
					+ getResources().getString(R.string.thetvdb_share_link)
					+ series.getId();
			sendIntent.putExtra(Intent.EXTRA_TEXT, message);
			sendIntent.setType("text/plain");
			startActivity(Intent.createChooser(sendIntent, getResources()
					.getText(R.string.share)));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	/**
	 * Listener for tab selection
	 */
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position) {
		mActionBar.setSelectedNavigationItem(position);

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
	public void onRestart() {
		super.onRestart();
		openDatabase();
	}

	/**
	 * Aux method for DB opening
	 */
	private void openDatabase() {
		DatabaseOpenerAsyncTask task = new DatabaseOpenerAsyncTask();
		task.execute();
	}

	/**
	 * Start the task for DB update
	 * 
	 * @param operation
	 *            switch between Add or Remove operation
	 */
	private void updateFav(int operation) {
		task = new ManageFavoriteTask();
		switch (operation) {
		case ADD:
			task.execute(ADD);
			break;
		case REMOVE:
			task.execute(REMOVE);
			break;
		}
	}

	/**
	 * Inner class for favorites management
	 * 
	 * @author Marco Battisti
	 * 
	 */
	private class ManageFavoriteTask extends AsyncTask<Integer, Void, Void> {

		private ProgressDialog progressDialog;
		private int op;

		@Override
		protected Void doInBackground(Integer... params) {
			int operation = Integer.valueOf(params[0]);
			switch (operation) {
			case ADD:
				op = ADD;
				// Load episodes' list from cache files
				if (series.getEpisodes().isEmpty()) {
					String filepath = context.getCacheDir().getAbsolutePath()
							+ "/" + series.getId() + "/" + lang + ".xml";
					String seriesResult = null;
					try {
						seriesResult = ZipUtility.getStringFromFile(filepath);
					} catch (IOException e) {
						Log.e("CACHE LOADING ERROR", "SERIES NULL!");
					}

					boolean fave = series.isFavorited();
					Series aux;
					if (seriesResult != null) {
						aux = ParsingUtility
								.parseCompleteSeriesDetails(seriesResult);
					} else {
						// When series is no more in favorites list
						// and there isn't cache files to reload
						// show warning dialog and bring user to home
						// so he can reload it
						this.cancel(true);
						String title = getResources().getString(
								R.string.dialog_title_error);
						String message = getResources().getString(
								R.string.dialog_message_refresh_error);
						Drawable icon = getResources().getDrawable(
								R.drawable.ic_action_warning);
						WarningDialog dialog = new WarningDialog(title,
								message, icon, true);
						dialog.show(getFragmentManager(), "Request Error");
						Log.w("SERIES REFRESH ERROR", "NEED TO REFRESH SERIES!");
						return null;
					}

					series.setEpisodes(aux.getEpisodes());
					series.setFavorited(fave);
				}
				FavoriteDBManager.favoriteSeries(favoritesDB, series);
				break;
			case REMOVE:
				op = REMOVE;
				FavoriteDBManager.unfavoriteSeries(favoritesDB, series);
				break;

			}
			return null;
		}

		/**
		 * Build and show ProgressDialog while adding to favorite
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(context,
					AlertDialog.THEME_HOLO_DARK);
			progressDialog.setIndeterminate(true);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage(getResources().getString(
					R.string.favorite_update));
			progressDialog.setCancelable(false);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.show();
		}

		/**
		 * Dismiss ProgressDialog and launch details activity if result is not
		 * null. Otherwise, it start an error dialog
		 */
		@Override
		protected void onPostExecute(Void res) {
			progressDialog.dismiss();
			if (this.op == REMOVE) {
				Toast.makeText(getApplicationContext(), R.string.fave_removed,
						Toast.LENGTH_SHORT).show();
			} else
				Toast.makeText(getApplicationContext(), R.string.fave_add,
						Toast.LENGTH_SHORT).show();

		}

	}

	/**
	 * Inner class for database opening
	 * 
	 * @author Marco Battisti
	 * 
	 */
	private class DatabaseOpenerAsyncTask extends
			AsyncTask<Void, Void, SQLiteDatabase> {

		@Override
		protected SQLiteDatabase doInBackground(Void... params) {
			// Open database if exists, build it otherwise
			dbHelper = FavoriteDBHelper.getInstance(context);
			SQLiteDatabase result = dbHelper.getWritableDB();
			return result;
		}

		@Override
		protected void onPostExecute(SQLiteDatabase result) {
			favoritesDB = result;
		}
	}

}
