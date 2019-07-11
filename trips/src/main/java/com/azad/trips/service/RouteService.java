package com.azad.trips.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.azad.trips.consts.URLEnum;
import com.azad.trips.exception.RouteNotFoundException;
import com.azad.trips.model.Route;

public class RouteService {
	
	private ResponseMapper apiClient = new ResponseMapper(); 
	
	public Route findRoute(String route) throws IOException {
		
		List<Route> availableRoutes = apiClient.findRoutes(URLEnum.ROUTES.url());
		List<Route> routeWithName = availableRoutes.stream()
		.filter(r -> r.hasMatchingRoutes(route))
		.distinct().collect(Collectors.toList());
		if(routeWithName.size() > 1 || routeWithName.size() < 1) 
			throw new RouteNotFoundException("More than one Route or None found");
		
		return routeWithName.get(0);
	}

}
