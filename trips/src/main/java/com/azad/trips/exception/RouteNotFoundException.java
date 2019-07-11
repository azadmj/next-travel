package com.azad.trips.exception;

public class RouteNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public RouteNotFoundException() {
		super();
	}

	public RouteNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public RouteNotFoundException(String message) {
		super(message);
	}
	
	

}
