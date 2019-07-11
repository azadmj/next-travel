package com.azad.trips;

import com.azad.trips.exception.ApplicationException;
import com.azad.trips.exception.RouteNotFoundException;
import com.azad.trips.service.NextBusFinderService;

/**
 * Next Bus App - Rest Client for MetroTransit
 *
 */
public class NextBus {
	
	public static void main(String[] args) {
		System.out.println("Next Bus App - Rest Client for MetroTransit");
		System.setProperty("user.timezone", "US/Central");
		if (args.length < 3) {
			throw new ApplicationException("invalid options, usage - NextBus \"BUS ROUTE\" \"BUS STOP NAME\" \"DIRECTION\"");
		}
		NextBusFinderService busFinder = new NextBusFinderService();
		Long nextBusDepartureTime = -1l;
		try {
			nextBusDepartureTime = busFinder.timeToNextBus(args[0], args[1], args[2]);
		} catch (ApplicationException | RouteNotFoundException e) {
			e.printStackTrace();
		}
		if(nextBusDepartureTime < 0) {
			System.out.println("No Bus available");
		} else {
			System.out.println(nextBusDepartureTime + " Minutes");
		}
	}
	
}
