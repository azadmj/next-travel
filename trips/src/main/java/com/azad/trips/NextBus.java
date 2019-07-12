package com.azad.trips;

import java.util.Arrays;

import com.azad.trips.exception.ApplicationException;
import com.azad.trips.exception.RouteNotFoundException;
import com.azad.trips.service.NextBusFinderService;

/**
 * Next Bus App - Rest Client for MetroTransit
 *@author azad
 */
public class NextBus {
	
	public static void main(String[] args) {
		
		if (args.length < 3) {
			throw new ApplicationException("invalid options, usage - NextBus \"BUS ROUTE\" \"BUS STOP NAME\" \"DIRECTION\"");
		}
		System.setProperty("user.timezone", "US/Central");
		System.out.println("-----------------------------------------------------------------------------------------------");
		System.out.println("Next Bus App - Rest Client for MetroTransit - Running with inputs -- ");
		Arrays.stream(args).forEach(System.out::println);
		System.out.println("-----------------------------------------------------------------------------------------------");
		NextBusFinderService busFinder = new NextBusFinderService();
		Long nextBusDepartureTime = -1l;
		try {
			nextBusDepartureTime = busFinder.timeToNextBus(args[0], args[1], args[2]);
		} catch (ApplicationException | RouteNotFoundException e) {
			System.err.println("FATAL ERROR - " + e.getMessage());
		}
		if(nextBusDepartureTime < 0) {
			System.out.println("No Bus available");
		} else {
			System.out.println(nextBusDepartureTime + " Minutes");
		}
	}
}
