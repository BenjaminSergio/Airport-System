package com.solvd.airport.interfaces;

import com.solvd.airport.vehicles.Vehicle;

import java.util.LinkedList;

public interface IPark <T extends Vehicle> {
    void park(LinkedList<T> garage);
}
