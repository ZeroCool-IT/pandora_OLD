/**
 * File it.zerocool.series.adapter/CastAdapter.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */
package it.zerocool.series.adapter;

import it.zerocool.series.R;

import java.util.ArrayList;
import model.Actor;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Adapter class for Cast ListView
 * 
 */
public class CastAdapter extends ArrayAdapter<Actor> {

	private ArrayList<Actor> cast;

	/**
	 * Adapter Constructor
	 * 
	 * @param context
	 * @param resource
	 * @param textViewResourceId
	 * @param objects
	 */
	public CastAdapter(Context context, int resource, int textViewResourceId,
			ArrayList<Actor> objects) {
		super(context, resource, textViewResourceId, objects);
		cast = objects;
		// TODO Auto-generated constructor stub
	}

	/**
	 * getView() override. Inflates elements layout and fills fields Also sets
	 * the alternate color of rows
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = super.getView(position, convertView, parent);

		if (rowView != null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.cast_adapter, null);
		}
		Actor a = cast.get(position);
		if (a != null) {
			TextView tvActorName = (TextView) rowView
					.findViewById(R.id.tvActorName);
			TextView tvActorRole = (TextView) rowView.findViewById(R.id.tvRole);
			if (tvActorName != null) {
				tvActorName.setText(a.getName());
			}
			if (tvActorRole != null) {
				tvActorRole.setText(a.getRole());
			}
		}

		if (position % 2 == 1) {
			rowView.setBackgroundColor(rowView.getResources().getColor(
					R.color.black));
		} else {
			rowView.setBackgroundColor(rowView.getResources().getColor(
					R.color.adapter_alternate_row));
		}
		return rowView;

	}

}
