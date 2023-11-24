package com.app.dr1009.chronodialogpreference;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public abstract class ChronoPreferenceFragment extends PreferenceFragmentCompat {

    public static final String DIALOG_FRAGMENT_TAG = "ChronoPreferenceFragment.DIALOG";
    static final String LOG_TAG = "ChronoPreference";
    static final String ARG_PREFERENCE_KEY = "key";
    private ChronoPreferenceFragmentHandler mFragmentHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentHandler = new ChronoPreferenceFragmentHandler(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        mFragmentHandler.onResume();
    }

    @Override
    public void onDisplayPreferenceDialog(@NonNull Preference preference) {
        DialogFragment dialogFragment =
            ChronoPreferenceFragmentHandler.createDisplayPreferenceDialog(preference);

        if (dialogFragment != null) {
            //noinspection deprecation
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(getParentFragmentManager(), DIALOG_FRAGMENT_TAG);
        } else {
            super.onDisplayPreferenceDialog(preference);
        }
    }
}
