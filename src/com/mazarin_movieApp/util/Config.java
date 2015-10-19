/**
 * Copyright (C) 2015 - Mazarin Pvt Ltd
 * @author Tharaka Nirmana
 */
package com.mazarin_movieApp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Config {
	static Pattern pattern;
	static Matcher matcher;
	public static final int DEFAULT_TIMEOUT = 300 * 1000;

	public static final String pleaseWait = "Please Wait...";
	public static final String error = "Alert!";
	public static final String success = "Success!";
	public static final String noInternet = "Internet not available. Please enable internet and try again...";
	public static final String serverError = "Server error, please try again...";
	public static final String timeOut = "Request timed out";
	public static final String enter_movie_name = "Please enter movie name";
	public static final String enter_year = "Please enter the year of the movie";
	public static final String enter_valid_year = "Please enter a valid year";
	public static final String enter_valid_format_year = "Please enter a year in the correct format";
	public static final String select_genre_type = "Please select genre type";
	public static final String add_casts = "Please add atleast one cast";
	public static final String select_rating = "Please rate the movie by selecting stars";

	public static final String movie_added = "Movie saved successfully";
	public static final String add_cast_error = "Please enter the name of the cast before adding";
	public static final String movie_not_added = "Something went wrong when addding the movie";

	public static final String SERVICE_URL = "http://mobiletest.mazarin.lk/movieservice/rest/";
	public static final String GET_GENRE = "movies/genres/";
	public static final String MOVIE = "movies";

}
