package com.yoji.healthmonitoringsystem.RoomDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lifedata")
public class Lifedata {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "date")
    private String date;
    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }

    @ColumnInfo(name = "weight")
    private int weight;
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public int getWeight() {
        return weight;
    }

    @ColumnInfo(name = "qty_of_steps")
    private int qtyOfSteps;
    public void setQtyOfSteps(int qtyOfSteps) {
        this.qtyOfSteps = qtyOfSteps;
    }
    public int getQtyOfSteps() {
        return qtyOfSteps;
    }
}
