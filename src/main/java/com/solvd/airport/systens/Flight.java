package main.java.com.solvd.airport.systens;

import main.java.com.solvd.airport.companies.Company;
import main.java.com.solvd.airport.data.AirportFileHandler;
import main.java.com.solvd.airport.exceptions.CrewAssignmentException;
import main.java.com.solvd.airport.exceptions.SeatAllocationException;
import main.java.com.solvd.airport.persons.Attendant;
import main.java.com.solvd.airport.persons.Crew;
import main.java.com.solvd.airport.persons.Passenger;
import main.java.com.solvd.airport.persons.Pilot;
import main.java.com.solvd.airport.places.Airport;
import main.java.com.solvd.airport.places.City;
import main.java.com.solvd.airport.utils.AirportUtils;
import main.java.com.solvd.airport.vehicles.Aircraft;

import java.util.Arrays;
import java.util.Objects;

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
        String seatId;
        int seatsAdd = 0;
        int maxSeats = seats.length;
        for(int i =0; i < columns; i++){
            for(int j = 0; j < rows; j++){
                if(seatsAdd<maxSeats){
                    seatId = "0" + i + "-" + j;
                    if(i<2) seats[seatsAdd] = new Seat(seatId, Seat.SeatClass.FIRST_CLASS);
                    else if (i<4) seats[seatsAdd] = new Seat(seatId, Seat.SeatClass.BUSINESS);
                    else seats[seatsAdd] = new Seat(seatId, Seat.SeatClass.ECONOMIC);
                    seatsAdd++;
                }else{
                    break;
                }                
            }
            if(seatsAdd >= maxSeats){
                break;
            }
        }
    }


    public Ticket createTicket(Passenger passenger, Seat seat){
        Ticket ticket = new Ticket(passenger.getName(), this.getCompany().getCompanyName(),Integer.toString(this.flightId),seat.getSeatId(),this.flightTime,this.flightDate,this.departureCity.getCityName(),this.arrivalCity.getCityName(),this.price);
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

        for(int index = 0; index < seats.length;index++){
            if(seats[index] == null){
                continue;
            }
            resultSeat = seats[index].assignSeat(passenger);
            if(resultSeat.equals("Success - Seat reserved!")) {
                this.openSeats--;
                resultTicket = createTicket(passenger,seats[index]);
                AirportFileHandler.addTicketToFile(AirportUtils.DEFAULT_TICKET_DATA_PATH,resultTicket);
                return resultSeat + " Passenger " + passenger.getName() + " allocated in seat: " + seats[index].getSeatId();
            }
        }
        msg = resultSeat;
        return  msg;
    }

    public String assignPilot(Pilot pilot) throws CrewAssignmentException {
        String msg = "Error - Cannot Assign Pilot";

        for(Crew crew:pilots){
            if(crew == null){
                crew = (Crew)pilot;
                msg = "Success - Pilot Assigned";
                break;
            }
        }
        if(msg.equals("Error - Cannot Assign Pilot")) throw new CrewAssignmentException("Error - Cannot Assign Pilot");
        return msg;
    }
    public String assignAttendant(Attendant attendant) throws CrewAssignmentException {
        String msg = "Error - Cannot Assign Attendant";

        for(Crew crew:attendants){
            if(crew == null){
                crew = (Crew)attendant;
                msg = "Success - Attendant Assigned";
                break;
            }
        }
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
