package com.SafetyNet.SafetyNet.Alerts.services;

import com.SafetyNet.SafetyNet.Alerts.dao.PersonsDAO;
import com.SafetyNet.SafetyNet.Alerts.models.Persons;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PersonsService {

    private static PersonsDAO personsDAO;

    public ArrayList<Persons> getAllPersons() {
        return personsDAO.getAllPersons();
    }

    public Persons getOnePerson(String firstName, String LastName) {
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

    public ArrayList<String> getMailPersons(String city) {
        ArrayList<Persons> personsList = personsDAO.getAllPersons();
        ArrayList<String> personsMail = new ArrayList<>();
        for(int i = 0; i < personsList.size(); i++){
            if (personsList.get(i).getCity().contains(city)) {
                personsMail.add(personsList.get(i).getFirstName() + personsList.get(i).getLastName() + personsList.get(i).getEmail());
            }
        }
        return personsMail;
    }

    public ArrayList<Persons> getChildList(String address) {
        ArrayList<Persons> personsList = personsDAO.getAllPersons();

        return null
    }
}
