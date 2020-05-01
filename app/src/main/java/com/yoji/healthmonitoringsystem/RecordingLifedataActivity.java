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
import android.widget.EditText;
import android.widget.Toast;

import com.yoji.healthmonitoringsystem.db.LifedataEntry;
import com.yoji.healthmonitoringsystem.db.UserDBHelper;

import java.util.Calendar;
import java.util.Date;

public class RecordingLifedataActivity extends AppCompatActivity {

    private static long userId;
    private EditText weightEditText;
    private EditText quantityOfStepsEditText;
    private Button saveButton;
    private UserDBHelper dbHelper;
    private SQLiteDatabase db;

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

        initViews ();
        initDbHelper();
        recordingBloodPressureDataButtonInit();
        saveButtonAction();
    }

    public void initViews(){
        weightEditText = findViewById(R.id.weightEditTextId);
        quantityOfStepsEditText = findViewById(R.id.quantityOfStepsEditTextId);

        weightEditText.addTextChangedListener(textWatcher);
        quantityOfStepsEditText.addTextChangedListener(textWatcher);
    }

    public void initDbHelper (){
        dbHelper = new UserDBHelper(RecordingLifedataActivity.this);
    }

    public void recordingBloodPressureDataButtonInit() {
        Button recordingBloodPressureDataButton = findViewById(R.id.recordingBloodPressureButtonId);
        recordingBloodPressureDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String LOG_TAG = "Logs";
                Log.i(LOG_TAG, "Нажата кнопка \"Запись показателей давления\" на экране записи жизненных показателей");
                Intent intent = new Intent(RecordingLifedataActivity.this, RecordingBloodPressureDataActivity.class);
                startActivity(intent);
            }
        });
    }

    public void saveButtonAction(){
        saveButton = findViewById(R.id.saveLifedataButtonId);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String LOG_TAG = "Logs";
                Log.i(LOG_TAG, "Нажата кнопка \"Сохранить\" на экране записи жизненных показателей");

                int weight = Integer.parseInt(weightEditText.getText().toString());
                int quantityOfSteps = Integer.parseInt(quantityOfStepsEditText.getText().toString());
                Date currentTime = Calendar.getInstance().getTime();

                Toast.makeText(getApplicationContext(), R.string.data_saved, Toast.LENGTH_SHORT).show();

                weightEditText.setText("");
                quantityOfStepsEditText.setText("");

                db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(LifedataEntry.USER_ID, userId);
                values.put(LifedataEntry.COLUMN_NAME_WEIGHT, weight);
                values.put(LifedataEntry.COLUMN_NAME_QUANTITY_OF_STEPS, quantityOfSteps);
                values.put(LifedataEntry.COLUMN_NAME_DATE_AND_TIME, String.valueOf(currentTime));
                db.insert(LifedataEntry.TABLE_NAME, null, values);
                db.close();

                //Logging data
                db = dbHelper.getReadableDatabase();
                Log.d(LOG_TAG, "--- Rows in Blood Pressure Data table: ---");
                Cursor c = db.query(LifedataEntry.TABLE_NAME, null, null, null, null, null, null);

                if (c.moveToFirst()) {
                    int idColIndex = c.getColumnIndex(LifedataEntry._ID);
                    int userIdColIndex = c.getColumnIndex(LifedataEntry.USER_ID);
                    int weightColIndex = c.getColumnIndex(LifedataEntry.COLUMN_NAME_WEIGHT);
                    int quantityOfStepsColIndex = c.getColumnIndex(LifedataEntry.COLUMN_NAME_QUANTITY_OF_STEPS);
                    int dateAndTimeColIndex = c.getColumnIndex(LifedataEntry.COLUMN_NAME_DATE_AND_TIME);

                    do {
                        Log.d(LOG_TAG,
                                "ID = " + c.getInt(idColIndex) +
                                        ", user_id = " + c.getString(userIdColIndex) +
                                        ", weight = " + c.getInt(weightColIndex) +
                                        ", quantity_of_steps = " + c.getInt(quantityOfStepsColIndex) +
                                        ", current_time = " + c.getString(dateAndTimeColIndex));
                    } while (c.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                c.close();
                db.close();
            }
        });
    }
}
