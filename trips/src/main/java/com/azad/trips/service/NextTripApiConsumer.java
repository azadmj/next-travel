package com.azad.trips.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.azad.trips.exception.ApplicationException;

/**
 * Rest API Client for MetroTransit
 * @author azad
 *
 */
public class NextTripApiConsumer {

	private static final String DEFAULT_CONTENT_TYPE = "application/json";
	/**
	 * REST API Call - designed only for Get. MetroTransit supports only GET 
	 * @param String apiURL
	 * @return jsonString
	 */
	public String executeHttpGet(String apiURL, String contentType) {
		String jsonString = new String();
		CloseableHttpClient httpClient = null;
		BufferedReader bufferR = null;
		CloseableHttpResponse response = null;
		try {
			httpClient = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(apiURL);
			if(contentType == null || contentType.isEmpty())
				getRequest.addHeader(HttpHeaders.ACCEPT, DEFAULT_CONTENT_TYPE);
			else 
				getRequest.addHeader(HttpHeaders.ACCEPT, contentType);
			response = httpClient.execute(getRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new ApplicationException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				//TODO create a wrapper object to contain status and response together.
			}

			bufferR = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			jsonString = bufferR.lines().collect(Collectors.joining("\n"));

		} catch (ClientProtocolException e) {
			throw new ApplicationException("Invalid URL - "+e.getMessage());
		} catch (IOException e) {
			throw new ApplicationException("Interrupted I/O operations - " + e.getMessage());
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
				}
				if (bufferR != null) {
					bufferR.close();
				}
				if (response != null) {
					response.close();
				}

			} catch (IOException e) {
				System.err.println("While closing the connection/stream - " +e.getMessage());
			}
		}
		return jsonString;
	}
	
	public String executeHttpGet(String apiURL) {
		return executeHttpGet(apiURL, null);
	}
}

