package com.azad.trips.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * POJO for JSON - "Description": "METRO A Line", "ProviderID": "8", "Route":
 * "921"
 * 
 * @author azad
 *
 */

public class Route extends NextTripBase implements Comparable<Route> {
	private Integer route;
	private Integer providerID;
	private String description;

	public Integer getRoute() {
		return route;
	}

	public void setRoute(Integer route) {
		this.route = route;
	}

	public Integer getProviderID() {
		return providerID;
	}

	public void setProviderID(Integer providerID) {
		this.providerID = providerID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Boolean hasMatching(String route) {
		Pattern pattern = Pattern.compile(route);
	    Matcher matcher = pattern.matcher(description);
	    return matcher.find();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Route [route=");
		builder.append(route);
		builder.append(", providerID=");
		builder.append(providerID);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int compareTo(Route o) {
		return this.route.compareTo(o.getRoute());
	}
}
