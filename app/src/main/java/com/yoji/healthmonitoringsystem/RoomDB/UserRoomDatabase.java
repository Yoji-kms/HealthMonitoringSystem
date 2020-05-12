package com.yoji.healthmonitoringsystem.RoomDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserData.class, BloodPressureData.class, Lifedata.class,
BloodPressureToUserConnection.class, LifedataToUserConnection.class}, version = 1)
public abstract class UserRoomDatabase extends RoomDatabase {

}