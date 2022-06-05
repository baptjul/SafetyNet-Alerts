package com.SafetyNet.SafetyNet.Alerts.services;

import com.SafetyNet.SafetyNet.Alerts.dao.FireStationsDAO;
import com.SafetyNet.SafetyNet.Alerts.dao.PersonsDAO;
import com.SafetyNet.SafetyNet.Alerts.models.MedicalRecords;
import com.SafetyNet.SafetyNet.Alerts.models.Persons;
import com.SafetyNet.SafetyNet.Alerts.models.FireStations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PersonsService {

    private static PersonsDAO personsDAO;
    private static FireStationsDAO fireStationsDAO;
    private static FireStationService fireStationService;
    private static MedicalRecordService medicalRecordService;

    public ArrayList<Persons> getAllPersons() {
        return personsDAO.getAllPersons();
    }

    public ArrayList<Persons> getPersonFromName(String firstName, String LastName) {
        return personsDAO.getOnePerson(firstName, LastName);
    }

    public Persons addOnePerson(Persons person) {
        return personsDAO.addOnePerson(person);
    }

    public Persons deleteOnePersons(String firstName, String LastName) {
        return personsDAO.deleteOnePersons(firstName, LastName);
    }

    public Persons updateOnePerson(Persons person, String firstName, String LastName) {
        return personsDAO.updateOnePerson(person, firstName, LastName);
    }

    public static ArrayList<Persons> coveredPersons(String stationNumber) {
        FireStations fireStation = fireStationsDAO.getOneFireStationWithNumber(stationNumber);
        String fireStationAddress = fireStation.getStationAddress();
        return personsDAO.getAllPersonsForAddress(fireStationAddress);
    }

    public ArrayList<String> getMailPersons(String city) {
        ArrayList<Persons> personsList = personsDAO.getAllPersons();
        ArrayList<String> personsMail = new ArrayList<>();
        for (Persons persons : personsList) {
            if (persons.getCity().contains(city)) {
                personsMail.add(persons.getFirstName() + persons.getLastName() + persons.getEmail());
            }
        }
        return personsMail;
    }

    public Map<String, Object> childAlert(String address) {
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

        Map<String, Object> childList = new HashMap<>();
        if (!childs.isEmpty()) {
            childList.putAll(childs);
            childList.putAll(adults);
        }
        return childList;
    }

    public ArrayList<Map> fireAddress(String address) {
        ArrayList<Persons> personsList = personsDAO.getAllPersonsForAddress(address);
        FireStations fireStation = fireStationService.getOneFireStationWithAddress(address);
        ArrayList<Map> allInhabitant = new ArrayList<>();
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
    return allInhabitant;
    }

    public Persons getPersonInfo(String firstName, String lastName) {
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
        return null;
    }
}
