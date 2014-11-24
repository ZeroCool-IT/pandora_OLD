/**
 * File it.zerocool.series/ActorActivity.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */

package it.zerocool.series;

import engine.utility.RequestUtility;
import model.Actor;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Activity representing actor picture and role
 * 
 * @author Marco Battisti
 */
public class ActorActivity extends Activity {

	protected Actor target;
	protected TextView tvName;
	protected TextView tvRole;
	protected ImageView ivPic;
	protected DownloadBitmapTask task;

	/**
	 * When activity starts, initializes widgets, fills fields and downloads the
	 * actor's bitmap
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actor_details);

		target = (Actor) getIntent().getExtras().getSerializable("Actor");

		tvName = (TextView) findViewById(R.id.tvAcName);
		tvRole = (TextView) findViewById(R.id.tvActorRole);
		ivPic = (ImageView) findViewById(R.id.ivActorPic);

		fillFields();
	}

	/**
	 * Fill all available fields
	 */
	private void fillFields() {
		tvName.setText(target.getName());
		tvRole.setText(target.getRole());

		if (target.getPic() != null) {
			task = new DownloadBitmapTask();
			task.execute(getResources().getString(R.string.banner_download_uri)
					+ target.getPic());
		} else
			ivPic.setVisibility(View.VISIBLE);

	}

	/**
	 * Stops the task when activity goes on stop
	 */
	@Override
	public void onStop() {
		super.onStop();

		// check the state of the task
		if (task != null && task.getStatus() == Status.RUNNING)
			task.cancel(true);
	}

	/**
	 * Inner class representing task for actor's picture downloading
	 * 
	 * @author Marco
	 * 
	 */
	private class DownloadBitmapTask extends AsyncTask<String, Void, Bitmap> {

		/**
		 * Downloads the bitmap from Internet
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
				ivPic.setImageBitmap(result);
			}
			ivPic.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

	}

}
