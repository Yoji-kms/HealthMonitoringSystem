package com.yoji.healthmonitoringsystem.RoomDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface LifedataDAO {
    @Query("SELECT * FROM lifedata")
    LiveData<List<Lifedata>> getLifedata();

    @Transaction
    @Query("SELECT * FROM user_data WHERE id IN (SELECT DISTINCT(user_id) FROM lifedata)")
    LiveData<List<LifedataOfUser>> getLifedataOfUser();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Lifedata lifedata);
}
