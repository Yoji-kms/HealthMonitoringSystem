package com.yoji.healthmonitoringsystem.db;

import android.provider.BaseColumns;

public class LifedataEntry implements BaseColumns {
    public static final String TABLE_NAME = "lifedata";
    public static final String USER_ID = "user_id";
    public static final String COLUMN_NAME_DATE_AND_TIME = "date_and_time";
    public static final String COLUMN_NAME_WEIGHT = "weight";
    public static final String COLUMN_NAME_QUANTITY_OF_STEPS = "quantity_of_steps";
}