package com.yoji.healthmonitoringsystem;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class BloodPressureAndLifeDataFragmentAdapter extends FragmentPagerAdapter {

    private static final int[] TAB_TITLES = new int[]{R.string.recording_blood_pressure_data, R.string.recording_lifedata};
    private final Context context;

    BloodPressureAndLifeDataFragmentAdapter(Context context, @NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.context = context;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment returningFragment;
        switch (position) {
            case 0:
                returningFragment = RecordingBloodPressureDataFragment.newInstance();
                break;
            case 1:
                returningFragment = RecordingLifedataFragment.newInstance();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
        return returningFragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
