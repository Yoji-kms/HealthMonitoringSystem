package com.yoji.healthmonitoringsystem.RoomDB;

import androidx.room.Embedded;
import androidx.room.Relation;

public class BloodPressureToUserConnection {
    @Embedded public UserData userDataTable;
    @Relation(
            parentColumn = "user_id",
            entityColumn = "user_id"
    )
    public BloodPressureData bloodPressureDataTable;
}
