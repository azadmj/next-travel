package com.azad.trips.model;

import java.util.function.Predicate;

/** 
 * Class is used for {@link Predicate} used in filters
 * @author azad
 *
 */
public abstract class NextTripBase {
	
	public abstract Boolean hasMatching(String route);

}
