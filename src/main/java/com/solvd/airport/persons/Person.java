package com.solvd.airport.persons;

import com.solvd.airport.vehicles.Vehicle;

import java.util.LinkedList;
import java.util.Objects;

public abstract class Person <T extends Vehicle>{

    private String name;
    private int age;
    private String id;
    private LinkedList<T> vehicles;
    public static int personsCreated = 0;

    public Person(String name, int age, String id){
        this.age = age;
        this.name = name;
        this.id = id;
        this.vehicles = new LinkedList<>();
        personsCreated++;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getId(){ return id; }

    public LinkedList<T> getVehicles() {
        return vehicles;
    }

    public Vehicle getSpecificVehicle(String vehicleId){
        return vehicles.stream()
                .filter(Objects::nonNull)
                .filter(v -> v.getVehicleId().equals(vehicleId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean equals(Object o) {

        if(o == null) return false;
        if(this.getClass() != o.getClass()) return false;

        Person person = (Person) o;
        if(!person.getId().equals(this.id)) return false;
        if(person.getName().equals(this.name)) return false;
        if(person.getAge() != this.age) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, id);
    }

    @Override
    public String toString(){
        return "Person: " + this.name +
               " Id: " + this.id +
               " Age: " + this.age;
    }
}
