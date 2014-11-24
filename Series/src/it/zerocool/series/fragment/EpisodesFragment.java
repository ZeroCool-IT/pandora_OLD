/**
 * File it.zerocool.series.fragment/EpisodesFragment.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */
package it.zerocool.series.fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import engine.database.FavoriteDBHelper;
import engine.database.FavoriteDBManager;
import engine.utility.ParsingUtility;
import engine.utility.ZipUtility;
import model.Episode;
import model.Series;
import it.zerocool.series.EpisodeActivity;
import it.zerocool.series.R;
import it.zerocool.series.adapter.ExpandableEpisodesListAdapter;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

/**
 * This class represents TV Series episodes list tab.
 * 
 * @author Marco Battisti
 * 
 */
public class EpisodesFragment extends Fragment implements OnChildClickListener {

	protected Series target;
	protected Context context;
	protected ExpandableListView episodeList;
	protected ArrayList<Episode> episodeArray;
	protected LinkedHashMap<String, ArrayList<Episode>> groupedEpisode;
	protected ExpandableEpisodesListAdapter adapter;
	protected SQLiteDatabase favorite;
	protected DetailsRetireveTask task;

	/**
	 * Creates tab and search for episodes list
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_episodes, container,
				false);
		context = getActivity().getApplicationContext();

		episodeList = (ExpandableListView) rootView
				.findViewById(R.id.exLVEpisodes);

		episodeList.setOnChildClickListener(this);

		target = (Series) getActivity().getIntent().getExtras()
				.getSerializable("Series");
		openFavorites();

		// Avoid to reload details if is not necessary
		if (episodeArray == null) {
			loadFromFile();
		} else {
			episodeList.setAdapter(adapter);
		}

		return rootView;
	}

	@Override
	public void onStop() {
		if (task != null && task.getStatus() == Status.RUNNING) {
			task.cancel(true);
		}
		super.onStop();
	}

	/**
	 * Builds the adapter for episodes list
	 */
	private void buildAdapter() {
		ArrayList<String> seasonList = prepareListData();
		adapter = new ExpandableEpisodesListAdapter(getActivity()
				.getApplicationContext(), seasonList, groupedEpisode);
		episodeList.setAdapter(adapter);

	}

	/**
	 * On list item click, starts new activity showing episode details
	 */
	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		Intent intent = new Intent(context, EpisodeActivity.class);
		Episode e = (Episode) adapter.getChild(groupPosition, childPosition);
		intent.putExtra("Episode", e);
		Series clone = target.clone();
		clone.setEpisodes(null);
		intent.putExtra("Series", clone);
		startActivity(intent);
		return true;
	}

	/**
	 * Aux method for db opening
	 */
	private void openFavorites() {
		OpenFavoritesTask task = new OpenFavoritesTask();
		task.execute();
	}

	/**
	 * Prepares the episodes list grouping them by season
	 * 
	 * @return The array with group headers
	 */
	private ArrayList<String> prepareListData() {
		Set<String> keys = groupedEpisode.keySet();
		LinkedHashMap<String, ArrayList<Episode>> result = new LinkedHashMap<String, ArrayList<Episode>>();
		ArrayList<String> keyList = new ArrayList<String>();
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String currentKey = it.next();
			ArrayList<Episode> group = groupedEpisode.get(currentKey);
			String newKey;
			if (!currentKey.equals("0")) {
				newKey = getResources().getString(R.string.season) + " "
						+ currentKey;
			} else
				newKey = getResources().getString(R.string.specials);
			keyList.add(newKey);
			result.put(newKey, group);
		}
		groupedEpisode = result;
		return keyList;

	}

	/**
	 * 
	 */
	private void loadFromFile() {
		task = new DetailsRetireveTask();
		task.execute(target.getId());
	}

	/**
	 * Inner class representing task for db opening
	 * 
	 * @author Marco
	 * 
	 */
	private class OpenFavoritesTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			FavoriteDBHelper dbHelper = FavoriteDBHelper.getInstance(context);
			favorite = dbHelper.getWritableDB();

			return null;
		}

	}

	/**
	 * Inner class representing task for loading series details
	 * 
	 * @author Marco Battisti
	 * 
	 */
	private class DetailsRetireveTask extends AsyncTask<String, Void, Void> {

		/**
		 * Load details from DB if series is favorite, load from files otherwise
		 */
		@Override
		protected Void doInBackground(String... params) {
			// Load series' details from cache files
			// if it's not in the favorites list
			if (!target.isFavorited()) {
				SharedPreferences sharedPref = PreferenceManager
						.getDefaultSharedPreferences(context);
				String lang = sharedPref.getString("lang", "en");
				String seriesResult = null;
				try {
					seriesResult = ZipUtility.getStringFromFile(context
							.getCacheDir()
							+ "/"
							+ params[0]
							+ "/"
							+ lang
							+ ".xml");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				target = ParsingUtility
						.parseCompleteSeriesDetails(seriesResult);
			}
			// Load details from DB
			else
				FavoriteDBManager.loadEpisodes(favorite, target);
			return null;
		}

		/**
		 * Showing ActionBar progress indicator while searching
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			getActivity().setProgressBarIndeterminateVisibility(true);
		}

		/**
		 * Dismiss progress indicator and build results list
		 */
		@Override
		protected void onPostExecute(Void res) {
			getActivity().setProgressBarIndeterminateVisibility(false);
			episodeArray = target.getEpisodes();
			groupedEpisode = target.groupBySeason();
			buildAdapter();
		}

	}
}
