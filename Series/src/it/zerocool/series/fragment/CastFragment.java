/**
 * File it.zerocool.series.fragment/CastFragment.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */
package it.zerocool.series.fragment;

import java.util.ArrayList;

import engine.database.FavoriteDBHelper;
import engine.database.FavoriteDBManager;
import model.Actor;
import model.Series;
import it.zerocool.series.ActorActivity;
import it.zerocool.series.R;
import it.zerocool.series.adapter.CastAdapter;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Fragment representing TV Series cast list
 * 
 * @author Marco Battisti
 * 
 */
public class CastFragment extends Fragment implements OnItemClickListener {

	protected ListView lvCast;
	protected CastAdapter adapter;
	protected Series target;
	protected ArrayList<Actor> cast;
	protected SQLiteDatabase cache;
	protected SQLiteDatabase favorite;

	/**
	 * Retrieves series infos from parent activity and populate the cast list
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_cast, container,
				false);

		target = (Series) getActivity().getIntent().getExtras()
				.getSerializable("Series");

		lvCast = (ListView) rootView.findViewById(R.id.castList);

		lvCast.setOnItemClickListener(this);

		// Avoid to reload infos if not necessary
		if (cast == null) {
			if (target.isFavorited()) {
				LoadCastTask task = new LoadCastTask();
				task.execute();
			} else {
				cast = target.getCast();
				buildAdapter();
			}
		}
		lvCast.setAdapter(adapter);

		return rootView;
	}

	/**
	 * Builds the adapter for the list
	 */
	private void buildAdapter() {
		adapter = new CastAdapter(getActivity().getApplicationContext(),
				R.layout.cast_adapter, R.id.tvActorName, target.getCast());
		lvCast.setAdapter(adapter);

	}

	/**
	 * On list item click, starts new activity showing the actor's pic
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Actor a = adapter.getItem(position);

		if (a.getPic() != null) {
			Intent intent = new Intent(getActivity().getApplicationContext(),
					ActorActivity.class);
			intent.putExtra("Actor", a);
			startActivity(intent);
		} else
			Toast.makeText(getActivity().getApplicationContext(),
					R.string.no_info_warning, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Inner class representing task for loading cast details when calling
	 * series from favorites list
	 * 
	 * @author Marco
	 * 
	 */
	private class LoadCastTask extends AsyncTask<Void, Void, Void> {

		/**
		 * When series is called from favorites list, load the cast from DB
		 */
		@Override
		protected Void doInBackground(Void... params) {
			FavoriteDBHelper f = FavoriteDBHelper.getInstance(getActivity());
			SQLiteDatabase db = f.getWritableDB();
			FavoriteDBManager.loadCast(db, target);
			cast = target.getCast();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			buildAdapter();
			super.onPostExecute(result);
		}

	}

}
