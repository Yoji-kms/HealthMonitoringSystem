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

import com.yoji.healthmonitoringsystem.db.UserDBHelper;
import com.yoji.healthmonitoringsystem.db.UserDataEntry;

public class MainActivity extends AppCompatActivity {
    private Button saveButton;
    private EditText surnameEditText;
    private EditText nameEditText;
    private EditText secondNameEditText;
    private EditText ageEditText;
    private UserDBHelper dbHelper;
    private SQLiteDatabase db;

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
        initDbHelper();
        saveButtonAction();
    }

    public void initDbHelper (){
        dbHelper = new UserDBHelper(MainActivity.this);
    }

    public void initViews (){
        surnameEditText = findViewById(R.id.surnameEditTextId);
        nameEditText = findViewById(R.id.nameEditTextId);
        secondNameEditText = findViewById(R.id.seccondNameEditTextId);
        ageEditText = findViewById(R.id.ageEditTextId);

        surnameEditText.addTextChangedListener(textWatcher);
        nameEditText.addTextChangedListener(textWatcher);
        secondNameEditText.addTextChangedListener(textWatcher);
        ageEditText.addTextChangedListener(textWatcher);
    }

    public void saveButtonAction (){
        saveButton = findViewById(R.id.saveUserDataButtonId);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String LOG_TAG = "Logs";
                Log.i(LOG_TAG, "Нажата кнопка \"Сохранить\" на регистрационном экране");

                String surname = surnameEditText.getText().toString().trim();
                String name = nameEditText.getText().toString().trim();
                String secondName = secondNameEditText.getText().toString().trim();
                String userName = name + " " + secondName + " " + surname;
                int age = Integer.parseInt(ageEditText.getText().toString().trim());

                //writing data to database
                db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(UserDataEntry.COLUMN_NAME_USER_NAME, userName);
                values.put(UserDataEntry.COLUMN_NAME_AGE, age);
                long newRowId = db.insert(UserDataEntry.TABLE_NAME, null, values);
                RecordingBloodPressureDataActivity.setUserId(newRowId);
                RecordingLifedataActivity.setUserId(newRowId);
                db.close();

                Toast.makeText(getApplicationContext(), R.string.data_saved, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, RecordingBloodPressureDataActivity.class);
                startActivity(intent);

                //Logging data from database
                db = dbHelper.getReadableDatabase();
                Log.d(LOG_TAG, "--- Rows in User Data table: ---");
                Cursor c = db.query(UserDataEntry.TABLE_NAME, null, null, null, null, null, null);

                if (c.moveToFirst()) {

                    int idColIndex = c.getColumnIndex(UserDataEntry._ID);
                    int userNameColIndex = c.getColumnIndex(UserDataEntry.COLUMN_NAME_USER_NAME);
                    int ageColIndex = c.getColumnIndex(UserDataEntry.COLUMN_NAME_AGE);

                    do {
                        Log.d(LOG_TAG,
                                "ID = " + c.getInt(idColIndex) +
                                        ", name = " + c.getString(userNameColIndex) +
                                        ", age = " + c.getInt(ageColIndex));
                    } while (c.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                c.close();
                db.close();
            }
        });
    }
}
