/**
 * Copyright (C) 2015 - Mazarin Pvt Ltd
 * @author Tharaka Nirmana
 */
package com.mazarin_movieApp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mazarin_movieApp.service.MovieWebService;
import com.mazarin_movieApp.util.Config;
import com.mazarin_movieApp.util.GravityCompoundDrawable;
import com.mazarin_movieApp.util.JsonAnalyzer;

/*
 * This class handles tthe saving of a movie to the backend.
 * When the activity loads it will load all genres from the server.
 * Using the form in this activty, it is possible to add a new movie to the server provided that all validations are passed
 * 
 */
public class AddMovieActivity extends BaseActivity implements OnClickListener {

	AddMovieActivity context;
	EditText edtMovieName, edtCast, edtYear;
	TextView txtGenre;
	CheckBox chkFiction;
	Button btnAdd, btnSave;
	LinearLayout layCast;
	ImageView star1, star2, star3, star4, star5, star6, star7, star8, star9,
			star10;
	ArrayList<String> genreList;
	ArrayList<String> castList;
	int rating = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_movies_layout);
		initComponants();
		setListners();
		getGenres();
	}

	/*
	 * Initialize the componants of the xml UI
	 */
	private void initComponants() {
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(getResources().getColor(R.color.theme_blue)));
		context = this;
		edtMovieName = (EditText) findViewById(R.id.edt_mov_name);
		edtCast = (EditText) findViewById(R.id.edt_cast);
		edtYear = (EditText) findViewById(R.id.txt_year);
		txtGenre = (TextView) findViewById(R.id.txt_genre);
		chkFiction = (CheckBox) findViewById(R.id.chk_fiction);
		btnAdd = (Button) findViewById(R.id.btn_add_cast);
		layCast = (LinearLayout) findViewById(R.id.cast_lay);
		btnSave = (Button) findViewById(R.id.save_movie);
		star1 = (ImageView) findViewById(R.id.star1);
		star2 = (ImageView) findViewById(R.id.star2);
		star3 = (ImageView) findViewById(R.id.star3);
		star4 = (ImageView) findViewById(R.id.star4);
		star5 = (ImageView) findViewById(R.id.star5);
		star6 = (ImageView) findViewById(R.id.star6);
		star7 = (ImageView) findViewById(R.id.star7);
		star8 = (ImageView) findViewById(R.id.star8);
		star9 = (ImageView) findViewById(R.id.star9);
		star10 = (ImageView) findViewById(R.id.star10);
		castList = new ArrayList<String>();
	}

	/*
	 * Make the UI componants clickable
	 */
	private void setListners() {
		txtGenre.setOnClickListener(this);
		btnAdd.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		star1.setOnClickListener(this);
		star2.setOnClickListener(this);
		star3.setOnClickListener(this);
		star4.setOnClickListener(this);
		star5.setOnClickListener(this);
		star6.setOnClickListener(this);
		star7.setOnClickListener(this);
		star8.setOnClickListener(this);
		star9.setOnClickListener(this);
		star10.setOnClickListener(this);
	}

	/*
	 * Service call to get all genre from the webservice
	 */
	private void getGenres() {
		if (checkInternet(context)) {
			showProgressDialog(context, Config.pleaseWait);
			MovieWebService.getMovieGenres(context);
		} else {
			showHintDialog(context, Config.noInternet, Config.error);
		}
	}

	/*
	 * After getting all genres results from service, pasrse the json and create
	 * an array list from it
	 */
	public void didReceivegetMovieGenresDetails(JSONArray genreJArray) {
		if(genreJArray == null){
			showHintDialog(context, Config.timeOut, Config.error);
			hideProgressDialog();
			return;
		}
		genreList = new ArrayList<String>();
		try {
			genreList = JsonAnalyzer.parseGenreJson(genreJArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		hideProgressDialog();
	}

	/*
	 * Post request to add a movie. All user inputs have to udergo some
	 * validations, if they pass only it is possible to add a new movie
	 */
	private void addMovie() {
		String movieName = edtMovieName.getText().toString().trim();
		String year = edtYear.getText().toString().trim();
		String isFiction = "false";
		if (chkFiction.isChecked()) {
			isFiction = "true";
		}
		String genre = txtGenre.getText().toString().trim();

		// movie name check
		if (movieName.length() == 0) {
			showHintDialog(context, Config.enter_movie_name, Config.error);
			return;
		}
		// year check
		if (year.length() == 0) {
			showHintDialog(context, Config.enter_year, Config.error);
			return;
		}
		// year lenght check
		if (year.length() != 4) {
			showHintDialog(context, Config.enter_valid_year, Config.error);
			return;
		}
		// year format check
		if (!year.matches("[0-9]+")) {
			showHintDialog(context, Config.enter_valid_format_year,
					Config.error);
			return;
		}
		// genre check
		if (genre.length() == 0) {
			showHintDialog(context, Config.select_genre_type, Config.error);
			return;
		}
		// cast check
		if (castList.size() == 0) {
			showHintDialog(context, Config.add_casts, Config.error);
			return;
		}
		// rating check
		if (rating == 0) {
			showHintDialog(context, Config.select_rating, Config.error);
			return;
		}
		// convert castList to a json array as expected by the service
		JSONArray jsArray = new JSONArray(castList);

		if (checkInternet(context)) {
			showProgressDialog(context, Config.pleaseWait);
			try {
				// pass the parameters after url encoding
				MovieWebService.addMovie(context,
						URLEncoder.encode(movieName, "UTF-8"),
						URLEncoder.encode(year, "UTF-8"), isFiction,
						URLEncoder.encode(genre, "UTF-8"),
						URLEncoder.encode(jsArray.toString(), "UTF-8"), rating
								+ "");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			showHintDialog(context, Config.noInternet, Config.error);
		}
	}

	/*
	 * After submitting a new movie, when the results are obtained from the
	 * server, alert the user saying whether it succeeded or failed
	 */
	public void didReceivAddMovieDetails(String responseBody) {
		if(responseBody == null){
			showHintDialog(context, Config.timeOut, Config.error);
			hideProgressDialog();
			return;
		}
		if (responseBody.contains("Succees")) {
			showHintDialog(context, Config.movie_added, Config.success);
		} else {
			showHintDialog(context, Config.movie_not_added, Config.success);
		}
		hideProgressDialog();
	}

	/*
	 * On click of the add cast button : adds the typed cast to an array list
	 */
	private void addCast() {
		if (edtCast.getText().toString().trim().length() != 0) {
			castList.add(edtCast.getText().toString().trim());
			populateCastlayout();
			edtCast.setText("");
			hideKeyboard();
		} else {
			showHintDialog(context, Config.add_cast_error, Config.error);
		}
	}

	/*
	 * Cast array list is used to populate the linear layout that will contain
	 * the casts. Textviews are created dynamically and added to the linear
	 * layout. Click listner is also set to each textview with a tag so that
	 * when clicked on a textview, the added cast name will get deleted.
	 */
	private void populateCastlayout() {
		// add dynamically all textviews to the linear layout.
		layCast.removeAllViews();
		for (int i = 0; i < castList.size(); i++) {
			String castName = castList.get(i);

			TextView textview = new TextView(context);
			LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			llp.setMargins(0, 20, 0, 20);
			textview.setText(castName);

			// the "X" mark drawable to the left hand side of the textview
			Drawable img = getResources().getDrawable(
					R.drawable.my_referral_list_btn_remove);
			GravityCompoundDrawable gravityDrawable = new GravityCompoundDrawable(
					img);
			img.setBounds(0, 0, img.getIntrinsicWidth(),
					img.getIntrinsicHeight());
			int valueInPixels = 12;
			gravityDrawable.setBounds(0, valueInPixels,
					img.getIntrinsicWidth(), img.getIntrinsicHeight());
			textview.setCompoundDrawables(gravityDrawable, null, null, null);
			textview.setCompoundDrawablePadding(10);

			textview.setLayoutParams(llp);
			textview.setTextColor(getResources().getColor(R.color.black));
			textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
			textview.setOnClickListener(this);
			textview.setTag(castName);
			layCast.addView(textview);
		}
	}

	/*
	 * Click action of the select genre textview. All genres will be displayed
	 * in a listview in a pop up and the user can select from it.
	 */
	private void showGenres() {
		AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
		builderSingle.setIcon(R.drawable.ic_launcher);
		builderSingle.setTitle("Select Genre");

		// loop the arraylist and add to the arrayAdapter
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				context, android.R.layout.select_dialog_item);
		for (int i = 0; i < genreList.size(); i++) {
			arrayAdapter.add(genreList.get(i));
		}

		builderSingle.setNegativeButton("cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		// selected genre will be set to the textview
		builderSingle.setAdapter(arrayAdapter,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String selectedGenre = arrayAdapter.getItem(which);
						txtGenre.setText(selectedGenre);
					}
				});
		builderSingle.show();
	}

	/*
	 * Before displaying the selected star number, all stars will be set to the
	 * default state
	 */
	private void resetAllStars() {
		star1.setBackgroundResource(R.drawable.ic_star_red);
		star2.setBackgroundResource(R.drawable.ic_star_red);
		star3.setBackgroundResource(R.drawable.ic_star_red);
		star4.setBackgroundResource(R.drawable.ic_star_red);
		star5.setBackgroundResource(R.drawable.ic_star_red);
		star6.setBackgroundResource(R.drawable.ic_star_red);
		star7.setBackgroundResource(R.drawable.ic_star_red);
		star8.setBackgroundResource(R.drawable.ic_star_red);
		star9.setBackgroundResource(R.drawable.ic_star_red);
		star10.setBackgroundResource(R.drawable.ic_star_red);
	}

	/*
	 * Hides the keyboard after a cast is added
	 */
	private void hideKeyboard() {
		View view = this.getCurrentFocus();
		if (view != null) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	// handling click events
	public void onClick(View v) {
		// if clicked on one of the cast textviews that were added, remove it
		// depending on the tag of the cast textview
		String castName = ((String) v.getTag());
		if (castName != null) {
			// loop the cast array list to find out the tag
			for (int i = 0; i < castList.size(); i++) {
				if (castName.equals(castList.get(i))) {
					// remove that tag (name) from the casr array list
					castList.remove(i);
					return;
				}
			}
			// repopulate the cast linear layout
			populateCastlayout();
		}

		switch (v.getId()) {
		case R.id.txt_genre:
			showGenres();
			break;
		case R.id.btn_add_cast:
			addCast();
			break;
		case R.id.save_movie:
			addMovie();
			break;
		case R.id.star1:
			rating = 1;
			resetAllStars();
			star1.setBackgroundResource(R.drawable.ic_star_filled_red);
			break;
		case R.id.star2:
			rating = 2;
			resetAllStars();
			star1.setBackgroundResource(R.drawable.ic_star_filled_red);
			star2.setBackgroundResource(R.drawable.ic_star_filled_red);
			break;
		case R.id.star3:
			rating = 3;
			resetAllStars();
			star1.setBackgroundResource(R.drawable.ic_star_filled_red);
			star2.setBackgroundResource(R.drawable.ic_star_filled_red);
			star3.setBackgroundResource(R.drawable.ic_star_filled_red);
			break;
		case R.id.star4:
			rating = 4;
			resetAllStars();
			star1.setBackgroundResource(R.drawable.ic_star_filled_red);
			star2.setBackgroundResource(R.drawable.ic_star_filled_red);
			star3.setBackgroundResource(R.drawable.ic_star_filled_red);
			star4.setBackgroundResource(R.drawable.ic_star_filled_red);
			break;
		case R.id.star5:
			rating = 5;
			resetAllStars();
			star1.setBackgroundResource(R.drawable.ic_star_filled_red);
			star2.setBackgroundResource(R.drawable.ic_star_filled_red);
			star3.setBackgroundResource(R.drawable.ic_star_filled_red);
			star4.setBackgroundResource(R.drawable.ic_star_filled_red);
			star5.setBackgroundResource(R.drawable.ic_star_filled_red);
			break;
		case R.id.star6:
			rating = 6;
			resetAllStars();
			star1.setBackgroundResource(R.drawable.ic_star_filled_red);
			star2.setBackgroundResource(R.drawable.ic_star_filled_red);
			star3.setBackgroundResource(R.drawable.ic_star_filled_red);
			star4.setBackgroundResource(R.drawable.ic_star_filled_red);
			star5.setBackgroundResource(R.drawable.ic_star_filled_red);
			star6.setBackgroundResource(R.drawable.ic_star_filled_red);
			break;
		case R.id.star7:
			rating = 7;
			resetAllStars();
			star1.setBackgroundResource(R.drawable.ic_star_filled_red);
			star2.setBackgroundResource(R.drawable.ic_star_filled_red);
			star3.setBackgroundResource(R.drawable.ic_star_filled_red);
			star4.setBackgroundResource(R.drawable.ic_star_filled_red);
			star5.setBackgroundResource(R.drawable.ic_star_filled_red);
			star6.setBackgroundResource(R.drawable.ic_star_filled_red);
			star7.setBackgroundResource(R.drawable.ic_star_filled_red);
			break;
		case R.id.star8:
			rating = 8;
			resetAllStars();
			star1.setBackgroundResource(R.drawable.ic_star_filled_red);
			star2.setBackgroundResource(R.drawable.ic_star_filled_red);
			star3.setBackgroundResource(R.drawable.ic_star_filled_red);
			star4.setBackgroundResource(R.drawable.ic_star_filled_red);
			star5.setBackgroundResource(R.drawable.ic_star_filled_red);
			star6.setBackgroundResource(R.drawable.ic_star_filled_red);
			star7.setBackgroundResource(R.drawable.ic_star_filled_red);
			star8.setBackgroundResource(R.drawable.ic_star_filled_red);
			break;
		case R.id.star9:
			rating = 9;
			resetAllStars();
			star1.setBackgroundResource(R.drawable.ic_star_filled_red);
			star2.setBackgroundResource(R.drawable.ic_star_filled_red);
			star3.setBackgroundResource(R.drawable.ic_star_filled_red);
			star4.setBackgroundResource(R.drawable.ic_star_filled_red);
			star5.setBackgroundResource(R.drawable.ic_star_filled_red);
			star6.setBackgroundResource(R.drawable.ic_star_filled_red);
			star7.setBackgroundResource(R.drawable.ic_star_filled_red);
			star8.setBackgroundResource(R.drawable.ic_star_filled_red);
			star9.setBackgroundResource(R.drawable.ic_star_filled_red);
			break;
		case R.id.star10:
			rating = 10;
			resetAllStars();
			star1.setBackgroundResource(R.drawable.ic_star_filled_red);
			star2.setBackgroundResource(R.drawable.ic_star_filled_red);
			star3.setBackgroundResource(R.drawable.ic_star_filled_red);
			star4.setBackgroundResource(R.drawable.ic_star_filled_red);
			star5.setBackgroundResource(R.drawable.ic_star_filled_red);
			star6.setBackgroundResource(R.drawable.ic_star_filled_red);
			star7.setBackgroundResource(R.drawable.ic_star_filled_red);
			star8.setBackgroundResource(R.drawable.ic_star_filled_red);
			star9.setBackgroundResource(R.drawable.ic_star_filled_red);
			star10.setBackgroundResource(R.drawable.ic_star_filled_red);
			break;
		default:
			break;
		}
	}
}
