package com.yoji.healthmonitoringsystem.RoomDB;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class UserRepository {
    private UserDataDAO userDataDAO;
    private BloodPressureDataDAO bloodPressureDataDAO;
    private LifedataDAO lifedataDAO;

    private LiveData<List<UserData>> allUsersData;
    private LiveData<List<Lifedata>> allLifedata;
    private LiveData<List<BloodPressureData>> allBloodPressureData;

    public UserRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        userDataDAO = db.userDataDAO();
        bloodPressureDataDAO = db.bloodPressureDataDAO();
        lifedataDAO = db.lifedataDAO();

        allUsersData = userDataDAO.getUsersData();
        allLifedata = lifedataDAO.getLifedata();
        allBloodPressureData = bloodPressureDataDAO.getBloodPressureData();
    }

    public LiveData<List<UserData>> getAllUsersData() {
        return allUsersData;
    }

    public LiveData<List<Lifedata>> getAllLifedata() {
        return allLifedata;
    }

    public long insertUserData(UserData userData) {
        Future<Long> userIdFuture = UserRoomDatabase.databaseWriteExecutor.submit(() -> userDataDAO.insert(userData));
        long userId = 0;
        try {
            userId = userIdFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return userId;
    }

    public LiveData<List<BloodPressureData>> getAllBloodPressureData() {
        return allBloodPressureData;
    }

    public void insertBloodPressureData(BloodPressureData bloodPressureData) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> bloodPressureDataDAO.insert(bloodPressureData));
    }

    public void insertLifedata(Lifedata lifedata) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> lifedataDAO.insert(lifedata));
    }
}
