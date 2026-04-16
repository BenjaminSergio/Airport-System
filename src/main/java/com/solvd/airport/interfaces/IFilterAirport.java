package com.solvd.airport.interfaces;

import com.solvd.airport.places.Airport;

@FunctionalInterface
public interface IFilterAirport {
    boolean test(Airport airpoty);
}
