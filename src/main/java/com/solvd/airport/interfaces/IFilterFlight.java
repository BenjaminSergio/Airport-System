package com.solvd.airport.interfaces;

import com.solvd.airport.systens.Flight;

@FunctionalInterface
public interface IFilterFlight {
    boolean test(Flight flight);
}
