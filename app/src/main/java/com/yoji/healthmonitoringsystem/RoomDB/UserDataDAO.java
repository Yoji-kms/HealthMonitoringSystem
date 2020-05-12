package com.yoji.healthmonitoringsystem.RoomDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDataDAO {

    @Query("SELECT * FROM user_data")
    List<UserData> getUsersData();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(UserData...usersData);

    @Delete
    void delete(UserData userData);
}
