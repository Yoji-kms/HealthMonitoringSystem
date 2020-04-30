package com.yoji.healthmonitoringsystem.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserData.db";

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_USER_DATA_ENTRIES = "CREATE TABLE " + UserDataEntry.TABLE_NAME + " (" +
                UserDataEntry._ID + " INTEGER PRIMARY KEY," +
                UserDataEntry.COLUMN_NAME_USER_NAME + " TEXT," +
                UserDataEntry.COLUMN_NAME_AGE + " INTEGER)";
        db.execSQL(SQL_CREATE_USER_DATA_ENTRIES);

        String SQL_CREATE_BLOOD_PRESSURE_DATA_ENTRIES = "CREATE TABLE " + BloodPressureDataEntry.TABLE_NAME + " (" +
                BloodPressureDataEntry._ID + " INTEGER PRIMARY KEY," +
                BloodPressureDataEntry.USER_ID + " INTEGER," +
                BloodPressureDataEntry.COLUMN_NAME_DATE_AND_TIME + " TEXT," +
                BloodPressureDataEntry.COLUMN_NAME_SYSTOLIC_PRESSURE + " INTEGER," +
                BloodPressureDataEntry.COLUMN_NAME_DIASTOLIC_PRESSURE + " INTEGER," +
                BloodPressureDataEntry.COLUMN_NAME_PULSE + " INTEGER," +
                BloodPressureDataEntry.COLUMN_NAME_TACHYCARDIA + " NUMERIC," +
                "FOREIGN KEY(" + BloodPressureDataEntry.USER_ID + ") REFERENCES " +
                UserDataEntry.TABLE_NAME + "(" + UserDataEntry._ID +"))";
        db.execSQL(SQL_CREATE_BLOOD_PRESSURE_DATA_ENTRIES);

        String SQL_CREATE_LIFEDATA_ENTRIES = "CREATE TABLE " + LifedataEntry.TABLE_NAME + " (" +
                LifedataEntry._ID + " INTEGER PRIMARY KEY," +
                LifedataEntry.USER_ID + " INTEGER," +
                LifedataEntry.COLUMN_NAME_DATE_AND_TIME + " NUMERIC," +
                LifedataEntry.COLUMN_NAME_WEIGHT + " INTEGER," +
                LifedataEntry.COLUMN_NAME_QUANTITY_OF_STEPS + " INTEGER," +
                "FOREIGN KEY(" + LifedataEntry.USER_ID + ") REFERENCES " +
                UserDataEntry.TABLE_NAME + "(" + UserDataEntry._ID +"))";
        db.execSQL(SQL_CREATE_LIFEDATA_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_USER_DATA_ENTRIES = "DROP TABLE IF EXISTS " + UserDataEntry.TABLE_NAME;
        db.execSQL(SQL_DELETE_USER_DATA_ENTRIES);

        String SQL_DELETE_BLOOD_PRESSURE_DATA_ENTRIES = "DROP TABLE IF EXISTS " + UserDataEntry.TABLE_NAME;
        db.execSQL(SQL_DELETE_BLOOD_PRESSURE_DATA_ENTRIES);

        String SQL_DELETE_LifedataEntry_ENTRIES = "DROP TABLE IF EXISTS " + UserDataEntry.TABLE_NAME;
        db.execSQL(SQL_DELETE_LifedataEntry_ENTRIES);

        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
