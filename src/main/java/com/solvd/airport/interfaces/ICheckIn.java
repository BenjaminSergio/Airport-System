package com.solvd.airport.interfaces;

import com.solvd.airport.systens.Flight;

public interface ICheckIn {
    //Every Class that can enter in a fly must implement this interface
    //Intended to be used t implement every process of the checkin
    String checkin(Flight flight);

}
