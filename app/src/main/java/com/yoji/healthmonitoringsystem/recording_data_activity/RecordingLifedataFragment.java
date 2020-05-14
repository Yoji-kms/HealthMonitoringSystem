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
import android.widget.EditText;
import android.widget.Toast;

import com.yoji.healthmonitoringsystem.R;
import com.yoji.healthmonitoringsystem.RoomDB.Lifedata;
import com.yoji.healthmonitoringsystem.RoomDB.UserRepository;

import java.util.Calendar;
import java.util.Objects;

public class RecordingLifedataFragment extends Fragment {

    private View view;

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
        RecordingLifedataFragment.userId = userId;
    }

    public RecordingLifedataFragment() {
    }

    static RecordingLifedataFragment newInstance() {
        return new RecordingLifedataFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recording_lifedata, container, false);

        initViews();
        initDb();
        saveButtonAction();

        return view;
    }

    private void initViews() {
        weightEditText = view.findViewById(R.id.weightEditTextId);
        quantityOfStepsEditText = view.findViewById(R.id.quantityOfStepsEditTextId);

        weightEditText.addTextChangedListener(textWatcher);
        quantityOfStepsEditText.addTextChangedListener(textWatcher);
    }

    private void initDb() {
        userRepository = new UserRepository(Objects.requireNonNull(getActivity()).getApplication());
    }

    private void saveButtonAction() {
        saveButton = view.findViewById(R.id.saveLifedataButtonId);

        saveButton.setOnClickListener(v -> {
            Log.i(LOG_TAG, "Нажата кнопка \"Сохранить\" на экране записи жизненных показателей");

            saveDataToDb();
            clearViews();
            logLifedataDb();
        });
    }

    private void saveDataToDb() {
        Lifedata lifedata = new Lifedata();
        int weight = Integer.parseInt(weightEditText.getText().toString());
        int quantityOfSteps = Integer.parseInt(quantityOfStepsEditText.getText().toString());
        String currentDate = String.valueOf(Calendar.getInstance().getTime());

        lifedata.setWeight(weight);
        lifedata.setQtyOfSteps(quantityOfSteps);
        lifedata.setDate(currentDate);
        lifedata.setUserId(userId);

        userRepository.insertLifedata(lifedata);

        Toast.makeText(getContext(), R.string.data_saved, Toast.LENGTH_SHORT).show();
    }

    private void clearViews() {
        weightEditText.setText("");
        quantityOfStepsEditText.setText("");
    }

    private void logLifedataDb() {
        Log.d(LOG_TAG, "--- Rows in Blood Lifedata table: ---");
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
