package com.yoji.healthmonitoringsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yoji.healthmonitoringsystem.RoomDB.UserData;
import com.yoji.healthmonitoringsystem.RoomDB.UserRepository;

public class MainActivity extends AppCompatActivity {
    private Button saveButton;
    private EditText surnameEditText;
    private EditText nameEditText;
    private EditText secondNameEditText;
    private EditText ageEditText;

    private UserRepository userRepository;
    private String LOG_TAG = "Logs";

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String surname = surnameEditText.getText().toString().trim();
            String name = nameEditText.getText().toString().trim();
            String secondName = secondNameEditText.getText().toString().trim();
            String age = ageEditText.getText().toString().trim();

            saveButton.setEnabled(!surname.isEmpty() && !name.isEmpty()
                    && !secondName.isEmpty() && !age.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initDb();
        saveButtonAction();
    }

    public void initDb() {
        userRepository = new UserRepository(getApplication());
    }

    public void initViews() {
        surnameEditText = findViewById(R.id.surnameEditTextId);
        nameEditText = findViewById(R.id.nameEditTextId);
        secondNameEditText = findViewById(R.id.seccondNameEditTextId);
        ageEditText = findViewById(R.id.ageEditTextId);

        surnameEditText.addTextChangedListener(textWatcher);
        nameEditText.addTextChangedListener(textWatcher);
        secondNameEditText.addTextChangedListener(textWatcher);
        ageEditText.addTextChangedListener(textWatcher);
    }

    public void saveButtonAction() {
        saveButton = findViewById(R.id.saveUserDataButtonId);

        saveButton.setOnClickListener(v -> {
            Log.i(LOG_TAG, "Нажата кнопка \"Сохранить\" на регистрационном экране");

            saveUserDataToDb();

            Intent intent = new Intent(MainActivity.this, BloodPressureAndLifeDataActivity.class);
            startActivity(intent);

            logUserDataDb();
        });
    }

    public void saveUserDataToDb() {
        UserData userData = new UserData();
        String surname = surnameEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        String secondName = secondNameEditText.getText().toString().trim();
        String userName = name + " " + secondName + " " + surname;
        int age = Integer.parseInt(ageEditText.getText().toString().trim());
        userData.setUserName(userName);
        userData.setAge(age);
        long userId = userRepository.insertUserData(userData);
        RecordingBloodPressureDataFragment.setUserId(userId);
        RecordingLifedataFragment.setUserId(userId);

        Toast.makeText(getApplicationContext(), R.string.data_saved, Toast.LENGTH_SHORT).show();
    }

    public void logUserDataDb() {
        Log.d(LOG_TAG, "--- Rows in User Data table: ---");
        userRepository.getAllUsersData().observe(this, usersData -> {
            assert usersData != null;
            for (UserData user : usersData) {
                Log.d(LOG_TAG, "ID = " + user.getUserId() + "; name = " +
                        user.getUserName() + "; age = " + user.getAge());
            }
        });
    }
}
