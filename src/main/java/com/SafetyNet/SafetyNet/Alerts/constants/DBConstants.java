package com.SafetyNet.SafetyNet.Alerts.constants;

public class DBConstants {
    public static final String GET_ALL_FIRESTATION = "select count(*) from firestations";
    public static final String GET_ONE_FIRESTATION_FROM_NUMBER = "select count(*) from firestations where NUMBER = ?";
    public static final String GET_ALL_FIRESTATION_FROM_ADDRESS = "select count(*) from firestations where ADDRESS = ?";
    public static final String ADD_FIRESTATION  = "insert into firestations (ADDRESS, STATION) values(?, ?)";
    public static final String UPDATE_FIRESTATION = "update firestations set STATION = ? where ADDRESS = ?";
    public static final String DELETE_FIRESTATION = "delete from firestations where ADDRESS = ? and NUMBER = ?";

    public static final String GET_ALL_MEDICALRECORD = "select count(*) from medicalrecords";
    public static final String GET_PERSON_MEDICALRECORD = "select count(*) from medicalrecords where FIRSTNAME = ? AND LASTNAME = ?";
    public static final String ADD_MEDICALRECORD  = "insert into medicalrecords (FIRSTNAME, LASTNAME, BIRTHDATE, MEDICATIONS, ALLERGIES) values(?, ?, ?, ?, ?)";
    public static final String UPDATE_MEDICALRECORD = "update medicalrecords set BIRTHDATE = ?, MEDICATIONS = ?, ALLERGIES = ? where FIRSTNAME = ? AND LASTNAME = ?";
    public static final String DELETE_MEDICALRECORD = "delete from medicalrecords where FIRSTNAME = ? AND LASTNAME = ?";
    public static final String GET_PERSON_ANTECEDENT = "select MEDICATIONS, ALLERGIES from medicalrecords where FIRSTNAME = ? AND LASTNAME = ?";

    public static final String GET_ONE_PERSONS = "select count(*) from persons";
    public static final String GET_ALL_PERSONS = "select count(*) from persons where FIRSTNAME = ? AND LASTNAME = ?";
    public static final String ADD_PERSONS = "insert into persons (FIRSTNAME, LASTNAME, ADDRESS, CITY, ZIP, PHONE, EMAIL) values(?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_PERSONS = "update persons set ADDRESS = ?, CITY = ?, ZIP = ?, PHONE = ?, EMAIL = ? where FIRSTNAME = ? AND LASTNAME = ?";
    public static final String DELETE_PERSONS = "delete from persons where FIRSTNAME = ? AND LASTNAME = ?";
    public static final String GET_COVERED_PERSONS = "select count(*) from persons where address like '%?%'";
}
