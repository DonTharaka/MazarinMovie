/**
 * Copyright (C) 2015 - Mazarin Pvt Ltd
 * @author Tharaka Nirmana
 */
package com.mazarin_movieApp.service;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mazarin_movieApp.util.Config;

/*
 * Service client that handles POST / GET requests. Timeout and the actual URL is created in this class
 */
public class MovieServiceClient {
	
	
	private static AsyncHttpClient client = new AsyncHttpClient();

	public static void get(String url, AsyncHttpResponseHandler responseHandler) {
		client.setTimeout(Config.DEFAULT_TIMEOUT);
		client.get(getAbsoluteUrl(url), responseHandler);
	}

	public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.setTimeout(Config.DEFAULT_TIMEOUT);
		Log.e("Params", params+"");
		client.post(getAbsoluteUrl(url) +"?"+ params, responseHandler);
	}
	public static void post(String url, AsyncHttpResponseHandler responseHandler) {
		client.setTimeout(Config.DEFAULT_TIMEOUT);
		client.post(getAbsoluteUrl(url), responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return Config.SERVICE_URL + relativeUrl;
	}

}
