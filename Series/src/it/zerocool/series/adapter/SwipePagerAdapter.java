/**
 * File it.zerocool.series.adapter/SwipePagerAdapter.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */

package it.zerocool.series.adapter;

import it.zerocool.series.fragment.CastFragment;
import it.zerocool.series.fragment.EpisodesFragment;
import it.zerocool.series.fragment.SummaryFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Adapter class for building the tabs navigation of series' details activity
 * 
 * @author Marco Battisti
 * 
 */
public class SwipePagerAdapter extends FragmentPagerAdapter {

	/**
	 * Adapter constructor
	 * 
	 * @param fm
	 */
	public SwipePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	/**
	 * Tab fragment selector
	 * 
	 * @param the
	 *            index of tab
	 */
	@Override
	public Fragment getItem(int index) {
		switch (index) {
		case 0:
			return new CastFragment();
		case 1:
			return new SummaryFragment();
		case 2:
			return new EpisodesFragment();
		}

		return null;
	}

	/**
	 * @return the number of tabs to show
	 */
	@Override
	public int getCount() {
		// The number of tabs
		return 3;
	}

}
