/**
 * File it.zerocool.series/AboutActivity.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */

package it.zerocool.series;

import it.zerocool.series.dialog.MantraDialog;
import it.zerocool.series.fragment.AboutFragment;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * Informations Activity
 * 
 * @author Marco Battisti
 * 
 */
public class AboutActivity extends Activity {

	protected ImageView android;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		android = (ImageView) findViewById(R.id.ivAndroid);
		android.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.ivAndroid) {
					MantraDialog dialog = new MantraDialog();
					dialog.show(getFragmentManager(), "Mantra");
				}

			}
		});
		Fragment fragment = new AboutFragment();
		getFragmentManager().beginTransaction()
				.add(R.id.about_container, fragment).commit();
	}

}
