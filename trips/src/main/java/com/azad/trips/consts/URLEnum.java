package com.azad.trips.consts;

public enum URLEnum {
	PROVIDERS ("http://svc.metrotransit.org/nextrip/providers"),
	ROUTES("http://svc.metrotransit.org/NexTrip/Routes"),
	DIRECTIONS("http://svc.metrotransit.org/NexTrip/Directions/"),
	STOPS("http://svc.metrotransit.org/NexTrip/Stops/"),
	DEPARTURES(""),
	TIME_POINT_DEPARTURES("http://svc.metrotransit.org/NexTrip/"),
	VEHICLE_LOCATIONS(""),
	UNKNOWN("");
	
	
	private String url;
	
	private URLEnum(String url) {
		this.url = url;
	}
	
	public String url() {
		return url;
	}

}
