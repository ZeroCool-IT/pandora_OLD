/**
 * File it.zerocool.series.adapter/ExpandableEpisodesListAdapter.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */

package it.zerocool.series.adapter;

import it.zerocool.series.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import model.Episode;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * Adapter class for Episodes ExpandableListView
 * 
 * @author Marco Battisti
 */
public class ExpandableEpisodesListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private List<String> listDataHeader;
	private LinkedHashMap<String, ArrayList<Episode>> listDataChild;

	/**
	 * Adapter constructor
	 * 
	 * @param context
	 * @param listDataHeader
	 * @param listChildData
	 */
	public ExpandableEpisodesListAdapter(Context context,
			List<String> listDataHeader,
			LinkedHashMap<String, ArrayList<Episode>> listChildData) {
		this.context = context;
		this.listDataHeader = listDataHeader;
		this.listDataChild = listChildData;
	}

	/**
	 * @return the number of groups
	 */
	@Override
	public int getGroupCount() {
		return this.listDataHeader.size();
	}

	/**
	 * @param groupPosition
	 *            the position of the group
	 * @return the number of children in the given group
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		return this.listDataChild.get(this.listDataHeader.get(groupPosition))
				.size();
	}

	/**
	 * @param the
	 *            position of group
	 * @return the group in the given position
	 */
	@Override
	public Object getGroup(int groupPosition) {
		return this.listDataHeader.get(groupPosition);
	}

	/**
	 * @param groupPosition
	 *            the position of group
	 * @param childPosition
	 *            the position of child
	 * @return the child in the given positions
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return this.listDataChild.get(this.listDataHeader.get(groupPosition))
				.get(childPosition);
	}

	/**
	 * @param groupPosition
	 *            the group position
	 * @return the group position ad its ID
	 */
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	/**
	 * @param groupPosition
	 *            the position of group
	 * @param childPosition
	 *            the position of child
	 * @return the position of child as its ID
	 * 
	 */
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	/**
	 * getGroupView() override. Inflates elements layout and fills fields Also
	 * sets the alternate color of rows
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_group, null);
		}

		TextView lblListHeader = (TextView) convertView
				.findViewById(R.id.lblListHeader);
		lblListHeader.setText(headerTitle);

		if (groupPosition % 2 == 1) {
			convertView.setBackgroundColor(convertView.getResources().getColor(
					R.color.black));
		} else {
			convertView.setBackgroundColor(convertView.getResources().getColor(
					R.color.adapter_alternate_row));
		}

		return convertView;
	}

	/**
	 * getChildView() override. Inflates elements layout and fills fields Also
	 * sets the alternate color of rows
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		View rowView = convertView;

		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.episodes_adapter, null);
		}
		Episode ep = (Episode) getChild(groupPosition, childPosition);
		if (ep != null) {
			TextView tvEpisodeTitle = (TextView) rowView
					.findViewById(R.id.tvEpisodeTitle);
			TextView tvEpisodeNumber = (TextView) rowView
					.findViewById(R.id.tvEpisodeTicker);
			TextView tvSeasonNumber = (TextView) rowView
					.findViewById(R.id.tvSeasonTicker);
			if (tvEpisodeTitle != null)
				tvEpisodeTitle.setText(ep.getEpisodeName());
			if (tvEpisodeNumber != null)
				tvEpisodeNumber.setText(ep.getEpisodeNumber());
			if (tvSeasonNumber != null) {
				if (ep.getSeasonNumber().equals("0")) {
					tvSeasonNumber.setText(rowView.getResources().getString(
							R.string.specials));
				} else
					tvSeasonNumber.setText(ep.getSeasonNumber());
			}

		}

		if (childPosition % 2 == 1) {
			rowView.setBackgroundColor(rowView.getResources().getColor(
					R.color.black));
		} else {
			rowView.setBackgroundColor(rowView.getResources().getColor(
					R.color.adapter_alternate_row));
		}
		return rowView;

	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
