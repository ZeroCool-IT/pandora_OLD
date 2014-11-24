/**
 * File it.zerocool.series.dialog/ParentalDialog.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */

package it.zerocool.series.dialog;

import it.zerocool.series.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;

/**
 * Info dialog that shows the parental guidelines
 * 
 * @author Marco Battisti
 * 
 */
public class ParentalDialog extends DialogFragment {

	/**
	 * Action performed on creation of dialog
	 */
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),
				AlertDialog.THEME_HOLO_DARK);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		builder.setView(inflater
				.inflate(R.layout.fragment_parental_table, null));
		builder.setTitle(getResources().getString(
				R.string.parental_guidelines_title));
		// Create the AlertDialog object and return it
		return builder.create();
	}
}
