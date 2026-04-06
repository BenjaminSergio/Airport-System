package main.java.com.solvd.airport.vehicles;

import main.java.com.solvd.airport.exceptions.TemperatureMeasuringException;
import main.java.com.solvd.airport.utils.AirportUtils;
import main.java.com.solvd.airport.interfaces.IMeasure;

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
