/**
 * File it.zerocool.series.dialog/WarningDialog.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */

package it.zerocool.series.dialog;

import it.zerocool.series.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

/**
 * This class implements warning dialog for generic use
 * 
 * @author Marco Battisti
 * 
 */
public class WarningDialog extends DialogFragment {

	private String title;
	private String message;
	private Drawable icon;
	private boolean killActivity;

	/**
	 * WarningDialog constructor
	 * 
	 * @param title
	 *            is the title of the dialog
	 * @param message
	 *            is the message of the dialog
	 * @param icon
	 *            is the icon of the dialog
	 * @param killActivity
	 *            if true, pressing 'OK' button will kill the current activity
	 *            and bring back to previous activity
	 */
	public WarningDialog(String title, String message, Drawable icon,
			boolean killActivity) {
		super();
		this.title = title;
		this.message = message;
		this.icon = icon;
		this.killActivity = killActivity;

	}

	/**
	 * Action performed on creation of dialog
	 */
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),
				AlertDialog.THEME_HOLO_DARK);
		builder.setIcon(icon);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(R.string.dialog_button_OK,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						if (killActivity) {
							getActivity().finish();
						}
					}
				});
		// Create the AlertDialog object and return it
		return builder.create();
	}

}
