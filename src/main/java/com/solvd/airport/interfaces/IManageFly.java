package main.java.com.solvd.airport.interfaces;

import main.java.com.solvd.airport.systens.Flight;

public interface IManageFly {
    String startFly(Flight flight);
    String CancelFly(Flight flight);
    String delayFly(Flight flight);
}
