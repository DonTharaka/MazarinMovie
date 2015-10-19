/**
 * Copyright (C) 2015 - Mazarin Pvt Ltd
 * @author Tharaka Nirmana
 */
package com.mazarin_movieApp.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mazarin_movieApp.R;
import com.mazarin_movieApp.model.Movie;

/*
 * This adapter populates the listview of all movies, with a custom list row
 */
public class MovieAdapter extends BaseAdapter {

	Context context;
	List<Movie> movieList;
	LayoutInflater lInflater;

	public MovieAdapter(List<Movie> movieList, Context c) {
		this.movieList = movieList;
		this.context = c;
		lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return movieList.size();
	}

	@Override
	public Object getItem(int position) {
		return movieList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = lInflater.inflate(R.layout.movie_list_row, parent, false);
		}
		Movie movie = movieList.get(position);

		TextView movieName = (TextView) view.findViewById(R.id.txt_name);
		TextView genre = (TextView) view.findViewById(R.id.txt_genre);
		TextView rating = (TextView) view.findViewById(R.id.txt_ratings);
		
		movieName.setText(movie.getName() + " (" + movie.getYear() + ")");
		genre.setText(movie.getGenre());
		rating.setText(movie.getScore() + "/" + "10");

		return view;
	}

}

