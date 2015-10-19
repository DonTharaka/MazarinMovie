/**
 * Copyright (C) 2015 - Mazarin Pvt Ltd
 * @author Tharaka Nirmana
 */
package com.mazarin_movieApp.service;

import org.apache.http.Header;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mazarin_movieApp.AddMovieActivity;
import com.mazarin_movieApp.MainActivity;
import com.mazarin_movieApp.util.Config;

/*
 * Service class where all parameters are connected with key values and also this class listens after a request is sent : waits for the response
 */
public class MovieWebService {
	
	/*
	 * Service call that is sent to get all movie genres
	 */
	public static ReturnTypes getMovieGenres(final AddMovieActivity context) {
		MovieServiceClient.get(Config.GET_GENRE, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray arg0) {
				Log.e("getMovieGenres details", arg0.toString());
				context.didReceivegetMovieGenresDetails(arg0);

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable e, JSONObject errorResponse) {
				Log.e("errorResponse ", "" + errorResponse);
				super.onFailure(statusCode, headers, e, errorResponse);
			}
			
			@Override
			public void onFailure(Throwable error, String content) {
			    if ( error.getCause() instanceof ConnectTimeoutException ) {
			    	context.didReceivegetMovieGenresDetails(null);
			    }
			}

		});
		return ReturnTypes.SUCCESS;

	}

	/*
	 * Service call to add a movie.
	 */
	public static ReturnTypes addMovie(final AddMovieActivity context,
			String name, String year, String fiction, String genre,
			String cast, String score) {

		MovieServiceClient.post(
				Config.MOVIE,
				getAddMovieParams(context, name, year, fiction, genre, cast,
						score), new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(JSONObject arg0) {
						

					}

					@Override
					public void onSuccess(int statusCode, Header[] headers, String responseBody) {
						Log.e("addMovie details", responseBody);
						context.didReceivAddMovieDetails(responseBody);
						super.onSuccess(statusCode, headers, responseBody);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable e, JSONObject errorResponse) {
						super.onFailure(statusCode, headers, e, errorResponse);
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
					    if ( error.getCause() instanceof ConnectTimeoutException ) {
					    	context.didReceivAddMovieDetails(null);
					    }
					}

				});
		return ReturnTypes.SUCCESS;

	}
	/*
	 * Parameter binding of add movie request
	 */
	private static RequestParams getAddMovieParams(AddMovieActivity context,
			String name, String year, String fiction, String genre,
			String cast, String score) {
		RequestParams params = new RequestParams();
		params.put("name", name);
		params.put("year", year);
		params.put("fiction", fiction);
		params.put("genre", genre);
		params.put("cast", cast);
		params.put("score", score);
		return params;
	}

	/*
	 * Service call to get all movies
	 */
	public static ReturnTypes getAllMovies(final MainActivity context) {
		MovieServiceClient.get(Config.MOVIE, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray arg0) {
				Log.e("getMovieGenres details", arg0.toString());
				context.didReceivegetMovieDetails(arg0);

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable e, JSONObject errorResponse) {
				Log.e("errorResponse ", "" + errorResponse);
				super.onFailure(statusCode, headers, e, errorResponse);
			}
			
			@Override
			public void onFailure(Throwable error, String content) {
			    if ( error.getCause() instanceof ConnectTimeoutException ) {
			    	context.didReceivegetMovieDetails(null);
			    }
			}

		});
		return ReturnTypes.SUCCESS;
		
	}
	
	
}
