package com.SafetyNet.SafetyNet.Alerts.services;

import com.SafetyNet.SafetyNet.Alerts.dao.MedicalRecordsDAO;
import com.SafetyNet.SafetyNet.Alerts.models.MedicalRecords;
import com.SafetyNet.SafetyNet.Alerts.models.Persons;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class MedicalRecordService {

    private static final Logger logger = LogManager.getLogger("MedicalRecordService");

    @Autowired
    private static MedicalRecordsDAO medicalRecordsDAO;

    public ArrayList<MedicalRecords> getAllMedicalRecord(){
        return medicalRecordsDAO.getAllMedicalRecord();
    }

    public MedicalRecords getPersonMedicalRecord(String firstName, String lastName) {
        logger.info("access_person's_Medical_Record");
        try {
            return medicalRecordsDAO.getPersonMedicalRecord(firstName, lastName);
        } catch(Exception e) {
            logger.error("Unable to access person's medical record",e);
            return null;
        }
    }

    public ArrayList<MedicalRecords> addMedicalRecord(MedicalRecords medicalRecord){
        logger.info("access_add_Medical_Record");
        try {
            return medicalRecordsDAO.addMedicalRecord(medicalRecord);
        } catch(Exception e) {
            logger.error("Unable to add a medical record",e);
            return null;
        }
    }

    public MedicalRecords updateMedicalRecord(String firstName, String lastName, MedicalRecords medicalRecord) {
        logger.info("access_update_Medical_Record");
        try {
            return medicalRecordsDAO.updateMedicalRecord(firstName, lastName, medicalRecord);
        } catch(Exception e) {
            logger.error("Unable to update a medical record",e);
            return null;
        }
    }

    public MedicalRecords deleteMedicalRecord(String firstName, String lastName){
        logger.info("access_delete_Medical_Record");
        try {
            return medicalRecordsDAO.deleteMedicalRecord(firstName, lastName);
        } catch(Exception e) {
            logger.error("Unable to delete a medical record",e);
            return null;
        }
    }

    public MedicalRecords getPersonAntecedent(String firstName, String lastName) {
        // return medic + allergies
        logger.info("access_person's_antecedent");
        try {
            return medicalRecordsDAO.getPersonAntecedent(firstName, lastName);
        } catch(Exception e) {
            logger.error("Unable to access person's antecedent",e);
            return null;
        }
    }

    public int findPersonsAge(String firstName, String lastName) {
        logger.info("access_person's_age");
        try {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int currentYear = calendar.get(Calendar.YEAR);
        MedicalRecords medicalRecord = medicalRecordsDAO.getPersonMedicalRecord(firstName, lastName);
        String birthDate = medicalRecord.getBirthdate();
        String birthYearStr = birthDate.substring(birthDate.lastIndexOf("/") + 1);
        int birthYearInt = Integer.parseInt(birthYearStr);
        return currentYear - birthYearInt;
        } catch(Exception e) {
            logger.error("Unable to access person's age",e);
            return 0;
        }
    }
}
