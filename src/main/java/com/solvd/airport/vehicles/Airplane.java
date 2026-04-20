package com.solvd.airport.vehicles;

import com.solvd.airport.records.VehicleModel;
import com.solvd.airport.utils.AirportUtils;
import com.solvd.airport.interfaces.IFly;
import com.solvd.airport.interfaces.IMeasure;

import java.util.LinkedList;

import static com.solvd.airport.Main.LOGGER;

public class Airplane extends Aircraft implements IFly, IMeasure {

    public Airplane(String aircraftId, VehicleModel aircraftModel, int numSeats, int cols, int rows) {
        super(aircraftId, aircraftModel, numSeats, cols, rows);
    }

    @Override
    public String fly() {
        return "Flying!";
    }

    @Override
    public String measureTemperature() {
        String msg = "Temperature is bad!";
        if(AirportUtils.random.nextBoolean()) msg = "Temperature is OK!";
        return msg;
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
        LOGGER.info("Airplane parked!");
    }
}
