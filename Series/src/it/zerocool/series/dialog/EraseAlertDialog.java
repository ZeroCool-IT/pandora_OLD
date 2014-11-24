/**
 * File it.zerocool.series.dialog/EraseAlertDialog.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */

package it.zerocool.series.dialog;

import java.util.ArrayList;

import model.Series;
import engine.database.FavoriteDBManager;
import it.zerocool.series.HomeActivity;
import it.zerocool.series.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

/**
 * Dialog class shown when user wants to erase favorites list
 * 
 * @author Marco Battisti
 * 
 */
public class EraseAlertDialog extends DialogFragment {

	private SQLiteDatabase db;

	public EraseAlertDialog(SQLiteDatabase db) {
		super();
		this.db = db;
	}

	/**
	 * Build the dialog and starts the erase task on positive button click. Do
	 * nothing on negative button click
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(R.string.erase_menu_message);
		builder.setTitle(R.string.erase_menu_title)
				.setPositiveButton(R.string.erase_menu_positive,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								ClearFavoritesTask task = new ClearFavoritesTask();
								task.execute();

								Intent intent = new Intent(getActivity(),
										HomeActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
										| Intent.FLAG_ACTIVITY_NEW_TASK);
								startActivity(intent);
							}
						})
				.setNegativeButton(R.string.erase_menu_negative,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User cancelled the dialog
							}
						});
		// Create the AlertDialog object and return it
		return builder.create();
	}

	/**
	 * Inner task class to erase favorites list
	 * 
	 * @author Marco Battisti
	 * 
	 */
	private class ClearFavoritesTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			ArrayList<Series> favorites = FavoriteDBManager.listFavorites(db);
			if (!favorites.isEmpty())
				FavoriteDBManager.clearFavoirtes(db);
			else
				return null;
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

	}
}
