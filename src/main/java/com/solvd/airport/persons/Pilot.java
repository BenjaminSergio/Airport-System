package main.java.com.solvd.airport.persons;

import main.java.com.solvd.airport.interfaces.ICheckIn;
import main.java.com.solvd.airport.interfaces.IDrive;
import main.java.com.solvd.airport.interfaces.IManageFly;
import main.java.com.solvd.airport.systens.Flight;
import main.java.com.solvd.airport.vehicles.Vehicle;

public class Pilot extends Crew implements ICheckIn, IDrive, IManageFly {
    public Pilot(String name, int age, String id, String function) {
        super(name, age, id, function);
    }
    @Override
    public String checkin(Flight flight) {
        String msg = "Error in the Checkin";
        if(flight == null) msg = msg+" - Flight null";
        msg = "Pilot checkin in the +" + flight.toString();
        return msg;
    }

    @Override
    public String drive(Vehicle vehicle) {
        String msg = "Error in the Vehicle";
        if(vehicle == null) msg = msg+" - Vehicle null";
        msg = "Pilot drove the +" + vehicle.toString();
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
