package com.solvd.airport.vehicles;

public abstract class Aircraft extends Vehicle{

    private int numberSeats;
    private int columns;
    private int rows;

    public Aircraft(String aircraftId, String aircraftModel, int numSeats, int cols, int rows){
        super(aircraftId,aircraftModel);
        this.numberSeats = numSeats;
        this.columns = cols;
        this.rows = rows;
    }

    public String getAircraftId() {
        return super.getVehicleId();
    }

    public String getAircraftModel() {
        return super.getVehicleModel();
    }

    public int getNumberSeats() {
        return numberSeats;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

}
