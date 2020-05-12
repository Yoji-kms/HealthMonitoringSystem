package com.yoji.healthmonitoringsystem.RoomDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_data")
public class UserData {
    @PrimaryKey(autoGenerate = true)
    private int userId;
    public int getUserId() {
        return userId;
    }

    @ColumnInfo(name = "user_name")
    private String userName;
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    @ColumnInfo(name = "age")
    private int age;
    public void setAge(int age) {
        this.age = age;
    }
    public int getAge() {
        return age;
    }
}
