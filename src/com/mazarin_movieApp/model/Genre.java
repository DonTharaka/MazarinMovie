/**
 * Copyright (C) 2015 - Mazarin Pvt Ltd
 * @author Tharaka Nirmana
 */
package com.mazarin_movieApp.model;

import java.io.Serializable;

public class Genre implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String genre;

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
}
