package com.yoji.healthmonitoringsystem.RoomDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface LifedataDAO {
    @Transaction
    @Query("SELECT * FROM lifedata")
    List<Lifedata> getLifedata();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Lifedata...lifedata);

    @Delete
    void delete(Lifedata lifedata);
}
