package com.yoji.healthmonitoringsystem.RoomDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface BloodPressureDataDAO {
    @Transaction
    @Query("SELECT * FROM blood_pressure_data")
    List<BloodPressureData> getBloodPressureData();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(BloodPressureData...bloodPressureData);

    @Delete
    void delete(BloodPressureData bloodPressureData);
}
