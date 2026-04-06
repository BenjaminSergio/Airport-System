package main.java.com.solvd.airport.companies;

import main.java.com.solvd.airport.interfaces.IManageFly;
import main.java.com.solvd.airport.systens.Flight;
import main.java.com.solvd.airport.vehicles.Aircraft;
import main.java.com.solvd.airport.vehicles.Vehicle;

import java.util.LinkedList;

public class Company<T extends Aircraft> implements IManageFly {
    private String companyName;
    private String companyId;
    private LinkedList<T> aircrafts;
    private LinkedList<Flight> flights;

    public Company(String companyName, String companyId){
        this.companyId = companyId;
        this.companyName = companyName;
        this.aircrafts = new LinkedList<>();
        this.flights = new LinkedList<>();
    }

    public String  insertFlight(Flight flight){
        String msg = "Error - cannot insert flight";
        int index = 0;

        if(flight != null){
            flights.add(flight);
            msg = "flight inserted";
        }
        return msg;
    }

    public String  insertAircraft(T aircraft){
        String msg = "Error - cannot insert Aircraft";
        if(aircraft != null){
            aircrafts.add(aircraft);
            msg = "Aircraft inserted";
        }
        return msg;
    }
    public Vehicle getSpecificVehicle(String vehicleId){
        Vehicle vehicle = null;
        for(int i = 0; i < aircrafts.size();i++){
            if(aircrafts.get(i) == null) continue;
            if(aircrafts.get(i).getVehicleId().equals(vehicleId)) vehicle = aircrafts.get(i);
        }
        return vehicle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public Aircraft getAircraft(int index){
        if(aircrafts.get(index) != null){
            return aircrafts.get(index);
        }
        return null;
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
        if(flight == null) msg = msg + " - light is null";
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
