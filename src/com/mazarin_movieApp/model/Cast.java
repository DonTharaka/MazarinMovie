/**
 * Copyright (C) 2015 - Mazarin Pvt Ltd
 * @author Tharaka Nirmana
 */
package com.mazarin_movieApp.model;

import java.io.Serializable;

public class Cast  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String castName;

	@Override
	public String toString() {
		return "Cast [castName=" + castName + "]";
	}

	public Cast(String castName) {
		super();
		this.castName = castName;
	}

	public Cast() {
		
	}

	public String getCastName() {
		return castName;
	}

	public void setCastName(String castName) {
		this.castName = castName;
	}
	
}
