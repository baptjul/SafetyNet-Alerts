package com.SafetyNet.SafetyNet.Alerts.controllers;

import com.SafetyNet.SafetyNet.Alerts.models.MedicalRecords;
import com.SafetyNet.SafetyNet.Alerts.services.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
//@RequestMapping(path = "medicalRecord")
public class MedicalRecordsController {

    @Autowired
    MedicalRecordService medicalRecordService;

    @GetMapping(path = "medicalRecords")
    public ArrayList<MedicalRecords> getAllMedicalRecord(){
        return medicalRecordService.getAllMedicalRecord();
    }

    @GetMapping(path = "medicalRecord?firstName=<{firstName}>&lastName=<{lastName}>")
    public MedicalRecords getPersonMedicalRecord(@PathVariable String firstName, @PathVariable String lastName){
        return medicalRecordService.getPersonMedicalRecord(firstName, lastName);
    }

    @PostMapping(path = "medicalRecords")
    public ArrayList<MedicalRecords> addMedicalRecord(@RequestBody MedicalRecords medicalRecord){
        return medicalRecordService.addMedicalRecord(medicalRecord);
    }

    @PutMapping(path = "medicalRecords")
    public MedicalRecords updateMedicalRecord (@RequestBody String firstName, @RequestBody String lastName, @RequestBody MedicalRecords medicalRecord){
        return medicalRecordService.updateMedicalRecord(firstName, lastName, medicalRecord);
    }

    @DeleteMapping(path = "medicalRecord?firstName=<{firstName}>&lastName=<{lastName}>")
    public ArrayList<MedicalRecords> deleteMedicalRecord(@PathVariable String firstName, @PathVariable String lastName){
        return medicalRecordService.deleteMedicalRecord(firstName, lastName);
    }
}
