package main.java.com.solvd.airport.systens;

import main.java.com.solvd.airport.companies.Company;
import main.java.com.solvd.airport.data.AirportFileHandler;
import main.java.com.solvd.airport.exceptions.CrewAssignmentException;
import main.java.com.solvd.airport.exceptions.SeatAllocationException;
import main.java.com.solvd.airport.persons.Attendant;
import main.java.com.solvd.airport.persons.Passenger;
import main.java.com.solvd.airport.persons.Pilot;
import main.java.com.solvd.airport.places.Airport;
import main.java.com.solvd.airport.places.City;
import main.java.com.solvd.airport.utils.AirportUtils;
import main.java.com.solvd.airport.vehicles.Aircraft;
import main.java.com.solvd.airport.vehicles.Airplane;

import java.util.LinkedList;

import static main.java.com.solvd.airport.Main.LOGGER;

public abstract class AirportSystem {
    //This function serves the porpoise of encapsulate all the airport system outside main class
    //The Idea is making the workflow readable as much as possible

    protected static LinkedList<City> cities = new LinkedList<>();
    protected static LinkedList<Airport> airports = new LinkedList<>();
    protected static LinkedList<Company> companies = new LinkedList<>();
    public static void functionalityTest(){
        //Population a moc database to use to test the system
        AirportSystem.populateCities();
        AirportSystem.populateAirports();
        AirportSystem.populateCompanies();
        AirportSystem.populateAircrafts();
        AirportSystem.populateFlights();

        //Creating people to test the system
        Passenger passenger01 = new Passenger("john Doe",20,"123456789");
        Passenger passenger02 = new Passenger("Mary Doe",19,"987654321");
        Pilot pilot1 = new Pilot("Andrey Smirnov", 45, "23134143-3123", "First Pilot");
        Attendant attendant01 = new Attendant("Shirley Regina",23,"12394554367","Flight Attendant");


        LOGGER.debug("---------------- compilation success ----------------");
        //Testing search methods
        Flight cheapest = AirportSystem.cheapestFlight(airports.get(0),airports.get(1));
        Flight shortest = AirportSystem.shortestFlight(airports.get(0),airports.get(1));

        LOGGER.debug("Cheapest Flight: {} | price: {} | available seats: {}", cheapest.getCompany().getCompanyName(), cheapest.getPrice(), cheapest.getOpenSeats());
        LOGGER.debug("Shortest Flight: {} | time: {} | available seats: {}", shortest.getCompany().getCompanyName(), shortest.getFlightTime(), shortest.getOpenSeats());

        //Testing Polymorphs and override
        LOGGER.debug("------------------------------------");
        LOGGER.debug(passenger02.toString());
        passenger02.printTickets();
        LOGGER.debug(pilot1.toString());
        try {
            LOGGER.debug("Pilot Allocation: {}", cheapest.assignPilot(pilot1));
        } catch (CrewAssignmentException e) {
            throw new RuntimeException(e);
        }
        try {
            LOGGER.debug("Attendant Allocation: {}", cheapest.assignAttendant(attendant01));
        } catch (CrewAssignmentException e) {
            throw new RuntimeException(e);
        }
        try{
            LOGGER.debug(seatAllocation(passenger02,cheapest));
        }catch(SeatAllocationException e){
            LOGGER.warn(e);
        }
        AirportFileHandler.countTicketsInFile(AirportUtils.DEFAULT_TICKET_DATA_PATH);
    }

    //Functions to populate a database with mock data to test the airport system
    public static void populateCities(){
        cities.add(new City("Rio de Janeiro"));
        cities.add(new City("New York"));
        cities.add(new City("Mexico ity"));
        cities.add(new City("Los Angeles"));
        LOGGER.debug("Cities Populated");
    }
    public static void populateAirports(){
        airports.add(new Airport(cities.get(0),10,1));
        airports.add(new Airport(cities.get(1),10,2));
        airports.add(new Airport(cities.get(2),10,3));
        airports.add(new Airport(cities.get(3),10,4));
        LOGGER.debug("Airports populated");
    }
    public static void populateCompanies(){
        companies.add(new Company("Gol","GOL"));
        companies.add(new Company("Azul","AZL"));
        companies.add(new Company("Latam","LTM"));
        LOGGER.debug("Companies Populated");
    }
    public static void populateAircrafts(){
        Airplane airplane01 = new Airplane("737", "Boeing", 100, 6, 17);
        Airplane airplane02 = new Airplane("747", "Boeing", 100, 6, 17);
        companies.get(0).insertAircraft((Aircraft) airplane01);
        companies.get(1).insertAircraft((Aircraft) airplane02);
        LOGGER.debug("Aircrafts populated");
    }
    public static void populateFlights() {
        Flight flight01 = new Flight(companies.get(0).getAircraft(0), airports.get(0), airports.get(1),
                                     companies.get(0), cities.get(0), cities.get(1), "2025-12-01", "600",
                         "7000", 2, 4,499.99);
        Flight flight02 = new Flight(companies.get(1).getAircraft(0), airports.get(0), airports.get(1),
                                     companies.get(1), cities.get(0), cities.get(1), "2025-12-01", "550",
                         "7000", 2, 4,659.90);
        airports.get(0).insertFlight(flight01);
        airports.get(0).insertFlight(flight02);
        LOGGER.debug("Flights populated");
    }

    //Functions to help the passenger find the best way to travel (cheapest and sortest)
    public static Flight cheapestFlight(Airport departureAirport, Airport arrivalAirport){
        Flight cheapestFlight = null;
        double cheapestPrice = 99999999;
        double flightPrice = 0;
        for(Flight flight:departureAirport.getFlights()){
            if(flight !=null){
                if(flight.getArrivalAirport().equals(arrivalAirport)){
                    flightPrice = flight.getPrice();
                    if(flightPrice < cheapestPrice){
                        cheapestPrice = flightPrice;
                        cheapestFlight = flight;
                    }
                }
            }
        }
        return cheapestFlight;
    }
    public static Flight shortestFlight(Airport departureAirport, Airport arrivalAirport){
        Flight shortestFlight = null;
        int shortestTime = 99999999;
        int flightTime = 0;
        for(Flight flight:departureAirport.getFlights()){
            if(flight !=null){
                if(flight.getArrivalAirport().equals(arrivalAirport)){
                    flightTime = Integer.parseInt(flight.getFlightTime());
                    if(flightTime < shortestTime){
                        shortestTime = flightTime;
                        shortestFlight = flight;
                    }
                }
            }
        }
        return shortestFlight;
    }
    public static String seatAllocation(Passenger passenger, Flight flight) throws SeatAllocationException {
        return flight.assignSeat(passenger);
    }
}
