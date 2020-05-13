package com.yoji.healthmonitoringsystem.RoomDB;

import androidx.room.Embedded;
import androidx.room.Relation;

class BloodPressureOfUser {
    @Embedded
    UserData userData;
    @Relation(
            parentColumn = "id",
            entityColumn = "user_id"
    )
    BloodPressureData bloodPressureData;
}
