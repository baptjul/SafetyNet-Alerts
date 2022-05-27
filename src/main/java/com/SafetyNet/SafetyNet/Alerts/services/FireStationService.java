package com.SafetyNet.SafetyNet.Alerts.services;


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

    public ArrayList<FireStations> getFireStation() {
        return fireStationsDAO.getFireStation();
    }

    public FireStations getOneFireStation(String stationNumber) {
        return fireStationsDAO.getOneFireStation(stationNumber);
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

    public static ArrayList<Persons> coveredPersons(String stationNumber) {
        FireStations fireStation = fireStationsDAO.getOneFireStation(stationNumber);
        String fireStationAddress = fireStation.getStationAddress();
        return fireStationsDAO.coveredPersons(fireStationAddress);
    }

    public Map<String, Object> coverage(String stationNumber) {
        ArrayList<Persons> coveredPersons = FireStationService.coveredPersons(stationNumber);
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int currentYear = calendar.get(Calendar.YEAR);
        int over18 = 0;
        int under18 = 0;
        for (int i = 0; i < coveredPersons.size(); i++) {
            String firstName = coveredPersons.get(i).getFirstName();
            String lastName = coveredPersons.get(i).getLastName();
            String mediaclRecord = medicalRecordsDAO.getPersonMedicalRecord(firstName, lastName);
            String birthDate = mediaclRecord.getBirthdate();
            String birthYear = birthDate.substring(birthDate.lastIndexOf("/") + 1);
            int age = currentYear - birthYear
            if (age > 18) {
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
        ArrayList<Persons> coveredPersons = FireStationService.coveredPersons(stationNumber);
        ArrayList<String> personsPhone = new ArrayList<>();
        for (int i = 0; i < coveredPersons.size(); i++) {
            personsPhone.add(coveredPersons.get(i).getPhone());
        }
        return personsPhone;
    }
}
