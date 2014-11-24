/**
 * File it.zerocool.series.fragment/HomeFragment.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */
package it.zerocool.series.fragment;

import java.util.ArrayList;

import model.Series;
import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import it.zerocool.series.DetailsActivity;
import it.zerocool.series.R;
import it.zerocool.series.adapter.FavoriteAdapter;

/**
 * Home fragment, displaying favorites list if exists, or user instruction
 */
public class HomeFragment extends Fragment implements OnItemClickListener {

	protected ArrayList<Series> favorites;
	protected FavoriteAdapter adapter;
	protected ListView lvFavorites;

	public HomeFragment() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Bundle bundle = this.getArguments();
		favorites = (ArrayList<Series>) bundle.getSerializable("Favorites");
		View rootView;

		// If favorites list exists, inflates favorite layout, else inflates
		// instruction layout
		if (!favorites.isEmpty()) {

			rootView = inflater.inflate(R.layout.fragment_home_fav, container,
					false);
			rootView.setBackgroundResource(android.R.color.transparent);
			lvFavorites = (ListView) rootView.findViewById(R.id.lvFav);
			adapter = new FavoriteAdapter(getActivity(),
					R.layout.favorites_adapter, R.id.tvFavSeriesName, favorites);
			lvFavorites.setAdapter(adapter);
			lvFavorites.setBackgroundResource(android.R.color.transparent);
			lvFavorites.setOnItemClickListener(this);
		} else
			rootView = inflater.inflate(R.layout.fragment_home_no_fav,
					container, false);
		return rootView;
	}

	/**
	 * On Favorite click, start series details activity, if device is online.
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Series s = adapter.getItem(position);
		s.setFavorited(true);
		Intent intent = new Intent(this.getActivity(), DetailsActivity.class);
		intent.putExtra("Series", s);
		startActivity(intent);
	}
}