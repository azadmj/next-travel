package com.azad.trips.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * JSON response mapper for NextTripDeparture "Actual": false, "BlockNumber": 9,
 * "DepartureText": "3:02", "DepartureTime": "/Date(1562788920000-0500)/",
 * "Description": "to Mall of America", "Gate": "2", "Route": "Blue",
 * "RouteDirection": "SOUTHBOUND", "Terminal": "", "VehicleHeading": 0,
 * "VehicleLatitude": 0, "VehicleLongitude":
 * 
 * @author azad
 *
 */
public class Departure implements Comparable<Departure> {

	private boolean actual;
	private Integer blockNumber;
	@JsonFormat(pattern = "h:mm")
	private String departureText;
	private String departureTime;
	private String description;
	private Integer gate;
	private String route;
	private String routeDirection;
	private String terminal;
	private Integer vehicleHeading;
	private Integer vehicleLatitude;
	private Integer vehicleLongitude;
	private Long timeToNextBus;

	public boolean isActual() {
		return actual;
	}

	public void setActual(boolean actual) {
		this.actual = actual;
	}

	public Integer getBlockNumber() {
		return blockNumber;
	}

	public void setBlockNumber(Integer blockNumber) {
		this.blockNumber = blockNumber;
	}

	public String getDepartureText() {
		return departureText;
	}

	public void setDepartureText(String departureText) {
		this.departureText = departureText;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getGate() {
		return gate;
	}

	public void setGate(Integer gate) {
		this.gate = gate;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getRouteDirection() {
		return routeDirection;
	}

	public void setRouteDirection(String routeDirection) {
		this.routeDirection = routeDirection;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getVehicleHeading() {
		return vehicleHeading;
	}

	public void setVehicleHeading(Integer vehicleHeading) {
		this.vehicleHeading = vehicleHeading;
	}

	public Integer getVehicleLatitude() {
		return vehicleLatitude;
	}

	public void setVehicleLatitude(Integer vehicleLatitude) {
		this.vehicleLatitude = vehicleLatitude;
	}

	public Integer getVehicleLongitude() {
		return vehicleLongitude;
	}

	public void setVehicleLongitude(Integer vehicleLongitude) {
		this.vehicleLongitude = vehicleLongitude;
	}

	public Long getTimeToNextBus() {
		return timeToNextBus;
	}

	public void setTimeToNextBus(Long timeToNextBus) {
		this.timeToNextBus = timeToNextBus;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Departure [actual=");
		builder.append(actual);
		builder.append(", blockNumber=");
		builder.append(blockNumber);
		builder.append(", departureText=");
		builder.append(departureText);
		builder.append(", departureTime=");
		builder.append(departureTime);
		builder.append(", description=");
		builder.append(description);
		builder.append(", gate=");
		builder.append(gate);
		builder.append(", route=");
		builder.append(route);
		builder.append(", routeDirection=");
		builder.append(routeDirection);
		builder.append(", terminal=");
		builder.append(terminal);
		builder.append(", vehicleHeading=");
		builder.append(vehicleHeading);
		builder.append(", vehicleLatitude=");
		builder.append(vehicleLatitude);
		builder.append(", vehicleLongitude=");
		builder.append(vehicleLongitude);
		builder.append(", timeToNextBus=");
		builder.append(timeToNextBus);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * {@link Predicate} Method for the time difference between now and the schedule. 
	 * @param now
	 * @return
	 */
	public boolean hasScheduleAfterNow(Date now) {
		try {
			DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date departureDateTime = dtf.parse(departureTime);
			long timeDiff = departureDateTime.getTime() - now.getTime();
			timeToNextBus = TimeUnit.MINUTES.convert(timeDiff, TimeUnit.MILLISECONDS);
			if (departureDateTime.after(now))
				return true;
		} catch (ParseException e) {
			System.err.println("While Parsing Findind the nearest BUS TIME - " + e.getMessage());
		}
		return false;
	}

	@Override
	public int compareTo(Departure o) {
		if(this.timeToNextBus < o.getTimeToNextBus())
			return -1;
		if (this.timeToNextBus > o.getTimeToNextBus())
			return 1;
		return 0;
	}
}
