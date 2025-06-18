package com.app.exceptions;

public class PreferencesNotFoundException extends RuntimeException{
	public PreferencesNotFoundException(String message) {
		super(message);
	}

}
