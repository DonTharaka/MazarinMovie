/**
 * Copyright (C) 2015 - Mazarin Pvt Ltd
 * @author Tharaka Nirmana
 */
package com.mazarin_movieApp.model;

import java.io.Serializable;
import java.util.ArrayList;


public class Movie implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	String name;
	String genre;
	int score;
	int year;
	boolean fiction;
	ArrayList<Cast> castList = new ArrayList<Cast>();
	@Override
	public String toString() {
		return "Movie [name=" + name + ", genre=" + genre + ", score=" + score
				+ ", year=" + year + ", fiction=" + fiction + ", castList="
				+ castList + "]";
	}
	public Movie(String name, String genre, int score, int year,
			boolean fiction, ArrayList<Cast> castList) {
		super();
		this.name = name;
		this.genre = genre;
		this.score = score;
		this.year = year;
		this.fiction = fiction;
		this.castList = castList;
	}
	public Movie() {
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public boolean isFiction() {
		return fiction;
	}
	public void setFiction(boolean fiction) {
		this.fiction = fiction;
	}
	public ArrayList<Cast> getCastList() {
		return castList;
	}
	public void setCastList(ArrayList<Cast> castList) {
		this.castList = castList;
	}
	
}
