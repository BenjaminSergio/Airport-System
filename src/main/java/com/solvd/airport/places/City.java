package main.java.com.solvd.airport.places;

public class City {
    private String cityName;
    private String longitude;
    private String latitude;

    public City(String name, String latitude, String longitude){
        this.cityName = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public City(String name){
        this.cityName = name;
    }

    public String getCityName() {
        return cityName;
    }
    public String getLocation(){
        return longitude+", "+latitude;
    }
}
