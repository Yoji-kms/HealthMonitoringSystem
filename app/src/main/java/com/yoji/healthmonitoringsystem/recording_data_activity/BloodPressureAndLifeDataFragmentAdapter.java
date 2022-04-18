package com.yoji.healthmonitoringsystem.recording_data_activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class BloodPressureAndLifeDataFragmentAdapter extends FragmentStateAdapter {

    public BloodPressureAndLifeDataFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
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

    @Override
    public int getItemCount() {
        return 2;
    }
}
