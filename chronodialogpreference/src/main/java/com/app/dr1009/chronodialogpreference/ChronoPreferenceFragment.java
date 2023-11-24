package com.app.dr1009.chronodialogpreference;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public abstract class ChronoPreferenceFragment extends PreferenceFragmentCompat {

    public static final String DIALOG_FRAGMENT_TAG = "ChronoPreferenceFragment.DIALOG";
    static final String LOG_TAG = "ChronoPreference";
    static final String ARG_PREFERENCE_KEY = "key";

    @Override
    public void onResume() {
        super.onResume();

        final Fragment fragmentByTag = getParentFragmentManager().findFragmentByTag(DIALOG_FRAGMENT_TAG);
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
        return findPreference(preferenceKey);
    }

    @Override
    public void onDisplayPreferenceDialog(@NonNull Preference preference) {
        DialogFragment dialogFragment = null;
        if (preference instanceof TimeDialogPreference) {
            dialogFragment = MaterialTimePickerWrapper.createTimeDialogFragment((TimeDialogPreference) preference);
        } else if (preference instanceof DateDialogPreference) {
            dialogFragment = MaterialDatePickerWrapper.createDateDialogFragment((DateDialogPreference) preference);
        }

        if (dialogFragment != null) {
            //noinspection deprecation
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(getParentFragmentManager(), DIALOG_FRAGMENT_TAG);
        } else {
            super.onDisplayPreferenceDialog(preference);
        }
    }
}
