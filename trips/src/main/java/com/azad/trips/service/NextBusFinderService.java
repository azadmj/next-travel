package com.azad.trips.service;

import java.util.Calendar;
import java.util.List;
import java.util.SortedSet;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.azad.trips.consts.DirectionsEnum;
import com.azad.trips.consts.URLEnum;
import com.azad.trips.exception.ApplicationException;
import com.azad.trips.exception.RouteNotFoundException;
import com.azad.trips.model.Departure;
import com.azad.trips.model.Response;
import com.azad.trips.model.Route;

public class NextBusFinderService {

	private ResponseMapper responseMapper = new ResponseMapper();

	public Long timeToNextBus(String route, String stopName, String direction) throws ApplicationException {
		
		Route validRoute = validateRoute(route);
		System.out.println("VALID ROUTE :" + validRoute);
		Response validDirection = validateDirection(direction, validRoute.getRoute());
		System.out.println("VALID DIRECTION :" + validDirection);
		Response validStop = validateStop(stopName, validRoute.getRoute(), validDirection.getValue());
		System.out.println("VALID STOP :" + validStop);
		List<Departure> timeDepartures = timeDepartures(validStop.getValue(), validRoute.getRoute(), validDirection.getValue());
		System.out.println("Time Departures :" + timeDepartures);
		return nextDepartureTime(timeDepartures);
	}

	private Route validateRoute(String route) {
		List<Route> availableRoutes = responseMapper.findRoutes(URLEnum.ROUTES.url());
		System.out.println("Available Routes " + availableRoutes);
		List<Route> routeMatchName = availableRoutes.stream().filter(r -> r.hasMatchingRoutes(route)).distinct()
				.collect(Collectors.toList());
		if (routeMatchName.size() > 1 || routeMatchName.size() < 1)
			throw new RouteNotFoundException("More than one Route or None found for the Route: " + route);
		
		Route validRoute = routeMatchName.get(0);
		return validRoute;
	}

	private Response validateDirection(String direction, Integer routeId) {
		DirectionsEnum inputDirection = DirectionsEnum.valueOf(direction.toUpperCase());
		if(inputDirection == null) {
			throw new RouteNotFoundException("invalid Direction : "+direction);
		}
		List<Response> availableDirection = responseMapper.populateResponse(URLEnum.DIRECTIONS.url() + routeId);
		List<Response> directionMatchName = availableDirection.stream().filter(r -> r.hasMatchingValue(inputDirection.id())).distinct()
				.collect(Collectors.toList());
		if (directionMatchName.size() > 1 || directionMatchName.size() < 1)
			throw new RouteNotFoundException("More than one Direction or None found for the Direction: " + direction);

		return directionMatchName.get(0);
	}

	private Response validateStop(String stopName, Integer routeId, String directionId) {
		List<Response> availableStops = responseMapper.populateResponse(URLEnum.STOPS.url() + routeId + "/" + directionId);
		List<Response> stopMatchName = availableStops.stream().filter(r -> r.hasMatchingText(stopName)).distinct()
				.collect(Collectors.toList());
		if (stopMatchName.size() > 1 || stopMatchName.size() < 1)
			throw new RouteNotFoundException("More than one Stop or None found for the StopName: " + stopName);
		
		Response validStop = stopMatchName.get(0);
		return validStop;
	}
	
	private List<Departure> timeDepartures(String stopId, Integer routeId, String directionId) {
		List<Departure> availableDepartures = responseMapper.findTimeDepartures(URLEnum.TIME_POINT_DEPARTURES.url() + routeId + "/" + directionId + "/" + stopId);
		return availableDepartures;
	}
	
	private Long nextDepartureTime(List<Departure> schedule) {
		TimeZone usCentralZone = TimeZone.getTimeZone("US/Central");
		Calendar today = Calendar.getInstance();
		today.setTimeZone(usCentralZone);
		System.out.println("Now:: "+today.getTime() );
		SortedSet<Departure> scheduleAfterNow = schedule.stream()
												.filter(d -> d.hasScheduleAfterNow(today.getTime()))
												.distinct().collect(Collectors.toCollection(TreeSet::new));
		System.out.println("Actual schedule size " + schedule.size() + " After match:: " + " Size " + scheduleAfterNow.size() + " Data " + scheduleAfterNow);
		
		return scheduleAfterNow.first().getTimeToNextBus();
	}
}
