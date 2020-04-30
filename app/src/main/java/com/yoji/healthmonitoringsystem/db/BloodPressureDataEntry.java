package com.yoji.healthmonitoringsystem.db;

import android.provider.BaseColumns;

public class BloodPressureDataEntry implements BaseColumns {
    public static final String TABLE_NAME = "blood_pressure_data";
    public static final String USER_ID = "user_id";
    public static final String COLUMN_NAME_DATE_AND_TIME = "date_and_time";
    public static final String COLUMN_NAME_SYSTOLIC_PRESSURE = "systolic_pressure";
    public static final String COLUMN_NAME_DIASTOLIC_PRESSURE = "diastolic_pressure";
    public static final String COLUMN_NAME_PULSE = "pulse";
    public static final String COLUMN_NAME_TACHYCARDIA = "tachycardia";
}
