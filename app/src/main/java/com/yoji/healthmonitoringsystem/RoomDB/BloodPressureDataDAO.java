package com.yoji.healthmonitoringsystem.RoomDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface BloodPressureDataDAO {

    @Query("SELECT * FROM blood_pressure_data")
    LiveData<List<BloodPressureData>> getBloodPressureData();

    @Transaction
    @Query("SELECT * FROM user_data WHERE id IN (SELECT DISTINCT(user_id) FROM blood_pressure_data)")
    LiveData<List<BloodPressureOfUser>> getBloodPressureOfUserData();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(BloodPressureData bloodPressureData);
}
