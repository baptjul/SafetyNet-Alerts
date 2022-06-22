package com.SafetyNet.SafetyNet.Alerts.services;

import com.SafetyNet.SafetyNet.Alerts.dao.FireStationsDAO;
import com.SafetyNet.SafetyNet.Alerts.dao.PersonsDAO;
import com.SafetyNet.SafetyNet.Alerts.models.MedicalRecords;
import com.SafetyNet.SafetyNet.Alerts.models.Persons;
import com.SafetyNet.SafetyNet.Alerts.models.FireStations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PersonsService {

    private static final Logger logger = LogManager.getLogger("PersonsService");

    @Autowired
    private static PersonsDAO personsDAO;
    @Autowired
    private static FireStationsDAO fireStationsDAO;
    private static FireStationService fireStationService;
    private static MedicalRecordService medicalRecordService;

    public ArrayList<Persons> getAllPersons() {
        return personsDAO.getAllPersons();
    }

    public ArrayList<Persons> getPersonFromName(String firstName, String LastName) {
        logger.info("access_person_from_name");
        try {
            return personsDAO.getOnePerson(firstName, LastName);
        } catch(Exception e) {
            logger.error("Unable to access person with name",e);
            return null;
        }
    }

    public Persons addOnePerson(Persons person) {
        logger.info("access_add_person");
        try {
            return personsDAO.addOnePerson(person);
        } catch(Exception e) {
            logger.error("Unable to add a person",e);
            return null;
        }
    }

    public Persons deleteOnePersons(String firstName, String LastName) {
        logger.info("access_delete_person");
        try {
            return personsDAO.deleteOnePersons(firstName, LastName);
        } catch(Exception e) {
            logger.error("Unable to delete a person",e);
            return null;
        }
    }

    public Persons updateOnePerson(Persons person, String firstName, String LastName) {
        logger.info("access_update_person");
        try {
            return personsDAO.updateOnePerson(person, firstName, LastName);
        } catch(Exception e) {
            logger.error("Unable to update a person",e);
            return null;
        }
    }

    public static ArrayList<Persons> coveredPersons(String stationNumber) {
        logger.info("access_persons_covered");
        try {
            FireStations fireStation = fireStationsDAO.getOneFireStationWithNumber(stationNumber);
            String fireStationAddress = fireStation.getStationAddress();
            return personsDAO.getAllPersonsForAddress(fireStationAddress);
        } catch(Exception e) {
            logger.error("Unable to find persons covered",e);
            return null;
        }
    }

    public ArrayList<String> getMailPersons(String city) {
        logger.info("access_persons_mail_info");
        ArrayList<String> personsMail = new ArrayList<>();
        try {
            ArrayList<Persons> personsList = personsDAO.getAllPersons();
            for (Persons persons : personsList) {
                if (persons.getCity().contains(city)) {
                    personsMail.add(persons.getFirstName() + persons.getLastName() + persons.getEmail());
                }
            }
        } catch(Exception e) {
            logger.error("Unable to find mail info on this city",e);
        }
        return personsMail;
    }

    public Map<String, Object> childAlert(String address) {
        logger.info("access_child_alert_list");
        Map<String, Object> childList = new HashMap<>();
        try {
            ArrayList<Persons> personsList = personsDAO.getAllPersonsForAddress(address);
            Map<String, Object> childs = new HashMap<>();
            Map<String, Object> adults = new HashMap<>();
            for (Persons persons : personsList) {
                String firstName = persons.getFirstName();
                String lastName = persons.getLastName();
                String fullName = firstName + ' ' + lastName;
                int personAge = medicalRecordService.findPersonsAge(firstName, lastName);
                if (personAge < 18) {
                    childs.put(fullName, personAge);
                } else {
                    adults.put(fullName, personAge);
                }
            }

            if (!childs.isEmpty()) {
                childList.putAll(childs);
                childList.putAll(adults);
            }
        } catch(Exception e) {
            logger.error("Unable to find anyone on this address",e);
        }
        return childList;
    }

    public ArrayList<Map> fireAddress(String address) {
        logger.info("access_persons_from_address");
        ArrayList<Map> allInhabitant = new ArrayList<>();
        try {
        ArrayList<Persons> personsList = personsDAO.getAllPersonsForAddress(address);
        FireStations fireStation = fireStationService.getOneFireStationWithAddress(address);
        Map<String, Object> inhabitant = new HashMap<>();
        for (Persons persons : personsList) {
            String firstName = persons.getFirstName();
            String lastName = persons.getLastName();
            String phone = persons.getPhone();
            int personAge = medicalRecordService.findPersonsAge(firstName, lastName);
            MedicalRecords antecedent = medicalRecordService.getPersonAntecedent(firstName, lastName);
            inhabitant.put("name", lastName);
            inhabitant.put("phone", phone);
            inhabitant.put("age", personAge);
            inhabitant.put("antecedent", antecedent);
            inhabitant.put("fireStation", fireStation);
            allInhabitant.add(inhabitant);
            inhabitant.clear();
        }
        } catch(Exception e) {
            logger.error("Unable to find anyone living on this address",e);
        }
        return allInhabitant;
    }

    public Persons getPersonInfo(String firstName, String lastName) {
        logger.info("access_persons_info");
        try {
        ArrayList<Persons> persons = this.getPersonFromName(firstName, lastName);
        ArrayList<Persons> result = new ArrayList<>();
        if (persons.size() > 1) {
            for (Persons person : persons) {
                int personAge = medicalRecordService.findPersonsAge(firstName, lastName);
                MedicalRecords antecedent = medicalRecordService.getPersonAntecedent(firstName, lastName);
                String name = person.getLastName();
                String address = person.getAddress();
                String mail = person.getEmail();
            }
        } else {
            int personAge = medicalRecordService.findPersonsAge(firstName, lastName);
            MedicalRecords antecedent = medicalRecordService.getPersonAntecedent(firstName, lastName);
            String name = persons.get(0).getLastName();
            String address = persons.get(0).getAddress();
            String mail = persons.get(0).getEmail();
        }
        } catch(Exception e) {
            logger.error("Unable to find person's info",e);
        }
        return null;
    }
}
