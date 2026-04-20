package com.solvd.airport.places;

import com.solvd.airport.records.City;
import com.solvd.airport.utils.AirportUtils;
import com.solvd.airport.systens.Flight;
import com.solvd.airport.interfaces.IManageFly;
import com.solvd.airport.interfaces.IMeasure;
import com.solvd.airport.vehicles.Vehicle;

import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.IntStream;

public class Airport <T extends Vehicle> implements IMeasure, IManageFly {
    private int airpotId;
    private City city;
    private Flight[] flights;
    private LinkedList<T> garage;

    public AirportClimate getClimate() {
        return climate;
    }

    public LinkedList<T> getGarage() {
        return garage;
    }

    public int getAirpotId() {
        return airpotId;
    }

    private AirportClimate climate;
    public enum AirportClimate{
        SUN,
        RAIN,
        TORNADO,
        SNOW,
        CLOUD
    }

    public Airport(City city, int maxFlights, int airportId){
        this.airpotId = airportId;
        this.city = city;
        this.flights = new Flight[maxFlights];
        this.garage = new LinkedList<>();
        this.climate = AirportClimate.SUN;
    }

    public String  insertFlight(Flight flight){
        return IntStream.range(0, flights.length)
                .boxed()
                .filter(i -> flights[i] == null)
                .findFirst()
                .map(i -> {
                    flights[i] = flight;
                    return "flight inserted";
                })
                .orElse("Error - cannot inserted flight");
    }
    public Vehicle getSpecificVehicle(String vehicleId){
        return garage.stream()
                .filter(Objects::nonNull)
                .filter(v -> v.getVehicleId().equals(vehicleId))
                .findFirst()
                .orElse(null);
    }

    public City getCity() {
        return city;
    }

    public Flight[] getFlights() {
        return flights;
    }

    @Override
    public String measureTemperature() {
        String msg = "Temperature is bad!";
        this.climate = AirportClimate.RAIN;
        if(AirportUtils.random.nextBoolean()) {
            msg = "Temperature is OK!";
            this.climate = AirportClimate.SUN;
        }
        return msg;
    }

    @Override
    public String startFly(Flight flight) {
        String msg = "Error in the flight";
        if(flight == null) msg = msg + " - Flight is null";
        else msg = "flight: " + flight.getFlightId() + " has taken off!";
        return msg;
    }

    @Override
    public String CancelFly(Flight flight) {
        String msg = "Error in the flight";
        if(flight == null) msg = msg + " - Flight is null";
        else msg = "flight: " + flight.getFlightId() + " cancelled!";
        return msg;
    }

    @Override
    public String delayFly(Flight flight) {
        String msg = "Error in the flight";
        if(flight == null) msg = msg + " - Flight is null";
        else msg = "flight: " + flight.getFlightId() + " has been delayed!";
        return msg;
    }
}
