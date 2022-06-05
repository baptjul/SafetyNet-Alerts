package com.SafetyNet.SafetyNet.Alerts.services;


import com.SafetyNet.SafetyNet.Alerts.models.MedicalRecords;
import com.SafetyNet.SafetyNet.Alerts.models.Persons;
import com.SafetyNet.SafetyNet.Alerts.models.FireStations;
import com.SafetyNet.SafetyNet.Alerts.dao.FireStationsDAO;
import com.SafetyNet.SafetyNet.Alerts.dao.PersonsDAO;
import com.SafetyNet.SafetyNet.Alerts.dao.MedicalRecordsDAO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FireStationService {

    private static FireStationsDAO fireStationsDAO;
    private static PersonsDAO personsDAO;
    private static MedicalRecordsDAO medicalRecordsDAO;
    private static MedicalRecordService medicalRecordService;
    private static PersonsService personsService;

    public ArrayList<FireStations> getFireStation() {
        return fireStationsDAO.getFireStation();
    }

    public FireStations getOneFireStationWithNumber(String stationNumber) {
        return fireStationsDAO.getOneFireStationWithNumber(stationNumber);
    }

    public FireStations getOneFireStationWithAddress(String address) {
        return fireStationsDAO.getOneFireStationWithAddress(address);
    }

    public ArrayList<FireStations> addFireStation(FireStations fireStation) {
        return fireStationsDAO.addFireStation(fireStation);
    }

    public FireStations updateFireStationNumber(FireStations fireStation, String address) {
        return fireStationsDAO.updateFireStationNumber(fireStation, address);
    }

    public ArrayList<FireStations> deleteFireStation(FireStations fireStation) {
        return fireStationsDAO.deleteFireStation(fireStation);
    }

    public Map<String, Object> coverage(String stationNumber) {
        ArrayList<Persons> coveredPersons = personsService.coveredPersons(stationNumber);
        int over18 = 0;
        int under18 = 0;
        for (Persons coveredPerson : coveredPersons) {
            String firstName = coveredPerson.getFirstName();
            String lastName = coveredPerson.getLastName();
            int personAge = medicalRecordService.findPersonsAge(firstName, lastName);
            if (personAge > 18) {
                over18++;
            } else {
                under18++;
            }
        }

        final Map<String, Object> coverage = new HashMap<>();
        coverage.put("person covered", coveredPersons);
        coverage.put("person > 18", over18);
        coverage.put("person < 18", under18);
        return coverage;
    }

    public ArrayList<String> phoneAlert(String stationNumber) {
        ArrayList<Persons> coveredPersons = personsService.coveredPersons(stationNumber);
        ArrayList<String> personsPhone = new ArrayList<>();
        for (Persons coveredPerson : coveredPersons) {
            personsPhone.add(coveredPerson.getPhone());
        }
        return personsPhone;
    }

    public ArrayList<Map> floodAlert(ArrayList<Integer> stationNumber) {
        ArrayList<Map> personsIntel = new ArrayList<>();
        for (Integer station : stationNumber) {
            FireStations fireStation = this.getOneFireStationWithNumber(station.toString());
            String address = fireStation.getStationAddress();
            ArrayList<Persons> coveredPersons = personsService.coveredPersons(station.toString());
            Map<String, Object> inhabitant = new HashMap<>();
            for (Persons persons : coveredPersons) {
                String firstName = persons.getFirstName();
                String lastName = persons.getLastName();
                String phone = persons.getPhone();
                int personAge = medicalRecordService.findPersonsAge(firstName, lastName);
                MedicalRecords antecedent = medicalRecordService.getPersonAntecedent(firstName, lastName);
                inhabitant.put("name", lastName);
                inhabitant.put("phone", phone);
                inhabitant.put("age", personAge);
                inhabitant.put("antecedent", antecedent);
                personsIntel.add(inhabitant);
                inhabitant.clear();
            }
        }

        return personsIntel;
    }
}
