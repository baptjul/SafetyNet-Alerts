package com.SafetyNet.SafetyNet.Alerts.controllers;

import com.SafetyNet.SafetyNet.Alerts.models.FireStations;
import com.SafetyNet.SafetyNet.Alerts.services.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class FireStationsController {

    @Autowired
    FireStationService firestationService;

    @GetMapping(path = "firestation?stationNumber=<{stationNumber}r>")
    public Map<String, Object> coveredPersons(@PathVariable String stationNumber) {
        return firestationService.coverage(stationNumber);
    }

    @GetMapping(path = "phoneAlert?firestation=<{stationNumber}>")
    public ArrayList<String> phoneAlert(@PathVariable String stationNumber) {
        return firestationService.phoneAlert(stationNumber);
    }

    @GetMapping(path = "flood/stations?stations=<{station_numbers}>")
    public ArrayList<Map> floodAlert(@PathVariable ArrayList<Integer> stationNumber) {
        return firestationService.floodAlert(stationNumber);
    }

    @GetMapping(path = "firestation")
    public ArrayList<FireStations> getFireStation(){
        return firestationService.getFireStation();
    }

    @GetMapping(path = "firestation/?stationNumber=<{stationNumber}>")
    public FireStations getOneFireStationWithNumber(@PathVariable String stationNumber){
        return firestationService.getOneFireStationWithNumber(stationNumber);
    }

    @GetMapping(path = "firestation/?address=<{address}>")
    public FireStations getOneFireStationWithAddress(@PathVariable String address){
        return firestationService.getOneFireStationWithAddress(address);
    }

    @PostMapping(path = "firestation")
    public ArrayList<FireStations> addFireStation(@RequestBody FireStations fireStation){
        return firestationService.addFireStation(fireStation);
    }

    @PutMapping(path = "firestation?{address}")
    public FireStations updateFireStationNumber (@RequestBody FireStations fireStation, @PathVariable String address){
        return firestationService.updateFireStationNumber(fireStation, address);
    }

    @DeleteMapping(path = "firestation?{address}")
    public FireStations deleteFireStation(FireStations fireStation){
        return firestationService.deleteFireStation(fireStation);
    }
}