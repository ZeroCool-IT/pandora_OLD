/**
 * File it.zerocool.series/PreferencesActivity.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */

package it.zerocool.series;

import it.zerocool.series.fragment.PreferencesFragment;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Preferences activity for search language selection, started on settings menu
 * click.
 * 
 * @author Marco Battisti
 */
public class PreferencesActivity extends PreferenceActivity {

	/**
	 * Starts preferences fragment
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Display the fragment as the main content.
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new PreferencesFragment())
				.commit();
	}

}
