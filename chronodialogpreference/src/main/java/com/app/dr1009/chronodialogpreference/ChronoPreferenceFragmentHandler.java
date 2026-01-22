package com.app.dr1009.chronodialogpreference;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import static com.app.dr1009.chronodialogpreference.ChronoPreferenceFragment.ARG_PREFERENCE_KEY;
import static com.app.dr1009.chronodialogpreference.ChronoPreferenceFragment.DIALOG_FRAGMENT_TAG;

public class ChronoPreferenceFragmentHandler {
    private final PreferenceFragmentCompat mPreferenceFragment;

    public ChronoPreferenceFragmentHandler(PreferenceFragmentCompat preferenceFragment) {
        mPreferenceFragment = preferenceFragment;
    }

    public void onResume() {
        final Fragment fragmentByTag = mPreferenceFragment.getParentFragmentManager().findFragmentByTag(DIALOG_FRAGMENT_TAG);
        if (fragmentByTag instanceof MaterialDatePicker) {
            //noinspection unchecked
            MaterialDatePickerWrapper.attachDateDialogFragmentListeners(
                (MaterialDatePicker<Long>) fragmentByTag,
                getPreferenceFromFragment(fragmentByTag)
            );
        } else if (fragmentByTag instanceof MaterialTimePicker) {
            MaterialTimePickerWrapper.attachTimeDialogFragmentListeners(
                (MaterialTimePicker) fragmentByTag,
                getPreferenceFromFragment(fragmentByTag)
            );
        }
    }

    private <T extends Preference> T getPreferenceFromFragment(Fragment fragment) {
        assert fragment.getArguments() != null;
        String preferenceKey = fragment.getArguments().getString(ARG_PREFERENCE_KEY);
        assert preferenceKey != null;
        return mPreferenceFragment.findPreference(preferenceKey);
    }

    public static @Nullable DialogFragment createDisplayPreferenceDialog(@NonNull Preference preference) {
        DialogFragment dialogFragment = null;
        if (preference instanceof TimeDialogPreference) {
            dialogFragment = MaterialTimePickerWrapper.createTimeDialogFragment((TimeDialogPreference) preference);
        } else if (preference instanceof DateDialogPreference) {
            dialogFragment = MaterialDatePickerWrapper.createDateDialogFragment((DateDialogPreference) preference);
        }

        return dialogFragment;
    }
}