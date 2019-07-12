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
import com.azad.trips.model.NextTripBase;
import com.azad.trips.model.Response;
import com.azad.trips.model.Route;

public class NextBusFinderService {

	private ResponseMapperService responseMapper = new ResponseMapperService();

	public Long timeToNextBus(String route, String stopName, String direction) throws ApplicationException {
		
		Route validRoute = validateRoute(route);
		System.out.println("VALID ROUTE :" + validRoute);
		Response validDirection = validateDirection(direction, validRoute.getRoute());
		System.out.println("VALID DIRECTION :" + validDirection);
		Response validStop = validateStop(stopName, validRoute.getRoute(), validDirection.getValue());
		System.out.println("VALID STOP :" + validStop);
		List<Departure> timeDepartures = timeDepartures(validStop.getValue(), validRoute.getRoute(), validDirection.getValue());
		return nextDepartureTime(timeDepartures);
	}
	
	private <T> T searchWhatMatters(List<T> R, String searchText) {
		TreeSet<T> matches = R.stream().filter(o -> ((NextTripBase) o).hasMatching(searchText)).distinct()
				.collect(Collectors.toCollection(TreeSet::new));
		if (matches.size() > 1 || matches.size() < 1)
			throw new RouteNotFoundException("More than one Route or None found for the Route: ");
		return matches.first();
	}

	private Route validateRoute(String route) {
		List<Route> availableRoutes = responseMapper.findRoutes(URLEnum.ROUTES.url());
		Route validRoute = searchWhatMatters(availableRoutes, route);
		return validRoute;
	}

	private Response validateDirection(String direction, Integer routeId) {
		DirectionsEnum inputDirection = DirectionsEnum.lookup(direction.toUpperCase());
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
		if(scheduleAfterNow.isEmpty()) 
			return -1l;
		Departure nearDeparture = scheduleAfterNow.first();
		return nearDeparture.getTimeToNextBus();
	}
}
