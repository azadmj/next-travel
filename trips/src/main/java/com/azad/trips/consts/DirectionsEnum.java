package com.azad.trips.consts;

/**
 * Enum for Available directions
 * 1 = South, 2 = East, 3 = West, 4 = North
 * @author azad
 *
 */
public enum DirectionsEnum {
	SOUTH("1"),
	EAST("2"),
	WEST("3"),
	NORTH("4");
	
	
	private String id;
	
	private DirectionsEnum(String id) {
		this.id = id;
	}
	
	public String id() {
		return id;
	}

}
