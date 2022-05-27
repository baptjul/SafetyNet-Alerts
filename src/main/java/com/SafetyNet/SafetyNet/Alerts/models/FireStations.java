package com.SafetyNet.SafetyNet.Alerts.models;

public class FireStations {

    private String stationAddress;
    private Integer stationNumber;

    public FireStations(String stationAddress, Integer stationNumber) {
        this.stationAddress = stationAddress;
        this.stationNumber = stationNumber;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress;
    }

    public Integer getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(Integer stationNumber) {
        this.stationNumber = stationNumber;
    }
}