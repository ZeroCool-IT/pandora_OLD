/**
 * File it.zerocool.series.adapter/FavoriteAdapter.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */

package it.zerocool.series.adapter;

import it.zerocool.series.R;

import java.util.ArrayList;
import model.Series;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Adapter class for home fragment favorites ListView
 * 
 * @author Marco Battisti
 * 
 */
public class FavoriteAdapter extends ArrayAdapter<Series> {

	private ArrayList<Series> objects;

	/**
	 * Adapter constructor
	 * 
	 * @param context
	 * @param listDataHeader
	 * @param listChildData
	 */
	public FavoriteAdapter(Context context, int resource,
			int textViewResourceId, ArrayList<Series> objects) {
		super(context, resource, textViewResourceId, objects);
		this.objects = objects;
	}

	/**
	 * getView() override. Inflates elements layout and fills fields
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rootView = super.getView(position, convertView, parent);
		if (rootView != null) {
			TextView tvSeriesName = (TextView) rootView
					.findViewById(R.id.tvFavSeriesName);
			tvSeriesName.setText(objects.get(position).getSeriesName());
			tvSeriesName.setBackgroundResource(android.R.color.transparent);

		}
		return rootView;
	}

}
