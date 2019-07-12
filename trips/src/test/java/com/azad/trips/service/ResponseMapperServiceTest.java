package com.azad.trips.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.azad.trips.consts.URLEnum;
import com.azad.trips.model.Departure;
import com.azad.trips.model.Response;
import com.azad.trips.model.Route;

public class ResponseMapperServiceTest {

	ResponseMapperService responseMapper =  new ResponseMapperService();
	@Test
	public void test_Route_validRespose() {
		List<Route> allRoutes = responseMapper.findRoutes(URLEnum.ROUTES.url());
		Assert.assertFalse(allRoutes.isEmpty());
	}

	@Test
	public void test_Provider_validRespose() {
		List<Response> allProvider = responseMapper.populateResponse(URLEnum.PROVIDERS.url());
		Assert.assertFalse(allProvider.isEmpty());
	}

	@Test
	public void test_Direction_validRespose() {
		final Integer metroBlueLineRouteId = 901;
		List<Response> allProvider = responseMapper.populateResponse(URLEnum.DIRECTIONS.url() + metroBlueLineRouteId);
		assertNotNull(allProvider.get(0));
	}

	@Test
	public void test_XMLResponseMapper_validTimedepartures_API_responseValidate() throws IOException, ParseException {
		final Integer metroBlueLineRouteId = 901;
		final Integer directionId = 1;
		final String stopId = "TF22";
		List<Departure> allDepartureTime = responseMapper.findTimeDepartures(
				URLEnum.TIME_POINT_DEPARTURES.url() + metroBlueLineRouteId + "/" + directionId + "/" + stopId);
		Departure expectedResults = allDepartureTime.get(0);
		System.out.println("DEP: " + expectedResults);
		assertEquals(expectedResults.getRouteDirection(), "SOUTHBOUND");
		assertTrue(expectedResults.getRoute().contains("Blue"));
	}

}
