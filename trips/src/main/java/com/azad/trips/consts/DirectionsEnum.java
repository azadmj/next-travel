package com.azad.trips.consts;

import com.azad.trips.exception.RouteNotFoundException;

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

	public static DirectionsEnum lookup(String id) {
        try {
            return DirectionsEnum.valueOf(id);
        } catch (IllegalArgumentException e) {
            throw new RouteNotFoundException("Invalid Direction: " + id);
        }
    }

}
