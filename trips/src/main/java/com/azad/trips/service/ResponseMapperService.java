package com.azad.trips.service;

import java.io.IOException;
import java.util.List;

import com.azad.trips.exception.ApplicationException;
import com.azad.trips.model.Departure;
import com.azad.trips.model.Response;
import com.azad.trips.model.Route;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class ResponseMapperService {
	
	/**
	 * Parse the API response to Given Type (Model Class)
	 * @param apiURL
	 * @return <T> T
	 */
	public <T> T getRoutes(String apiURL, Class<T> valueType) {
		String jsonString = new NextTripApiConsumer().executeHttpGet(apiURL);
		System.out.println("aksjgfalskjfgaslfkagsF    >>>> "+jsonString);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		T routes;
		try {
			routes = mapper.readValue(jsonString, new TypeReference<List<?>>(){});
		} catch (IOException e) {
			throw new ApplicationException("While Parsing the ROUTES Response - "+ e.getMessage());
		}
		return routes;
	}
	
	private ObjectMapper mapper = new ObjectMapper();
	
	public ResponseMapperService( ) {
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
	}
	
	/**
	 * Parse the API response to {@link Route} Model Class
	 * @param apiURL
	 * @return List<Route>
	 */
	public List<Route> findRoutes(String apiURL) {
		String jsonString = jsonResponse(apiURL);
		List<Route> routes;
		try {
			routes = mapper.readValue(jsonString, new TypeReference<List<Route>>(){});
		} catch (IOException e) {
			throw new ApplicationException("While Parsing the ROUTE Response - "+ e.getMessage());
		}
		return routes;
	}
	
	/**
	 * Parse the API response to {@link Response} Model Class
	 * @param apiURL
	 * @return List<Response>
	 */
	public List<Response> populateResponse(String apiURL) {
		String jsonString = jsonResponse(apiURL);
		List<Response> response;
		try {
			response = mapper.readValue(jsonString, new TypeReference<List<Response>>(){});
		} catch (IOException e) {
			throw new ApplicationException("While Parsing the DIRECTIONS/STOPS Response - "+e.getMessage());
		}
		return response;
	}
	
	/**
	 * Parse the API Departure to {@link Departure} Model Class
	 * @param apiURL
	 * @return List<Response>
	 */
	public List<Departure> findTimeDepartures(String apiURL) {
		String jsonString = xmlResponse(apiURL, "application/xml");
		mapper = new XmlMapper();
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		List<Departure> departure;
		try {
			departure = mapper.readValue(jsonString, new TypeReference<List<Departure>>(){});
		} catch (IOException e) {
			throw new ApplicationException("While Parsing the TIME-SCHEDULE Response - " + e.getMessage());
		}
		return departure;
	}
	
	private String jsonResponse(String apiURL) {
		return new NextTripApiConsumer().executeHttpGet(apiURL);
	}
	
	private String xmlResponse(String apiURL, String contentType) {
		return new NextTripApiConsumer().executeHttpGet(apiURL, contentType);
	}
}