package com.SafetyNet.SafetyNet.Alerts.services;


import com.SafetyNet.SafetyNet.Alerts.models.MedicalRecords;
import com.SafetyNet.SafetyNet.Alerts.models.Persons;
import com.SafetyNet.SafetyNet.Alerts.models.FireStations;
import com.SafetyNet.SafetyNet.Alerts.dao.FireStationsDAO;
import com.SafetyNet.SafetyNet.Alerts.dao.PersonsDAO;
import com.SafetyNet.SafetyNet.Alerts.dao.MedicalRecordsDAO;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.Logger;

import java.util.*;

@Service
public class FireStationService {

    private static final Logger logger = LogManager.getLogger("FireStationService");

    @Autowired
    private static FireStationsDAO fireStationsDAO;
    @Autowired
    private static PersonsDAO personsDAO;
    @Autowired
    private static MedicalRecordsDAO medicalRecordsDAO;

    private static MedicalRecordService medicalRecordService;
    private static PersonsService personsService;

    public ArrayList<FireStations> getFireStation() {
        logger.info("access_all_fireStations");
        try {
            return fireStationsDAO.getFireStation();
        } catch(Exception e) {
            logger.error("Unable to access all fireStations",e);
            return null;
        }
    }

    public FireStations getOneFireStationWithNumber(String stationNumber) {
        logger.info("access_single_fireStation_with_number");
        try {
            return fireStationsDAO.getOneFireStationWithNumber(stationNumber);
        } catch(Exception e) {
            logger.error("Unable to access fireStation with it's number",e);
            return null;
        }
    }

    public FireStations getOneFireStationWithAddress(String address) {
        logger.info("access_single_fireStation_with_address");
        try {
            return fireStationsDAO.getOneFireStationWithAddress(address);
        } catch(Exception e) {
            logger.error("Unable to access fireStation with it's address",e);
            return null;
        }
    }

    public ArrayList<FireStations> addFireStation(FireStations fireStation) {
        logger.info("access_add_fireStation");
        try {
            return fireStationsDAO.addFireStation(fireStation);
        } catch(Exception e) {
            logger.error("Unable to add fireStation",e);
            return null;
        }
    }

    public FireStations updateFireStationNumber(FireStations fireStation, String address) {
        logger.info("access_update_fireStation");
        try {
            return fireStationsDAO.updateFireStationNumber(fireStation, address);
        } catch(Exception e) {
            logger.error("Unable to update fireStation",e);
            return null;
        }
    }

    public FireStations deleteFireStation(FireStations fireStation) {
        logger.info("access_delete_fireStation");
        try {
            return fireStationsDAO.deleteFireStation(fireStation);
        } catch(Exception e) {
            logger.error("Unable to delete fireStation",e);
            return null;
        }
    }

    public Map<String, Object> coverage(String stationNumber) {
        logger.info("access_fireStation_coverage");
        final Map<String, Object> coverage = new HashMap<>();
        try{
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


        coverage.put("person covered", coveredPersons);
        coverage.put("person > 18", over18);
        coverage.put("person < 18", under18);
        } catch(Exception e) {
            logger.error("Unable to find people covered by this fireStation",e);
        }
        return coverage;
    }

    public ArrayList<String> phoneAlert(String stationNumber) {
        logger.info("access_phoneAlert");
        ArrayList<String> personsPhone = new ArrayList<>();
        try {
            ArrayList<Persons> coveredPersons = personsService.coveredPersons(stationNumber);
            for (Persons coveredPerson : coveredPersons) {
                personsPhone.add(coveredPerson.getPhone());
            }
        } catch(Exception e) {
            logger.error("Unable to find people's phone for this fireStation",e);
        }
        return personsPhone;
    }

    // !!
    public ArrayList<Map> floodAlert(ArrayList<Integer> stationNumber) {
        logger.info("access_floodAlert");
        ArrayList<Map> personsIntel = new ArrayList<>();
        try {
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
        } catch(Exception e) {
            logger.error("Unable to create list for a flood alert",e);
        }
        return personsIntel;
    }
}
