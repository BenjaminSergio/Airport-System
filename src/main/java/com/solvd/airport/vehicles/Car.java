package com.solvd.airport.vehicles;

import com.solvd.airport.records.VehicleModel;

import java.util.LinkedList;

import static com.solvd.airport.Main.LOGGER;

public class Car extends Vehicle{
    public Car(String vehicleId, VehicleModel vehicleModel) {
        super(vehicleId, vehicleModel);
    }

    @Override
    public String drive(Vehicle vehicle) {
        return "Driving";
    }

    @Override
    public void park(LinkedList garage) {
        if((garage == null) || (this.isParked())) {
            LOGGER.error("Error - invalid Operation");
            return;
        }
        LOGGER.info("Car parked!");

    }
}
