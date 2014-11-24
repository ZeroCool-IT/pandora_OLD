/**
 * File it.zerocool.series.adapter/SearchAdapter.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */
package it.zerocool.series.adapter;

import it.zerocool.series.R;

import java.util.List;

import model.Series;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Adapter class for search result activity
 * 
 * @author Marco Battisti
 * 
 */
public class SearchAdapter extends ArrayAdapter<Series> {

	/**
	 * Adapter constructor
	 * 
	 * @param context
	 * @param resource
	 * @param textViewResourceId
	 * @param objects
	 */
	public SearchAdapter(Context context, int resource, int textViewResourceId,
			List<Series> objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		if (position % 2 == 1) {
			view.setBackgroundColor(view.getResources().getColor(R.color.black));
		} else {
			view.setBackgroundColor(view.getResources().getColor(
					R.color.adapter_alternate_row));
		}
		return view;

	}

}
