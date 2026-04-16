package com.solvd.airport.persons;

import com.solvd.airport.interfaces.ICheckIn;
import com.solvd.airport.systens.Flight;

public class Attendant extends Crew implements ICheckIn {
    public Attendant(String name, int age, String id, String function) {
        super(name, age, id , function);
    }
    @Override
    public String checkin(Flight flight) {
        String msg = "Error in the Checkin";
        if(flight == null) msg = msg+" - Flight null";
        msg = "Attendant checkin in the +" + flight.toString();
        return msg;
    }
}
