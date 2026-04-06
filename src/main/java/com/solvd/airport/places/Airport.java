package main.java.com.solvd.airport.places;

import main.java.com.solvd.airport.utils.AirportUtils;
import main.java.com.solvd.airport.systens.Flight;
import main.java.com.solvd.airport.interfaces.IManageFly;
import main.java.com.solvd.airport.interfaces.IMeasure;
import main.java.com.solvd.airport.vehicles.Vehicle;

import java.util.LinkedList;

public class Airport <T extends Vehicle> implements IMeasure, IManageFly {
    private City city;
    private Flight[] flights;
    private LinkedList<T> garage;

    public Airport(City city, int maxFlights){
        this.city = city;
        this.flights = new Flight[maxFlights];
        this.garage = new LinkedList<>();
    }

    public String  insertFlight(Flight flight){
        String msg = "Error - cannot inserted flight";
        int index = 0;
        while(flights[index] != null && index < flights.length){
            index++;
        }
        if(flights[index] == null){
            flights[index] = flight;
            msg = "flight inserted";
        }
        return msg;
    }
    public Vehicle getSpecificVehicle(String vehicleId){
        Vehicle vehicle = null;
        for(int i = 0; i < garage.size();i++){
            if(garage.get(i) == null) continue;
            if(garage.get(i).getVehicleId().equals(vehicleId)) vehicle = garage.get(i);
        }
        return vehicle;
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
        if(AirportUtils.random.nextBoolean()) msg = "Temperature is OK!";
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
