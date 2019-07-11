package com.azad.trips.service;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.azad.trips.exception.RouteNotFoundException;
import com.azad.trips.model.Route;

public class RouteServiceTest {

	@Test
	public void test_validResponse() {
		final String expectedRoute = "METRO Blue Line";
		try {
			Route route = new RouteService().findRoute(expectedRoute);
			Assert.assertEquals(expectedRoute, route.getDescription());
		} catch (IOException e) {
			e.printStackTrace();
			fail("failed to find Route");
		}
	}

	@Test(expected = RouteNotFoundException.class)
	public void test_inValidResponse() throws IOException {
		final String expectedRoute = "METRO Blue1 Line";
		Route route = new RouteService().findRoute(expectedRoute);
		Assert.assertEquals(expectedRoute, route.getDescription());
	}

}
