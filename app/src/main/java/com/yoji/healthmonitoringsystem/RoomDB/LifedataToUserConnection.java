package com.yoji.healthmonitoringsystem.RoomDB;

import androidx.room.Embedded;
import androidx.room.Relation;

public class LifedataToUserConnection {
    @Embedded
    public UserData userDataTable;
    @Relation(
            parentColumn = "user_id",
            entityColumn = "user_id"
    )
    public Lifedata lifedataTable;
}
