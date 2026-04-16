package com.solvd.airport.vehicles;

import com.solvd.airport.exceptions.TemperatureMeasuringException;
import com.solvd.airport.utils.AirportUtils;
import com.solvd.airport.interfaces.IMeasure;

public class WeatherBalloon extends Aircraft implements IMeasure {
    public WeatherBalloon(String aircraftId, String aircraftModel, int numSeats, int cols, int rows) {
        super(aircraftId, aircraftModel, numSeats, cols, rows);
    }
    @Override
    public String measureTemperature() throws TemperatureMeasuringException {
        String msg = "Temperature is bad!";
        if(AirportUtils.random.nextBoolean()) msg = "Temperature is OK!";
        else throw new TemperatureMeasuringException("Bad Temperature Measuring");
        return msg;
    }
}
