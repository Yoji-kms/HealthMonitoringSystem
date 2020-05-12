package com.yoji.healthmonitoringsystem.RoomDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserData.class, BloodPressureData.class, Lifedata.class,
BloodPressureToUserConnection.class, LifedataToUserConnection.class}, version = 1)
public abstract class UserRoomDatabase extends RoomDatabase {
    public abstract UserDataDAO userDataDAO();
    public abstract BloodPressureDataDAO bloodPressureDataDAO();
    public abstract LifedataDAO lifedataDAO();

    private static volatile UserRoomDatabase INSTANCE;
    
    static UserRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserRoomDatabase.class, "word_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}