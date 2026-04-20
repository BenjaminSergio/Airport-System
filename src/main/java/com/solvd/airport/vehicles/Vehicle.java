package com.solvd.airport.vehicles;

import com.solvd.airport.interfaces.IDrive;
import com.solvd.airport.interfaces.IPark;
import com.solvd.airport.records.VehicleModel;

public abstract class Vehicle implements IPark, IDrive {
    private String vehicleId;
    private VehicleModel vehicleModel;
    private boolean isParked;

    public Vehicle(String vehicleId, VehicleModel vehicleModel) {
        this.vehicleId = vehicleId;
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public VehicleModel getVehicleModel() {
        return this.vehicleModel;
    }
    public  boolean isParked(){return this.isParked;}

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId='" + vehicleId + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                '}';
    }
}
