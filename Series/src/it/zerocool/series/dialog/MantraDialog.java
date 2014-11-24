/**
 * File it.zerocool.series.dialog/MantraDialog.java
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

public class MantraDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),
				AlertDialog.THEME_HOLO_DARK);
		builder.setTitle(getResources().getString(R.string.mantra_title));
		builder.setMessage(getResources().getString(R.string.mantra));
		// Create the AlertDialog object and return it
		return builder.create();
	}

}
