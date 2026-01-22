package com.app.dr1009.chronodialogpreferencedemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.dr1009.chronodialogpreference.ChronoPreferenceFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SettingsFragment extends ChronoPreferenceFragment {
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.pref_chrono, rootKey);
    }
}
