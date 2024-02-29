package com.mobilefirst.bookreview.exception;

public class BookNotFoundException extends Exception {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String message;
public BookNotFoundException(String message) {
	super();
	this.message = message;
}

}
