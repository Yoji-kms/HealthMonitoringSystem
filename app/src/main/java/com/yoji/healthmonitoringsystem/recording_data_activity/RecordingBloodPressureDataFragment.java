package com.yoji.healthmonitoringsystem.recording_data_activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.yoji.healthmonitoringsystem.R;
import com.yoji.healthmonitoringsystem.RoomDB.BloodPressureData;
import com.yoji.healthmonitoringsystem.RoomDB.UserRepository;

import java.util.Calendar;

public class RecordingBloodPressureDataFragment extends Fragment {
    private View view;

    private static long userId;
    private EditText systolicPressureEditText;
    private EditText diastolicPressureEditText;
    private EditText pulseEditText;
    private CheckBox tachycardiaCheckBox;
    private Button saveButton;
    private UserRepository userRepository;

    private final String LOG_TAG = "Logs";

    private final TextWatcher textWatcher = new TextWatcher() {
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
        RecordingBloodPressureDataFragment.userId = userId;
    }

    public RecordingBloodPressureDataFragment() {
    }

    static RecordingBloodPressureDataFragment newInstance() {
        return new RecordingBloodPressureDataFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recording_blood_pressure_data, container, false);

        initViews();
        initDb();
        saveButtonAction();

        return view;
    }

    private void initDb() {
        userRepository = new UserRepository(requireActivity().getApplication());
    }

    private void initViews() {
        systolicPressureEditText = view.findViewById(R.id.systolicPressureEditTextId);
        diastolicPressureEditText = view.findViewById(R.id.diastolicPressureEditTextId);
        pulseEditText = view.findViewById(R.id.pulseEditTextId);
        tachycardiaCheckBox = view.findViewById(R.id.tachycardiaCheckBoxId);

        systolicPressureEditText.addTextChangedListener(textWatcher);
        diastolicPressureEditText.addTextChangedListener(textWatcher);
        pulseEditText.addTextChangedListener(textWatcher);
    }

    private void saveButtonAction() {
        saveButton = view.findViewById(R.id.saveBloodPressureDataButtonId);

        saveButton.setOnClickListener(v -> {
            Log.i(LOG_TAG, "Нажата кнопка \"Сохранить\" на экране записи показателей давления");

            saveDataToDb();
            clearViews();
            logBloodPressureDataDb();
        });
    }

    private void saveDataToDb() {
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

        Toast.makeText(getContext(), R.string.data_saved, Toast.LENGTH_SHORT).show();
    }

    private void clearViews() {
        systolicPressureEditText.setText("");
        diastolicPressureEditText.setText("");
        pulseEditText.setText("");
        tachycardiaCheckBox.setChecked(false);
    }

    private void logBloodPressureDataDb() {
        Log.d(LOG_TAG, "--- Rows in Blood Pressure Data table: ---");
        userRepository.getAllBloodPressureData().observe(getViewLifecycleOwner(), allBloodPressureData -> {
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
