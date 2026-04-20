package com.solvd.airport.persons;

import com.solvd.airport.interfaces.ICheckIn;
import com.solvd.airport.systens.Flight;

import java.util.Objects;

public abstract class Crew extends Person  {
    private String function;

    public Crew(String name, int age, String id, String function) {
        super(name, age, id);
        this.function = function;
    }

    public String getFunction() {
        return function;
    }

    @Override
    public String toString(){
        return super.toString() + "\n" +
                "Function: " + this.function;
    }

    @Override
    public boolean equals(Object o) {
        Crew crew = (Crew) o;
        if(!crew.getFunction().equals(this.function)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),function);
    }

}
