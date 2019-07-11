package com.azad.trips.service;

import org.junit.Assert;
import org.junit.Test;

import com.azad.trips.consts.URLEnum;
import com.azad.trips.exception.ApplicationException;

public class NextTripApiConsumerTest {

	@Test(expected = ApplicationException.class)
	public void test_restClient_invalid_url() {
		new NextTripApiConsumer().executeHttpGet(URLEnum.UNKNOWN.url());
	}

	@Test
	public void test_restClient_200_response_for_providers() {
		Assert.assertNotEquals("Failed", new NextTripApiConsumer().executeHttpGet(URLEnum.PROVIDERS.url()));
	}

	@Test
	public void test_api_response_Route_has_MetroBlueLine() {
		Assert.assertNotEquals("Failed", new NextTripApiConsumer().executeHttpGet(URLEnum.ROUTES.url()));
	}

}
