package com.yoji.healthmonitoringsystem.recording_data_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.yoji.healthmonitoringsystem.R;

public class BloodPressureAndLifeDataActivity extends AppCompatActivity {
    private static final int[] TAB_TITLES = new int[]{R.string.recording_blood_pressure_data, R.string.recording_lifedata};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure_and_life_data);

        BloodPressureAndLifeDataFragmentAdapter fragmentAdapter = new BloodPressureAndLifeDataFragmentAdapter(this);
        ViewPager2 viewPager = findViewById(R.id.swipeViewPagerId);
        viewPager.setAdapter(fragmentAdapter);
        TabLayout tabLayout = findViewById(R.id.tabLayoutId);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(TAB_TITLES[position])
        ).attach();
    }
}
