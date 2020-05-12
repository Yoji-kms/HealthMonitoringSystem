package com.yoji.healthmonitoringsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.yoji.healthmonitoringsystem.db.BloodPressureDataEntry;
import com.yoji.healthmonitoringsystem.db.UserDBHelper;

import java.util.Calendar;


public class RecordingBloodPressureDataActivity extends AppCompatActivity {

    private static long userId;
    private EditText systolicPressureEditText;
    private EditText diastolicPressureEditText;
    private EditText pulseEditText;
    private CheckBox tachycardiaCheckBox;
    private Button saveButton;
//    private UserDBHelper dbHelper;
//    private SQLiteDatabase db;

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
//        initDbHelper();
        saveButtonAction();
    }

    public void recordingLifedataButtonInit() {
        Button recordingLifedataButton = findViewById(R.id.recordingLifedataButtonId);
        recordingLifedataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String LOG_TAG = "Logs";
                Log.i(LOG_TAG, "Нажата кнопка \"Запись жизненных показателей\" на экране записи показателей давления");
                Intent intent = new Intent(RecordingBloodPressureDataActivity.this, RecordingLifedataActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initViews (){
        systolicPressureEditText = findViewById(R.id.systolicPressureEditTextId);
        diastolicPressureEditText = findViewById(R.id.diastolicPressureEditTextId);
        pulseEditText = findViewById(R.id.pulseEditTextId);
        tachycardiaCheckBox = findViewById(R.id.tachycardiaCheckBoxId);

        systolicPressureEditText.addTextChangedListener(textWatcher);
        diastolicPressureEditText.addTextChangedListener(textWatcher);
        pulseEditText.addTextChangedListener(textWatcher);
    }

//    public void initDbHelper (){
//        dbHelper = new UserDBHelper(RecordingBloodPressureDataActivity.this);
//    }

    public void saveButtonAction() {
        saveButton = findViewById(R.id.saveBloodPressureDataButtonId);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String LOG_TAG = "Logs";
                Log.i(LOG_TAG, "Нажата кнопка \"Сохранить\" на экране записи показателей давления");

                int systolicPressure = Integer.parseInt(systolicPressureEditText.getText().toString());
                int diastolicPressure = Integer.parseInt(diastolicPressureEditText.getText().toString());
                int pulse = Integer.parseInt(pulseEditText.getText().toString());
                boolean tachycardia = tachycardiaCheckBox.isChecked();
                String currentTime = String.valueOf(Calendar.getInstance().getTime());

                Toast.makeText(getApplicationContext(), R.string.data_saved, Toast.LENGTH_SHORT).show();

                systolicPressureEditText.setText("");
                diastolicPressureEditText.setText("");
                pulseEditText.setText("");
                tachycardiaCheckBox.setChecked(false);


//                db = dbHelper.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                values.put(BloodPressureDataEntry.USER_ID, userId);
//                values.put(BloodPressureDataEntry.COLUMN_NAME_DATE_AND_TIME, currentTime);
//                values.put(BloodPressureDataEntry.COLUMN_NAME_SYSTOLIC_PRESSURE, systolicPressure);
//                values.put(BloodPressureDataEntry.COLUMN_NAME_DIASTOLIC_PRESSURE, diastolicPressure);
//                values.put(BloodPressureDataEntry.COLUMN_NAME_PULSE, pulse);
//                values.put(BloodPressureDataEntry.COLUMN_NAME_TACHYCARDIA, tachycardia);
//                db.insert(BloodPressureDataEntry.TABLE_NAME, null, values);
//                db.close();

                //Logging data
//                db = dbHelper.getReadableDatabase();
//                Log.d(LOG_TAG, "--- Rows in Blood Pressure Data table: ---");
//                Cursor c = db.query(BloodPressureDataEntry.TABLE_NAME, null, null, null, null, null, null);
//
//                if (c.moveToFirst()) {
//                    int idColIndex = c.getColumnIndex(BloodPressureDataEntry._ID);
//                    int userIdColIndex = c.getColumnIndex(BloodPressureDataEntry.USER_ID);
//                    int systolicPressureColIndex = c.getColumnIndex(BloodPressureDataEntry.COLUMN_NAME_SYSTOLIC_PRESSURE);
//                    int diastolicPressureColIndex = c.getColumnIndex(BloodPressureDataEntry.COLUMN_NAME_DIASTOLIC_PRESSURE);
//                    int pulseColIndex = c.getColumnIndex(BloodPressureDataEntry.COLUMN_NAME_PULSE);
//                    int tachycardiaColIndex = c.getColumnIndex(BloodPressureDataEntry.COLUMN_NAME_TACHYCARDIA);
//                    int dateAndTimeColIndex = c.getColumnIndex(BloodPressureDataEntry.COLUMN_NAME_DATE_AND_TIME);
//
//                    do {
//                        Log.d(LOG_TAG,
//                                "ID = " + c.getInt(idColIndex) +
//                                        ", user_id = " + c.getString(userIdColIndex) +
//                                        ", systolic_pressure = " + c.getInt(systolicPressureColIndex) +
//                                        ", diastolic_pressure = " + c.getInt(diastolicPressureColIndex) +
//                                        ", pulse = " + c.getInt(pulseColIndex) +
//                                        ", tachycardia = " + c.getInt(tachycardiaColIndex) +
//                                        ", current_time = " + c.getString(dateAndTimeColIndex));
//                    } while (c.moveToNext());
//                } else
//                    Log.d(LOG_TAG, "0 rows");
//                c.close();
//                db.close();
            }
        });
    }
}
