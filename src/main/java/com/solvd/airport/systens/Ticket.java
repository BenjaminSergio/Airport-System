package com.solvd.airport.systens;

import java.util.Objects;

public class Ticket {
    private String passengerName;
    private String companyName;
    private String flightId;
    private String seatId;
    private String flightTime;
    private String flightDate;
    private String departureCity;
    private String arrivalCity;
    private double price;

    public Ticket(String passengerName, String companyName, String flightId,
                  String seatId, String flightTime, String flightDate,
                  String departureCity, String arrivalCity, double price) {

        this.passengerName = passengerName;
        this.companyName = companyName;
        this.flightId = flightId;
        this.seatId = seatId;
        this.flightTime = flightTime;
        this.flightDate = flightDate;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.price = price;

    }

public String getPassengerName()     {
        return passengerName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getFlightId() {
        return flightId;
    }

    public String getSeatId() {
        return seatId;
    }

    public String getFlightTime() {
        return flightTime;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "passengerName='" + passengerName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", flightId='" + flightId + '\'' +
                ", seatId='" + seatId + '\'' +
                ", flightTime='" + flightTime + '\'' +
                ", flightDate='" + flightDate + '\'' +
                ", departureCity='" + departureCity + '\'' +
                ", arrivalCity='" + arrivalCity + '\'' +
                ", price=" + price +
                '}';
    }
    @Override
    public int hashCode(){
        return Objects.hash(flightId,seatId);
    }
}
