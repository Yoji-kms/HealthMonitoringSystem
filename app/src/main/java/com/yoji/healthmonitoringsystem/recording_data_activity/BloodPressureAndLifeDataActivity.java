package com.yoji.healthmonitoringsystem.recording_data_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.yoji.healthmonitoringsystem.R;

public class BloodPressureAndLifeDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure_and_life_data);

        BloodPressureAndLifeDataFragmentAdapter fragmentAdapter = new BloodPressureAndLifeDataFragmentAdapter(this, getSupportFragmentManager(), 0);
        ViewPager viewPager = findViewById(R.id.swipeViewPagerId);
        viewPager.setAdapter(fragmentAdapter);
        TabLayout tabLayout = findViewById(R.id.tabLayoutId);
        tabLayout.setupWithViewPager(viewPager);
    }
}
