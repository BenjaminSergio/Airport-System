package com.solvd.airport.systens;

import com.solvd.airport.companies.Company;
import com.solvd.airport.data.AirportFileHandler;
import com.solvd.airport.exceptions.CrewAssignmentException;
import com.solvd.airport.exceptions.SeatAllocationException;
import com.solvd.airport.interfaces.IFilterAirport;
import com.solvd.airport.interfaces.IFilterFlight;
import com.solvd.airport.interfaces.IGenerateMensage;
import com.solvd.airport.persons.Attendant;
import com.solvd.airport.persons.Passenger;
import com.solvd.airport.persons.Pilot;
import com.solvd.airport.places.Airport;
import com.solvd.airport.records.City;
import com.solvd.airport.records.VehicleModel;
import com.solvd.airport.utils.AirportUtils;
import com.solvd.airport.vehicles.Aircraft;
import com.solvd.airport.vehicles.Airplane;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.solvd.airport.Main.LOGGER;

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

        // 1. Create the lambda to join all parts with a space
        IGenerateMensage msgGen = (String... parts) -> String.join(" ", parts);

        String cheapestMsg = msgGen.generate(
                "Cheapest Flight:", cheapest.getCompany().getCompanyName(),
                "| price:", String.valueOf(cheapest.getPrice()),
                "| available seats:", String.valueOf(cheapest.getOpenSeats())
        );
        LOGGER.debug(cheapestMsg);

        String shortestMsg = msgGen.generate(
                "Shortest Flight:", shortest.getCompany().getCompanyName(),
                "| time:", shortest.getFlightTime(), // getFlightTime() already returns a String
                "| available seats:", String.valueOf(shortest.getOpenSeats())
        );
        LOGGER.debug(shortestMsg);

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
            passenger02.printTickets();
            LOGGER.debug(pilot1.toString());
        }catch(SeatAllocationException e){
            LOGGER.warn(e);
        }
        AirportFileHandler.countTicketsInFile(AirportUtils.DEFAULT_TICKET_DATA_PATH);

        checkAirportsClimate(cities.getFirst(), Airport.AirportClimate.SUN);
        checkFligthStatus(airports.getFirst(), Flight.FlightStatus.SCHEDULED);
    }

    //Functions to populate a database with mock data to test the airport system
    public static void populateCities(){
        cities.add(new City("Rio de Janeiro","-22.906847","-43.172897"));
        cities.add(new City("New York","40.730610","-73.935242"));
        cities.add(new City("Mexico ity","19.432608","-99.133209"));
        cities.add(new City("Los Angeles","34.052235","-118.243683"));
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
        VehicleModel model1 = new VehicleModel("Boeing - 747","Boeing");
        VehicleModel model2 = new VehicleModel("Boeing 787 Dreamliner","Boeing");
        Airplane airplane01 = new Airplane("737", model1, 100, 6, 17);
        Airplane airplane02 = new Airplane("747", model2, 100, 6, 17);
        companies.get(0).insertAircraft((Aircraft) airplane01);
        companies.get(1).insertAircraft((Aircraft) airplane02);
        LOGGER.debug("Aircraft populated");
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
        IFilterFlight destinationFilter = flight -> flight.getArrivalAirport().equals(arrivalAirport);
        Flight cheapestFlight = Arrays.stream(departureAirport.getFlights())
                .filter(Objects::nonNull)
                .filter(destinationFilter::test)
                .min(Comparator.comparingDouble(Flight::getPrice))
                .orElse(null);
        return cheapestFlight;
    }
    public static Flight shortestFlight(Airport departureAirport, Airport arrivalAirport){
        IFilterFlight destinationFilter = flight -> flight.getArrivalAirport().equals(arrivalAirport);
        Flight shortestFlight = Arrays.stream(departureAirport.getFlights())
                .filter(Objects::nonNull)
                .filter(destinationFilter::test)
                .min(Comparator.comparingInt(f -> Integer.parseInt(f.getFlightDistance())))
                .orElse(null);
        return shortestFlight;
    }
    public static String checkFligthStatus(Airport airport, Flight.FlightStatus status){
        IFilterFlight statusFilter = flight -> flight.getStatus() == status;

        String msg = Arrays.stream(airport.getFlights())
                .filter(Objects::nonNull)
                .filter(statusFilter::test)
                .map(flight -> String.valueOf(flight.getFlightId()))
                .collect(Collectors.joining(", "));

        return msg;
    }

    public static String checkAirportsClimate(City city, Airport.AirportClimate climate){
        IFilterAirport climateFilter = airport -> airport.getClimate() == climate;
        IFilterAirport cityFilter = airpoty -> airpoty.getCity() == city;

        String matchingAirports = airports.stream()
                .filter(Objects::nonNull)
                .filter(climateFilter::test)
                .filter(cityFilter::test)
                .map(airport -> String.valueOf(airport.getAirpotId()))
                .collect(Collectors.joining(", "));
        return matchingAirports;
    }

    public static String seatAllocation(Passenger passenger, Flight flight) throws SeatAllocationException {
        return flight.assignSeat(passenger);
    }
}
