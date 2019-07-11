package com.azad.trips.model;

/**
 * POJO for JSON - "Description": "METRO A Line", "ProviderID": "8", "Route": "921"
 * 
 * @author azad
 *
 */

public class Route {
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
	
	public Boolean hasMatchingRoutes(String route) {
		return description.contains(route);//TODO a pattern bases search for the route
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
	
	

}
