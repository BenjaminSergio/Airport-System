package main.java.com.solvd.airport.systens;

import main.java.com.solvd.airport.persons.Passenger;

public class Seat {
    private String seatId;
    private boolean vacancy;
    private Passenger passenger;

    public Seat(String seatId) {
        this.seatId = seatId;
        this.vacancy = true;
    }

    public String getSeatId() {
        return seatId;
    }

    public boolean isVacancy() {
        return vacancy;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public String assignSeat(Passenger passenger){

        String msg = "Error - Seat already occupied!";

        if(this.vacancy){
            this.passenger = passenger;
            vacancy = false;
            msg = "Success - Seat reserved!";
        }

        return msg;
    }

}
