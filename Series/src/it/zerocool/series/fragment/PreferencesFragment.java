/**
 * File it.zerocool.series.fragment/PreferencesFragment.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */
package it.zerocool.series.fragment;

import it.zerocool.series.R;
import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Preferences fragment for search language selection, started on settings menu
 * click.
 * 
 * @author Marco Battisti
 * 
 */
public class PreferencesFragment extends PreferenceFragment {

	/**
	 * Loads preferences menu from XML file
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.activity_preferences);

	}
}
