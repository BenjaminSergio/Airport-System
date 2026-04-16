package com.solvd.airport.systens;

import com.solvd.airport.persons.Passenger;

public class Seat {
    private String seatId;
    private boolean vacancy;
    private Passenger passenger;
    private SeatClass seatClass;

    public enum SeatClass{
        ECONOMIC,
        BUSINESS,
        FIRST_CLASS
    }

    public Seat(String seatId, SeatClass seatClass) {
        this.seatId = seatId;
        this.vacancy = true;
        this.seatClass = seatClass;
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
