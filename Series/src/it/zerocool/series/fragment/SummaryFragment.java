/**
 * File it.zerocool.series.fragment/SummaryFragment.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */

package it.zerocool.series.fragment;

import java.text.SimpleDateFormat;

import engine.utility.CustomGregorianCalendar;
import engine.utility.RequestUtility;
import model.Series;
import it.zerocool.series.R;
import it.zerocool.series.dialog.ParentalDialog;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class is one of the fragment of ActivityDetails, representing the series
 * details summary
 * 
 * @author Marco
 * 
 */
public class SummaryFragment extends Fragment implements OnClickListener {

	protected Series target;
	protected TextView tvOverview, tvFirstAired, tvMPAA, tvGenre, tvUserRating,
			tvRuntime, tvStatus, tvNetwork, tvLink;
	protected ImageView ivBanner;
	protected Series detailedSeries;
	protected DownloadBitmapTask task;
	SimpleDateFormat fmt;

	/**
	 * Initializes all view then start filling
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_summary, container,
				false);

		// Retrive target series from Intent Extra
		target = (Series) getActivity().getIntent().getExtras()
				.getSerializable("Series");

		// Widget initialization
		tvOverview = (TextView) rootView.findViewById(R.id.tvOverview);
		tvFirstAired = (TextView) rootView.findViewById(R.id.tvFirstAired);
		tvMPAA = (TextView) rootView.findViewById(R.id.tvMPAA);
		tvMPAA.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.tvMPAA) {
					ParentalDialog frg = new ParentalDialog();
					frg.show(getActivity().getFragmentManager(),
							"Parental Fragment");
				}

			}
		});
		tvGenre = (TextView) rootView.findViewById(R.id.tvGenre);
		tvUserRating = (TextView) rootView.findViewById(R.id.tvUserRating);
		tvRuntime = (TextView) rootView.findViewById(R.id.tvRuntime);
		tvStatus = (TextView) rootView.findViewById(R.id.tvStatus);
		tvNetwork = (TextView) rootView.findViewById(R.id.tvNetwork);
		ivBanner = (ImageView) rootView.findViewById(R.id.ivBanner);
		tvLink = (TextView) rootView.findViewById(R.id.tvIMDb);
		tvLink.setOnClickListener(this);
		fmt = new SimpleDateFormat("dd/MM/yyyy");

		// Fill all the available fields
		fillFields();

		return rootView;

	}

	/**
	 * Callback on the super class
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	/**
	 * Listener for the IMDb text view click, it starts an Intent for browser or
	 * IMDb app
	 */
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.tvIMDb) {
			if (target.getImdb_id() != null) {
				String uri = getResources().getString(R.string.imdb_series_URI);
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri
						+ target.getImdb_id()));
				startActivity(intent);
			} else
				Toast.makeText(getActivity().getApplicationContext(),
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
	 * Aux method that fill all available fields It also downloads the series
	 * banner from the site
	 */
	private void fillFields() {
		tvOverview.setText(target.getOverview());
		if (target.getFirstAired() != null) {
			CustomGregorianCalendar date = target.getFirstAired();
			String d = fmt.format(date.getTime());
			tvFirstAired.setText(d);
		} else
			tvFirstAired.setText(R.string.not_available);

		tvNetwork.setText(target.getNetwork());
		if (target.getBanner() != null) {
			task = new DownloadBitmapTask();
			task.execute(getResources().getString(R.string.banner_download_uri)
					+ target.getBanner());
		} else
			ivBanner.setVisibility(View.VISIBLE);
		tvGenre.setText(target.getGenre());
		tvUserRating.setText(target.getRating());
		tvRuntime.setText(target.getRuntime() + "'");
		tvStatus.setText(target.getStatus());
		tvMPAA.setText(target.getContentRating());

	}

	/**
	 * Inner class representing task that download series banner
	 * 
	 * @author Marco
	 * 
	 */
	private class DownloadBitmapTask extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
			return RequestUtility.downloadBitmap(params[0]);

		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if (result != null) {
				ivBanner.setImageBitmap(result);
			}
			ivBanner.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

	}
}
