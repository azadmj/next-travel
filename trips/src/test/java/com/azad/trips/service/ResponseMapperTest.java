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

public class ResponseMapperTest {

	@Test
	public void test_Route_validRespose() {
		List<Route> allRoutes = new ResponseMapper().findRoutes(URLEnum.ROUTES.url());
		Assert.assertFalse(allRoutes.isEmpty());
	}

	@Test
	public void test_Provider_validRespose() {
		List<Response> allProvider = new ResponseMapper().populateResponse(URLEnum.PROVIDERS.url());
		Assert.assertFalse(allProvider.isEmpty());
	}

	@Test
	public void test_Direction_validRespose() {
		final Integer metroBlueLineRouteId = 901;
		List<Response> allProvider = new ResponseMapper()
				.populateResponse(URLEnum.DIRECTIONS.url() + metroBlueLineRouteId);
		assertNotNull(allProvider.get(0));
	}

	@Test
	public void test_XMLResponseMapper_validTimedepartures() throws IOException, ParseException {
		/*
		 * String xmlResponse = "<ArrayOfNexTripDeparture>" + "	<NexTripDeparture>" +
		 * "		<Actual>false</Actual>" + "		<BlockNumber>3</BlockNumber>" +
		 * "		<DepartureText>4:00</DepartureText>" +
		 * "		<DepartureTime>2019-07-11T04:00:00</DepartureTime>" +
		 * "		<Description>to Mall of America</Description>" +
		 * "		<Gate>2</Gate>" + "		<Route>Blue</Route>" +
		 * "		<RouteDirection>SOUTHBOUND</RouteDirection>" +
		 * "		<Terminal/><VehicleHeading>0</VehicleHeading>" +
		 * "		<VehicleLatitude>0</VehicleLatitude>" +
		 * "		<VehicleLongitude>0</VehicleLongitude>" + "	</NexTripDeparture>" +
		 * "</ArrayOfNexTripDeparture>";
		 */
		final Integer metroBlueLineRouteId = 901;
		final Integer directionId = 1;
		final String stopId = "TF22";
		List<Departure> allDepartureTime = new ResponseMapper().findTimeDepartures(
				URLEnum.TIME_POINT_DEPARTURES.url() + metroBlueLineRouteId + "/" + directionId + "/" + stopId);
		Departure expectedResults = allDepartureTime.get(0);
		System.out.println("DEP: " + expectedResults);
		assertEquals(expectedResults.getRouteDirection(), "SOUTHBOUND");
		assertTrue(expectedResults.getRoute().contains("Blue"));
	}

}
