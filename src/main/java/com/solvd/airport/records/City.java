package com.solvd.airport.records;

import com.solvd.airport.annotation.StringValidation;

public record City(
        @StringValidation String cityName,
        String longitude,
        String latitude){
    public City{
        if(cityName.isEmpty() || cityName == null) throw new IllegalArgumentException("City Name Cannot be Empty");
    }
}