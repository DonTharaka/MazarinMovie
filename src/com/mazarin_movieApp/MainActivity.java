/**
 * Copyright (C) 2015 - Mazarin Pvt Ltd
 * @author Tharaka Nirmana
 */
package com.mazarin_movieApp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mazarin_movieApp.adapter.MovieAdapter;
import com.mazarin_movieApp.model.Movie;
import com.mazarin_movieApp.service.MovieWebService;
import com.mazarin_movieApp.util.Config;
import com.mazarin_movieApp.util.JsonAnalyzer;

/*
 * This class is used to display all movies. Movies are obtained via the web service.
 * When the page is loaded, the service call will be sent to get the movies via web service.
 * After getting the movies results, the json string is parsed and is set to a listview.
 * On click of an item in the listview, you can see the details of a perticular movie
 */
public class MainActivity extends BaseActivity {

	MainActivity context;
	ArrayList<Movie> moviesList;
	ListView lvMovies;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initComponants();
		getAllMovies();
	}
	
	/*
	 * This will sync movies info in background / foreground situation.
	 */
	@Override
	protected void onResume() {
		//getAllMovies();
		super.onResume();
	}

	/*
	 * Manu item is createed to navigate to add movie activity
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	/*
	 * Handling the click of the manu item
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.itemAdd) {
			Intent intent = new Intent(context, AddMovieActivity.class);
			startActivity(intent);
		}
		return false;
	}

	/*
	 * Initialize the componants of the xml, chhanges the color of the action
	 * bar and handles the item click of the listview
	 */
	private void initComponants() {
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(getResources().getColor(R.color.theme_blue)));
		context = this;
		lvMovies = (ListView) findViewById(R.id.list_movie);

		lvMovies.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// when the cell is clicked, identify the relevant object and
				// pass it to showMovieDetais() in order to show the details in
				// a pop up
				Movie movie = moviesList.get(position);
				showMovieDetais(movie);
			}
		});
	}

	/*
	 * Service call to get all movies, check if the internet is available fisrt.
	 */
	private void getAllMovies() {
		if (checkInternet(context)) {
			showProgressDialog(context, Config.pleaseWait);
			MovieWebService.getAllMovies(context);
		} else {
			showHintDialog(context, Config.noInternet, Config.error);
		}
	}

	/*
	 * After getting the service response, pasrse the json response and create
	 * the object array list. Create the adapter using the array list. Bind the
	 * adapter to the listview
	 */
	public void didReceivegetMovieDetails(JSONArray movieJArray) {
		if(movieJArray == null){
			showHintDialog(context, Config.timeOut, Config.error);
			hideProgressDialog();
			return;
		}
		moviesList = new ArrayList<Movie>();
		try {
			moviesList = JsonAnalyzer.parseMoviesJson(movieJArray);
			MovieAdapter adapter = new MovieAdapter(moviesList, context);
			lvMovies.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		hideProgressDialog();

	}

	/*
	 * Method to show the details of the selected movie in a dialog
	 */
	private void showMovieDetais(Movie movie) {
		// custom dialog
		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.movie_details_view);

		// set the custom dialog components
		TextView txtMovieName = (TextView) dialog.findViewById(R.id.movie_name);
		TextView txtGenre = (TextView) dialog.findViewById(R.id.txt_genre);
		TextView txtYear = (TextView) dialog.findViewById(R.id.txt_year);
		LinearLayout castLay = (LinearLayout) dialog
				.findViewById(R.id.cast_lay);

		ImageView star1 = (ImageView) dialog.findViewById(R.id.star1);
		ImageView star2 = (ImageView) dialog.findViewById(R.id.star2);
		ImageView star3 = (ImageView) dialog.findViewById(R.id.star3);
		ImageView star4 = (ImageView) dialog.findViewById(R.id.star4);
		ImageView star5 = (ImageView) dialog.findViewById(R.id.star5);
		ImageView star6 = (ImageView) dialog.findViewById(R.id.star6);
		ImageView star7 = (ImageView) dialog.findViewById(R.id.star7);
		ImageView star8 = (ImageView) dialog.findViewById(R.id.star8);
		ImageView star9 = (ImageView) dialog.findViewById(R.id.star9);
		ImageView star10 = (ImageView) dialog.findViewById(R.id.star10);

		// append [Fiction] to the movie name if it is a fiction type
		if (!movie.isFiction()) {
			txtMovieName.setText(movie.getName());
		} else {
			txtMovieName.setText(movie.getName() + " [Fiction]");
		}

		txtGenre.setText(movie.getGenre());
		txtYear.setText(movie.getYear() + "");

		// create textviews dynamically to show the cast names
		for (int i = 0; i < movie.getCastList().size(); i++) {
			TextView textview = new TextView(context);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					new LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			params.setMargins(0, 10, 0, 0);
			textview.setLayoutParams(params);
			textview.setText(movie.getCastList().get(i).getCastName());
			textview.setTextSize(14);
			textview.setTextColor(Color.BLACK);
			castLay.addView(textview);
		}

		// The attribute name "Score" is treated as "Ratings", ratings are
		// displayed in stars accroding to the score
		switch (movie.getScore()) {
		case 1:
			star1.setBackgroundResource(R.drawable.ic_star_filled_red);
			break;
		case 2:
			star1.setBackgroundResource(R.drawable.ic_star_filled_red);
			star2.setBackgroundResource(R.drawable.ic_star_filled_red);
			break;
		case 3:
			star1.setBackgroundResource(R.drawable.ic_star_filled_red);
			star2.setBackgroundResource(R.drawable.ic_star_filled_red);
			star3.setBackgroundResource(R.drawable.ic_star_filled_red);
			break;
		case 4:
			star1.setBackgroundResource(R.drawable.ic_star_filled_red);
			star2.setBackgroundResource(R.drawable.ic_star_filled_red);
			star3.setBackgroundResource(R.drawable.ic_star_filled_red);
			star4.setBackgroundResource(R.drawable.ic_star_filled_red);
			break;
		case 5:
			star1.setBackgroundResource(R.drawable.ic_star_filled_red);
			star2.setBackgroundResource(R.drawable.ic_star_filled_red);
			star3.setBackgroundResource(R.drawable.ic_star_filled_red);
			star4.setBackgroundResource(R.drawable.ic_star_filled_red);
			star5.setBackgroundResource(R.drawable.ic_star_filled_red);
			break;
		case 6:
			star1.setBackgroundResource(R.drawable.ic_star_filled_red);
			star2.setBackgroundResource(R.drawable.ic_star_filled_red);
			star3.setBackgroundResource(R.drawable.ic_star_filled_red);
			star4.setBackgroundResource(R.drawable.ic_star_filled_red);
			star5.setBackgroundResource(R.drawable.ic_star_filled_red);
			star6.setBackgroundResource(R.drawable.ic_star_filled_red);
			break;
		case 7:
			star1.setBackgroundResource(R.drawable.ic_star_filled_red);
			star2.setBackgroundResource(R.drawable.ic_star_filled_red);
			star3.setBackgroundResource(R.drawable.ic_star_filled_red);
			star4.setBackgroundResource(R.drawable.ic_star_filled_red);
			star5.setBackgroundResource(R.drawable.ic_star_filled_red);
			star6.setBackgroundResource(R.drawable.ic_star_filled_red);
			star7.setBackgroundResource(R.drawable.ic_star_filled_red);
			break;
		case 8:
			star1.setBackgroundResource(R.drawable.ic_star_filled_red);
			star2.setBackgroundResource(R.drawable.ic_star_filled_red);
			star3.setBackgroundResource(R.drawable.ic_star_filled_red);
			star4.setBackgroundResource(R.drawable.ic_star_filled_red);
			star5.setBackgroundResource(R.drawable.ic_star_filled_red);
			star6.setBackgroundResource(R.drawable.ic_star_filled_red);
			star7.setBackgroundResource(R.drawable.ic_star_filled_red);
			star8.setBackgroundResource(R.drawable.ic_star_filled_red);
			break;
		case 9:
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
		case 10:
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

		LinearLayout dialogButton = (LinearLayout) dialog
				.findViewById(R.id.btn_ok);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}

}
