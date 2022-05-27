package com.SafetyNet.SafetyNet.Alerts.controllers;

import com.SafetyNet.SafetyNet.Alerts.models.Persons;
import com.SafetyNet.SafetyNet.Alerts.services.PersonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class PersonsController {

    @Autowired
    PersonsService personsService;

    @GetMapping(path = "/person")
    public ArrayList<Persons> getAllPersons() {
        return personsService.getAllPersons();
    }

    @GetMapping(path = "/personInfo?firstName=<{firstName}&lastName=<{lastName}>")
    public Persons getOnePerson(@PathVariable String firstName, @PathVariable String lastName) {
        return personsService.getOnePerson(firstName, lastName);
    }

    @GetMapping(path = "communityEmail?city=<{city}>")
    public ArrayList<String> getMailPersons(@PathVariable String city) {
        return personsService.getMailPersons(city);
    }

    @GetMapping(path = "childAlert?address=<{address}>")
    public ArrayList<Persons> getChildList(@PathVariable String address) {
        return personsService.getChildList(address);
    }

    @PostMapping(path = "/person")
    public Persons addOnePerson(@RequestBody Persons person){
        return personsService.addOnePerson(person);
    }

    @PutMapping(path = "/person?{address}")
    public Persons updateOnePerson (@RequestBody Persons person, @RequestBody String firstName, @RequestBody String LastName){
        return personsService.updateOnePerson(person, firstName, LastName);
    }

    @DeleteMapping(path = "/person")
    public Persons deleteOnePersons(@RequestBody String firstName, @RequestBody String LastName){
        return personsService.deleteOnePersons(firstName, LastName);
    }
}
