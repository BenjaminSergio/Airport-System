package main.java.com.solvd.airport.persons;

public  abstract class AirportCrew extends Crew{
    public AirportCrew(String name, int age, String id, String function) {
        super(name, age, id, function);
    }
    public void doWork(){}
}
