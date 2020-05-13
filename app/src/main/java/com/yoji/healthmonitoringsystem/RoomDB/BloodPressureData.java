package com.yoji.healthmonitoringsystem.RoomDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "blood_pressure_data", foreignKeys = @ForeignKey(entity = UserData.class,
        parentColumns = "id", childColumns = "user_id", onDelete = ForeignKey.CASCADE),
        indices = @Index("user_id"))
public class BloodPressureData {
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

    @ColumnInfo(name = "systolic_pressure")
    private int systolicPressure;

    public void setSystolicPressure(int systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public int getSystolicPressure() {
        return systolicPressure;
    }

    @ColumnInfo(name = "diastolic_pressure")
    private int diastolicPressure;

    public void setDiastolicPressure(int diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public int getDiastolicPressure() {
        return diastolicPressure;
    }

    @ColumnInfo(name = "pulse")
    private int pulse;

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public int getPulse() {
        return pulse;
    }

    @ColumnInfo(name = "tachycardia")
    private boolean tachycardia;

    public void setTachycardia(boolean tachycardia) {
        this.tachycardia = tachycardia;
    }

    public boolean isTachycardia() {
        return tachycardia;
    }
}
