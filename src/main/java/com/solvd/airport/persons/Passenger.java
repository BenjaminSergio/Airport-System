package main.java.com.solvd.airport.persons;

import main.java.com.solvd.airport.interfaces.ICheckIn;
import main.java.com.solvd.airport.systens.Flight;
import main.java.com.solvd.airport.systens.Ticket;

import java.util.Objects;

import static main.java.com.solvd.airport.Main.LOGGER;

public class Passenger extends Person implements ICheckIn {
    private Ticket[] tickets;
    private int baggage;
    private int baggageWeight;

    public Passenger(String name, int age, String id) {
        super(name, age, id);
        this.tickets = new Ticket[10];
    }

    public int getBaggage() {
        return baggage;
    }

    public int getBaggageWeight() {
        return baggageWeight;
    }

    public Ticket[] getTickets() {
        return tickets;
    }

    @Override
    public String toString(){
        return super.toString()+
                " Number of Baggages: "+ this.baggage +
                " | Total Weight: "+ this.baggageWeight ;
    }

    @Override
    public boolean equals(Object o) {
        Passenger passenger = (Passenger) o;
        if(passenger.getBaggage() != this.baggage) return false;
        if(passenger.getBaggageWeight() != this.baggageWeight) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),this.baggage,this.baggageWeight);
    }

    public void printTickets(){

        LOGGER.debug("Passenger Name: "+ this.getName());
        for(Ticket ticket: tickets){
            if(ticket == null) continue;
            LOGGER.debug(ticket.toString());
        }
    }

    @Override
    public String checkin(Flight flight) {
        String msg = "Error in the Checkin";
        if(flight == null) msg = msg+" - Flight null";
        msg = "Passenger checkin in the +" + flight.toString();
        return msg;
    }
}
