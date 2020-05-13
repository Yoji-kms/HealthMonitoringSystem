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

import com.yoji.healthmonitoringsystem.RoomDB.Lifedata;
import com.yoji.healthmonitoringsystem.RoomDB.UserRepository;

import java.util.Calendar;

public class RecordingLifedataActivity extends AppCompatActivity {

    private static long userId;
    private EditText weightEditText;
    private EditText quantityOfStepsEditText;
    private Button saveButton;

    private String LOG_TAG = "Logs";
    private UserRepository userRepository;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String weight = weightEditText.getText().toString().trim();
            String quantityOfSteps = quantityOfStepsEditText.getText().toString().trim();

            saveButton.setEnabled(!weight.isEmpty() && !quantityOfSteps.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public static void setUserId(long userId) {
        RecordingLifedataActivity.userId = userId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_lifedata);

        initViews();
        initDb();
        recordingBloodPressureDataButtonInit();
        saveButtonAction();
    }

    public void initViews() {
        weightEditText = findViewById(R.id.weightEditTextId);
        quantityOfStepsEditText = findViewById(R.id.quantityOfStepsEditTextId);

        weightEditText.addTextChangedListener(textWatcher);
        quantityOfStepsEditText.addTextChangedListener(textWatcher);
    }

    public void initDb() {
        userRepository = new UserRepository(getApplication());
    }

    public void recordingBloodPressureDataButtonInit() {
        Button recordingBloodPressureDataButton = findViewById(R.id.recordingBloodPressureButtonId);
        recordingBloodPressureDataButton.setOnClickListener(v -> {
            final String LOG_TAG = "Logs";
            Log.i(LOG_TAG, "Нажата кнопка \"Запись показателей давления\" на экране записи жизненных показателей");
            Intent intent = new Intent(RecordingLifedataActivity.this, RecordingBloodPressureDataActivity.class);
            startActivity(intent);
        });
    }

    public void saveButtonAction() {
        saveButton = findViewById(R.id.saveLifedataButtonId);

        saveButton.setOnClickListener(v -> {
            Log.i(LOG_TAG, "Нажата кнопка \"Сохранить\" на экране записи жизненных показателей");

            saveDataToDb();
            clearViews();
            logLifedataDb();
        });
    }

    public void saveDataToDb() {
        Lifedata lifedata = new Lifedata();
        int weight = Integer.parseInt(weightEditText.getText().toString());
        int quantityOfSteps = Integer.parseInt(quantityOfStepsEditText.getText().toString());
        String currentDate = String.valueOf(Calendar.getInstance().getTime());

        lifedata.setWeight(weight);
        lifedata.setQtyOfSteps(quantityOfSteps);
        lifedata.setDate(currentDate);
        lifedata.setUserId(userId);

        userRepository.insertLifedata(lifedata);

        Toast.makeText(getApplicationContext(), R.string.data_saved, Toast.LENGTH_SHORT).show();
    }

    public void clearViews() {
        weightEditText.setText("");
        quantityOfStepsEditText.setText("");
    }

    public void logLifedataDb() {
        Log.d(LOG_TAG, "--- Rows in Blood Pressure Data table: ---");
        userRepository.getAllLifedata().observe(this, allLifedata -> {
            for (Lifedata lifedata : allLifedata) {
                Log.d(LOG_TAG, "ID = " + lifedata.getId() +
                        "; User ID = " + lifedata.getUserId() +
                        "; Weight = " + lifedata.getWeight() +
                        "; Quantity of Steps = " + lifedata.getQtyOfSteps() +
                        "; Date = " + lifedata.getDate());
            }
        });
    }
}
