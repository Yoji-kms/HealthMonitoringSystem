package com.yoji.healthmonitoringsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.yoji.healthmonitoringsystem.RoomDB.BloodPressureData;
import com.yoji.healthmonitoringsystem.RoomDB.UserRepository;

import java.util.Calendar;

public class RecordingBloodPressureDataActivity extends AppCompatActivity {

    private static long userId;
    private EditText systolicPressureEditText;
    private EditText diastolicPressureEditText;
    private EditText pulseEditText;
    private CheckBox tachycardiaCheckBox;
    private Button saveButton;
    private UserRepository userRepository;

    private String LOG_TAG = "Logs";

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String systolicPressure = systolicPressureEditText.getText().toString().trim();
            String diastolicPressure = diastolicPressureEditText.getText().toString().trim();
            String pulse = pulseEditText.getText().toString().trim();

            saveButton.setEnabled(!systolicPressure.isEmpty() && !diastolicPressure.isEmpty()
                    && !pulse.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public static void setUserId(long userId) {
        RecordingBloodPressureDataActivity.userId = userId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_blood_pressure_data);

        recordingLifedataButtonInit();
        initViews();
        initDb();
        saveButtonAction();
    }

    public void recordingLifedataButtonInit() {
        Button recordingLifedataButton = findViewById(R.id.recordingLifedataButtonId);
        recordingLifedataButton.setOnClickListener(v -> {
            final String LOG_TAG = "Logs";
            Log.i(LOG_TAG, "Нажата кнопка \"Запись жизненных показателей\" на экране записи показателей давления");
            Intent intent = new Intent(RecordingBloodPressureDataActivity.this, RecordingLifedataActivity.class);
            startActivity(intent);
        });
    }

    public void initDb() {
        userRepository = new UserRepository(getApplication());
    }

    public void initViews() {
        systolicPressureEditText = findViewById(R.id.systolicPressureEditTextId);
        diastolicPressureEditText = findViewById(R.id.diastolicPressureEditTextId);
        pulseEditText = findViewById(R.id.pulseEditTextId);
        tachycardiaCheckBox = findViewById(R.id.tachycardiaCheckBoxId);

        systolicPressureEditText.addTextChangedListener(textWatcher);
        diastolicPressureEditText.addTextChangedListener(textWatcher);
        pulseEditText.addTextChangedListener(textWatcher);
    }

    public void saveButtonAction() {
        saveButton = findViewById(R.id.saveBloodPressureDataButtonId);

        saveButton.setOnClickListener(v -> {
            Log.i(LOG_TAG, "Нажата кнопка \"Сохранить\" на экране записи показателей давления");

            saveDataToDb();
            clearViews();
            logBloodPressureDataDb();
        });
    }

    public void saveDataToDb() {
        BloodPressureData bloodPressureData = new BloodPressureData();
        int systolicPressure = Integer.parseInt(systolicPressureEditText.getText().toString());
        int diastolicPressure = Integer.parseInt(diastolicPressureEditText.getText().toString());
        int pulse = Integer.parseInt(pulseEditText.getText().toString());
        boolean tachycardia = tachycardiaCheckBox.isChecked();
        String currentDate = String.valueOf(Calendar.getInstance().getTime());

        bloodPressureData.setSystolicPressure(systolicPressure);
        bloodPressureData.setDiastolicPressure(diastolicPressure);
        bloodPressureData.setPulse(pulse);
        bloodPressureData.setTachycardia(tachycardia);
        bloodPressureData.setDate(currentDate);
        bloodPressureData.setUserId(userId);

        userRepository.insertBloodPressureData(bloodPressureData);

        Toast.makeText(getApplicationContext(), R.string.data_saved, Toast.LENGTH_SHORT).show();
    }

    public void clearViews() {
        systolicPressureEditText.setText("");
        diastolicPressureEditText.setText("");
        pulseEditText.setText("");
        tachycardiaCheckBox.setChecked(false);
    }

    public void logBloodPressureDataDb() {
        Log.d(LOG_TAG, "--- Rows in Blood Pressure Data table: ---");
        userRepository.getAllBloodPressureData().observe(this, allBloodPressureData -> {
            for (BloodPressureData bloodPressureData : allBloodPressureData) {
                Log.d(LOG_TAG, "ID = " + bloodPressureData.getId() +
                        "; User ID = " + bloodPressureData.getUserId() +
                        "; Systolic Pressure = " + bloodPressureData.getSystolicPressure() +
                        "; Diastolic Pressure = " + bloodPressureData.getDiastolicPressure() +
                        "; Tachycardia = " + bloodPressureData.isTachycardia() +
                        "; Pulse = " + bloodPressureData.getPulse() +
                        "; Date = " + bloodPressureData.getDate());
            }
        });
    }
}
