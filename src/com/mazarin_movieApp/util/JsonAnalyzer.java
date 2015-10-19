/**
 * Copyright (C) 2015 - Mazarin Pvt Ltd
 * @author Tharaka Nirmana
 */
package com.mazarin_movieApp.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mazarin_movieApp.model.Cast;
import com.mazarin_movieApp.model.Movie;

/*
 * This class parses all json responses
 */
public class JsonAnalyzer {
	
	/*
	 * Creates a single array list based on the model class for Movie
	 */
	public static ArrayList<Movie> parseMoviesJson(JSONArray movieJArray) throws JSONException {
		ArrayList<Movie> moviesList = new ArrayList<Movie>();
		//movie json array is looped first and movie object is created and data is bound
		for(int i = 0; i < movieJArray.length(); i++){
			JSONObject movieObj = movieJArray.getJSONObject(i);
			Movie movie = new Movie();
			movie.setName(movieObj.getString("name"));
			movie.setGenre(movieObj.getString("genre"));
			movie.setScore(movieObj.getInt("score"));
			movie.setYear(movieObj.getInt("year"));
			movie.setFiction(movieObj.getBoolean("fiction"));
			
			//cast Jarray of each movie Jobject is then taken out and looped 
			JSONArray castJArray = movieObj.getJSONArray("cast");
			
			for(int x = 0; x < castJArray.length(); x++){
				String castJObj = castJArray.getString(x);
				Cast cast = new Cast();
				cast.setCastName(castJObj);
				movie.getCastList().add(cast);
				
			}
			
			moviesList.add(movie);
			
		}
		return moviesList;
	}
	
	/*
	 * Created one single string array list after parsing Genre Array list
	 */
	public static ArrayList<String> parseGenreJson(JSONArray genreJArray) throws JSONException {
		ArrayList<String> genreList = new ArrayList<String>();
		for(int i = 0; i < genreJArray.length(); i++){
			String genreObj = genreJArray.getString(i);
			genreList.add(genreObj);
		}
		
		return genreList;
	}

}
