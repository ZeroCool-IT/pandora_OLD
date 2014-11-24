/**
 * File it.zerocool.series.fragment/EpisodeActivity.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */

package it.zerocool.series;

import java.text.SimpleDateFormat;

import engine.utility.CustomGregorianCalendar;
import engine.utility.RequestUtility;
import model.Episode;
import model.Series;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity representing episode details
 * 
 * @author Marco Battisti
 * 
 */
public class EpisodeActivity extends Activity implements OnClickListener {

	protected Episode target;
	protected TextView tvOverview;
	protected TextView tvWriters;
	protected TextView tvDirector;
	protected TextView tvGuestStars;
	protected TextView tvFirstAired;
	protected TextView tvProductionCode;
	protected TextView tvUserRating;
	protected ImageView ivEpisodeImage;
	protected TextView tvImdbLink;
	protected Series series;
	protected SimpleDateFormat fmt;
	protected DownloadBitmapTask task;

	/**
	 * When activity is created, initializes all widget and retrieves data from
	 * target, then fills all available fields
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_episodes_details);

		getActionBar().setHomeButtonEnabled(true);

		target = (Episode) getIntent().getExtras().getSerializable("Episode");
		series = (Series) getIntent().getExtras().getSerializable("Series");

		tvOverview = (TextView) findViewById(R.id.tvEpOverview);
		tvWriters = (TextView) findViewById(R.id.tvWriters);
		tvDirector = (TextView) findViewById(R.id.tvDirector);
		tvGuestStars = (TextView) findViewById(R.id.tvGuestStars);
		tvFirstAired = (TextView) findViewById(R.id.tvEpFirstAired);
		tvProductionCode = (TextView) findViewById(R.id.tvProductionCode);
		tvUserRating = (TextView) findViewById(R.id.tvEpUserRating);
		ivEpisodeImage = (ImageView) findViewById(R.id.epImage);
		tvImdbLink = (TextView) findViewById(R.id.tvEpIMDB);
		tvImdbLink.setOnClickListener(this);
		fmt = new SimpleDateFormat("dd/MM/yyyy");

		fillFields();

	}

	/**
	 * On click listener for IMDb link TextView. When clicked starts an Intent
	 * for opening IMDb link, if it's available. Shows a toast otherwise.
	 */
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.tvEpIMDB) {
			if (target.getImdb_id() != null) {
				String uri = getResources().getString(R.string.imdb_series_URI);
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri
						+ target.getImdb_id()));
				startActivity(intent);
			} else
				Toast.makeText(getApplicationContext(),
						R.string.no_link_warning, Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * Stops the executing task if Fragment stop
	 */
	@Override
	public void onStop() {
		super.onStop();

		// check the state of the task
		if (task != null && task.getStatus() == Status.RUNNING)
			task.cancel(true);
	}

	/**
	 * Fills all available fields
	 */
	private void fillFields() {
		tvOverview.setText(target.getOverview());
		tvWriters.setText(target.getWritersToString());
		tvDirector.setText(target.getDirectorToString());
		tvGuestStars.setText(target.getGuestStarsToString());
		if (target.getFirstAired() != null) {
			CustomGregorianCalendar date = target.getFirstAired();
			String d = fmt.format(date.getTime());
			tvFirstAired.setText(d);
		} else
			tvFirstAired.setText(R.string.not_available);
		tvProductionCode.setText(target.getProductionCode());
		tvUserRating.setText(target.getRating());
		if (target.getImage() != null) {
			task = new DownloadBitmapTask();
			task.execute(getResources().getString(R.string.banner_download_uri)
					+ target.getImage());
		} else
			ivEpisodeImage.setVisibility(View.VISIBLE);

	}

	/**
	 * Inflates ActionBar options menu, i.e. the share button
	 */
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.episode, menu);
		return true;
	}

	/**
	 * Listener for ActionBar share button.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_share:
			new ShareActionProvider(this);
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			String message = getResources().getString(
					R.string.share_episode_message1)
					+ " "
					+ series.getSeriesName()
					+ ":\n"
					+ getResources().getString(R.string.share_episode_message2)
					+ " "
					+ target.getSeasonNumber()
					+ ", "
					+ getResources().getString(R.string.share_episode_message3)
					+ " "
					+ target.getEpisodeNumber()
					+ ":\n"
					+ target.getEpisodeName();

			sendIntent.putExtra(Intent.EXTRA_TEXT, message);
			sendIntent.setType("text/plain");
			startActivity(Intent.createChooser(sendIntent, getResources()
					.getText(R.string.share)));
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Inner class representing task for episode picture downloading
	 * 
	 * @author Marco
	 * 
	 */
	private class DownloadBitmapTask extends AsyncTask<String, Void, Bitmap> {

		/**
		 * Downloads the bitmap from Internet
		 * 
		 */
		@Override
		protected Bitmap doInBackground(String... params) {
			return RequestUtility.downloadBitmap(params[0]);

		}

		/**
		 * If download is successful, set the bitmap. Make visible the
		 * placeholder otherwise
		 */
		@Override
		protected void onPostExecute(Bitmap result) {
			if (result != null) {
				ivEpisodeImage.setImageBitmap(result);
			}
			ivEpisodeImage.setVisibility(View.VISIBLE);

		}

	}

}
