package com.SafetyNet.SafetyNet.Alerts.services;

import com.SafetyNet.SafetyNet.Alerts.dao.MedicalRecordsDAO;
import com.SafetyNet.SafetyNet.Alerts.models.MedicalRecords;
import com.SafetyNet.SafetyNet.Alerts.models.Persons;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class MedicalRecordService {

    private static MedicalRecordsDAO medicalRecordsDAO;

    public ArrayList<MedicalRecords> getAllMedicalRecord(){
        return medicalRecordsDAO.getAllMedicalRecord();
    }

    public MedicalRecords getPersonMedicalRecord(String firstName, String lastName) {
        return medicalRecordsDAO.getPersonMedicalRecord(firstName, lastName);
    }

    public ArrayList<MedicalRecords> addMedicalRecord(MedicalRecords medicalRecord){
        return medicalRecordsDAO.addMedicalRecord(medicalRecord);
    }

    public MedicalRecords updateMedicalRecord(String firstName, String lastName, MedicalRecords medicalRecord) {
        return medicalRecordsDAO.updateMedicalRecord(firstName, lastName, medicalRecord);
    }

    public ArrayList<MedicalRecords> deleteMedicalRecord(String firstName, String lastName){
        return medicalRecordsDAO.deleteMedicalRecord(firstName, lastName);
    }

    public MedicalRecords getPersonAntecedent(String firstName, String lastName) {
        // return medic + allergies
        return medicalRecordsDAO.getPersonAntecedent(firstName, lastName);
    }

    public int findPersonsAge(String firstName, String lastName) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int currentYear = calendar.get(Calendar.YEAR);
        MedicalRecords medicalRecord = medicalRecordsDAO.getPersonMedicalRecord(firstName, lastName);
        String birthDate = medicalRecord.getBirthdate();
        String birthYearStr = birthDate.substring(birthDate.lastIndexOf("/") + 1);
        int birthYearInt = Integer.parseInt(birthYearStr);
        return currentYear - birthYearInt;
    }

}
