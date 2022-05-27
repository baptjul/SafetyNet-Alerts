package com.SafetyNet.SafetyNet.Alerts.constants;

public class DBConstants {
    public static final String GET_ONE_FIRESTATION = "";
    public static final String GET_ALL_FIRESTATION = "";
    public static final String ADD_FIRESTATION  = "";
    public static final String UPDATE_FIRESTATION = "";
    public static final String DELETE_FIRESTATION = "";
    public static final String GET_COVERED_PERSONS = "select count(*) from persons where address like '%?%'";


}
