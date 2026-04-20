package com.solvd.airport.systens;

import com.solvd.airport.companies.Company;
import com.solvd.airport.data.AirportFileHandler;
import com.solvd.airport.exceptions.CrewAssignmentException;
import com.solvd.airport.exceptions.SeatAllocationException;
import com.solvd.airport.persons.Attendant;
import com.solvd.airport.persons.Crew;
import com.solvd.airport.persons.Passenger;
import com.solvd.airport.persons.Pilot;
import com.solvd.airport.places.Airport;
import com.solvd.airport.records.City;
import com.solvd.airport.records.Ticket;
import com.solvd.airport.utils.AirportUtils;
import com.solvd.airport.vehicles.Aircraft;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

public class Flight {
    private Aircraft aircraft;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private Company company;
    private City departureCity;
    private City arrivalCity;
    private String flightDate;
    private String flightTime;
    private String flightDistance;
    private double price;
    private Crew[] pilots;
    private Crew[] attendants;
    private Seat[] seats;
    private int openSeats;
    private int flightId;

    public FlightStatus getStatus() {
        return status;
    }

    private FlightStatus status;

    public enum FlightStatus{
        SCHEDULED,
        BOARDING,
        DEPARTED,
        DELAYED,
        CANCELLED,
        LANDED
    }

    public Flight(Aircraft aircraft, Airport departureAirport, Airport arrivalAirport, Company company,
                  City departureCity, City arrivalCity,
                  String flightDate, String flightTime, String flightDistance,
                  int maxPilots, int maxAttendants, double price){
        this.aircraft = aircraft;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.company = company;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.flightDate = flightDate;
        this.flightTime = flightTime;
        this.flightDistance = flightDistance;
        this.seats = new Seat[this.aircraft.getNumberSeats()];
        this.openSeats = this.aircraft.getNumberSeats();
        this.pilots = new Crew[maxPilots];
        this.attendants = new Crew[maxAttendants];
        this.price = price;
        this.status = FlightStatus.SCHEDULED;
        this.flightId = Objects.hash(this.aircraft,this.departureAirport,this.arrivalAirport,this.flightDate, this.aircraft, this.company);
        createSeats(this.seats,
                    this.aircraft.getColumns(),
                    this.aircraft.getRows());
    }
    public Flight(){

    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public Company getCompany() {
        return company;
    }

    public City getDepartureCity() {
        return departureCity;
    }

    public City getArrivalCity() {
        return arrivalCity;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public String getFlightTime() {
        return flightTime;
    }

    public String getFlightDistance() {
        return flightDistance;
    }

    public double getPrice() {
        return price;
    }

    public int getOpenSeats(){
        return openSeats;
    }

    public int getFlightId() {
        return flightId;
    }

    private void createSeats(Seat[] seats, int columns, int rows){
        /* This function creates the seats while assign different ids for them,
        * a flight cannot have 2 seats with the same id*/
        int maxSeats = seats.length;
        IntStream.range(0, columns).boxed()
                // 2. flatMap to rows (list2) to create [i, j] pairs
                .flatMap(i -> IntStream.range(0, rows).mapToObj(j -> new int[]{i, j}))
                // Limit to maxSeats to replace the `break;` condition
                .limit(maxSeats)
                .forEach(pair -> {
                    int i = pair[0];
                    int j = pair[1];
                    String seatId = "0" + i + "-" + j;

                    // Calculate the 1D array index from the 2D coordinates
                    int index = (i * rows) + j;

                    if (i < 2) seats[index] = new Seat(seatId, Seat.SeatClass.FIRST_CLASS);
                    else if (i < 4) seats[index] = new Seat(seatId, Seat.SeatClass.BUSINESS);
                    else seats[index] = new Seat(seatId, Seat.SeatClass.ECONOMIC);
                });
    }


    public Ticket createTicket(Passenger passenger, Seat seat){
        Ticket ticket = new Ticket(passenger.getId(), passenger.getName(), this.getCompany().getCompanyName(),Integer.toString(this.flightId),
                seat.getSeatId(),this.flightTime,this.flightDate,this.departureCity.cityName(),this.arrivalCity.cityName(),
                this.price);
        Ticket[] tickets = passenger.getTickets();
        for(int index = 0; index < tickets.length;index++){
            if(tickets[index] != null) continue;
            tickets[index] = ticket;
            break;
        }
        return ticket;
    }

    public String assignSeat(Passenger passenger) throws SeatAllocationException{
        String msg;
        String resultSeat = "";
        Ticket resultTicket;
        if(this.openSeats <=0){
            msg = "Flight already Full";
            throw new SeatAllocationException(msg);
        }
        msg = java.util.Arrays.stream(seats)
                .filter(java.util.Objects::nonNull)
                .map(seat -> {
                    String res = seat.assignSeat(passenger);
                    if (res.equals("Success - Seat reserved!")) {
                        this.openSeats--;
                        Ticket ticket = createTicket(passenger, seat);
                        AirportFileHandler.addTicketToFile(AirportUtils.DEFAULT_TICKET_DATA_PATH, ticket);
                        return res + " Passenger " + passenger.getName() + " allocated in seat: " + seat.getSeatId();
                    }
                    return null;
                })
                .filter(java.util.Objects::nonNull)
                .findFirst()
                .orElse("Error - Seat already occupied!");

        return  msg;
    }

    public String assignPilot(Pilot pilot) throws CrewAssignmentException {
        String msg = IntStream.range(0, pilots.length)
                .boxed()
                .filter(i -> pilots[i] == null)
                .findFirst()
                .map(i -> {
                    pilots[i] = pilot;
                    return "Success - Pilot Assigned";
                })
                .orElse("Error - Cannot Assign Pilot");

        if(msg.equals("Error - Cannot Assign Pilot")) throw new CrewAssignmentException("Error - Cannot Assign Pilot");
        return msg;
    }
    public String assignAttendant(Attendant attendant) throws CrewAssignmentException {
        String msg = IntStream.range(0, attendants.length)
                .boxed()
                .filter(i -> attendants[i] == null)
                .findFirst()
                .map(i -> {
                    attendants[i] = attendant;
                    return "Success - Attendant Assigned";
                })
                .orElse("Error - Cannot Assign Attendant");
        if(msg.equals("Error - Cannot Assign Attendant")) throw new CrewAssignmentException("Error - Cannot Assign Attendant");
        return msg;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "aircraft=" + aircraft +
                ", departureAirport=" + departureAirport +
                ", arrivalAirport=" + arrivalAirport +
                ", company=" + company +
                ", departureCity=" + departureCity +
                ", arrivalCity=" + arrivalCity +
                ", flightDate='" + flightDate + '\'' +
                ", flightTime='" + flightTime + '\'' +
                ", flightDistance='" + flightDistance + '\'' +
                ", price=" + price +
                ", pilots=" + Arrays.toString(pilots) +
                ", attendants=" + Arrays.toString(attendants) +
                ", seats=" + Arrays.toString(seats) +
                ", openSeats=" + openSeats +
                ", flightId=" + flightId +
                ", status =" + status +
                '}';
    }
}
