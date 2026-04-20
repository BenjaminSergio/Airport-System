package com.solvd.airport.vehicles;

import com.solvd.airport.exceptions.TemperatureMeasuringException;
import com.solvd.airport.records.VehicleModel;
import com.solvd.airport.utils.AirportUtils;
import com.solvd.airport.interfaces.IMeasure;

import java.util.LinkedList;

import static com.solvd.airport.Main.LOGGER;

public class WeatherBalloon extends Aircraft implements IMeasure {
    public WeatherBalloon(String aircraftId, VehicleModel aircraftModel, int numSeats, int cols, int rows) {
        super(aircraftId, aircraftModel, numSeats, cols, rows);
    }
    @Override
    public String measureTemperature() throws TemperatureMeasuringException {
        String msg = "Temperature is bad!";
        if(AirportUtils.random.nextBoolean()) msg = "Temperature is OK!";
        else throw new TemperatureMeasuringException("Bad Temperature Measuring");
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
        LOGGER.info("Weather Balloon parked!");

    }
}
