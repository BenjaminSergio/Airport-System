package main.java.com.solvd.airport.interfaces;

import main.java.com.solvd.airport.vehicles.Vehicle;

public interface IDrive {
    //Every class that is seen to drive a vehicle must implement
    //Intended to be used to implement every vehicle functionality
    String drive(Vehicle vehicle);

}
