package com.yoji.healthmonitoringsystem.RoomDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "lifedata", foreignKeys = @ForeignKey(entity = UserData.class,
        parentColumns = "id", childColumns = "user_id", onDelete = ForeignKey.CASCADE),
        indices = @Index("user_id"))
public class Lifedata {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ColumnInfo(name = "user_id")
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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
