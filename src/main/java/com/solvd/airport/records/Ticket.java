package com.solvd.airport.records;

import com.solvd.airport.annotation.Sensitive;
import com.solvd.airport.annotation.StringValidation;
import com.solvd.airport.validator.StringDump;

import static com.solvd.airport.Main.LOGGER;
import static com.solvd.airport.validator.StringValidator.stringValidateAll;

public record  Ticket(
        @Sensitive @StringValidation String passengerId,
        String passengerName,
        String companyName,
        String flightId,
        String seatId,
        String flightTime,
        String flightDate,
        String departureCity,
        String arrivalCity,
        double price
){
        public Ticket(String passengerId,String passengerName, String companyName, String flightId, String seatId, String flightTime,
                      String flightDate, String departureCity, String arrivalCity, double price) {
                this.passengerId = passengerId;
                this.passengerName = passengerName;
                this.companyName = companyName;
                this.flightId = flightId;
                this.seatId = seatId;
                this.flightTime = flightTime;
                this.flightDate = flightDate;
                this.departureCity = departureCity;
                this.arrivalCity = arrivalCity;
                this.price = price;
                try{
                        stringValidateAll(this);
                } catch (RuntimeException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                }
        }
        @Override
        public String toString(){
                return StringDump.dump(this);
        }
}
