package com.solvd.airport.vehicles;

public abstract class Vehicle {
    private String vehicleId;
    private String vehicleModel;

    public Vehicle(String vehicleId, String vehicleModel) {
        this.vehicleId = vehicleId;
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId='" + vehicleId + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                '}';
    }
}
