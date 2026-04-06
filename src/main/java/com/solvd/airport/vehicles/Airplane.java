package main.java.com.solvd.airport.vehicles;

import main.java.com.solvd.airport.utils.AirportUtils;
import main.java.com.solvd.airport.interfaces.IFly;
import main.java.com.solvd.airport.interfaces.IMeasure;

public class Airplane extends Aircraft implements IFly, IMeasure {

    public Airplane(String aircraftId, String aircraftModel, int numSeats, int cols, int rows) {
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
}
