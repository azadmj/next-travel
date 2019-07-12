package com.azad.trips.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.azad.trips.exception.RouteNotFoundException;

public class NextBusFinderServiceTest {

	private NextBusFinderService finder = new NextBusFinderService();
	
	final String route = "METRO Blue Line";
	final String stopName = "Target Field Station Platform 1";
	final String direction = "south";
	
	@BeforeClass
	public static void setup() {
		System.setProperty("user.timezone", "US/Central");
	}

	@Test
	public void test_nextScheule_success() {
		assertEquals("when TIMEZONE is IST Tests will fail", "US/Central", System.getProperty("user.timezone"));
		Long timeForNextBus = finder.timeToNextBus(route, stopName, direction);
		assertTrue(timeForNextBus > 0);
	}

	@Test(expected = RouteNotFoundException.class)
	public void test_nextScheule_invalidRoute() {
		final String route = "METRO Blue Line1";
		finder.timeToNextBus(route, stopName, direction);
	}

	@Test(expected = RouteNotFoundException.class)
	public void test_nextScheule_invalidStopName() {
		final String stopName = "Target Field Station Platform 11";
		finder.timeToNextBus(route, stopName, direction);
	}
	
	@Test(expected = RouteNotFoundException.class)
	public void test_nextScheule_invalidDirection() {
		final String direction = "southbound";
		finder.timeToNextBus(route, stopName, direction);
	}

}
