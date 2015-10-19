/**
 * Copyright (C) 2015 - Mazarin Pvt Ltd
 * @author Tharaka Nirmana
 */
package com.mazarin_movieApp;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.MenuItem;

/**
 * Contains all common methods to all Activities All activites will be extended
 * by this...
 */
public class BaseActivity extends Activity {

	ProgressDialog dialog;
	public static boolean progressCalled = false;
	public static LocationManager mLocationManager;

	/**
	 * initializes the progress bar
	 * 
	 * @param context
	 */
	protected void progressDialog(Context context) {

		if (dialog == null) {
			dialog = new ProgressDialog(context);
			dialog.setMessage("Please Wait...");
			//dialog.setCancelable(false);
		}

		BaseActivity.progressCalled = true;
	}

	/**
	 * hides the progress bar
	 */
	protected void hideProgressDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
		BaseActivity.progressCalled = false;
	}

	/**
	 * shows the progress bar
	 * 
	 * @param context
	 * @param message
	 */
	protected void showProgressDialog(Context context, String message) {

		if (dialog == null) {
			progressDialog(context);
		}
		if (!dialog.isShowing()) {
			hideProgressDialog();
			dialog.setMessage(message);
		}
		dialog.show();
		BaseActivity.progressCalled = true;

	}

	/**
	 * check whether the internet is available or not
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkInternet(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			// Log.v("INTERNET", "Available");
			return true;
		}
		return false;
	}

	/**
	 * shows messages in a dialog
	 * 
	 * @param context
	 * @param message
	 * @param title
	 */
	protected final void showHintDialog(Context context, String message,
			String title) {
		new Builder(context).setMessage(message).setTitle(title)
				.setPositiveButton("Done", null).create().show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	
	public static String theMonth(int month){
	    String[] monthNames = {"Jan", "Fe", "March", "April", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};
	    return monthNames[month];
	}
}
