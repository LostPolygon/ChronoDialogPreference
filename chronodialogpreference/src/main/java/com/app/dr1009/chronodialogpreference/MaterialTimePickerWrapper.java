package com.app.dr1009.chronodialogpreference;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

import androidx.annotation.NonNull;

import static com.app.dr1009.chronodialogpreference.ChronoPreferenceFragment.*;

public final class MaterialTimePickerWrapper {
    @NonNull
    public static MaterialTimePicker createTimeDialogFragment(TimeDialogPreference preference) {
        final MaterialTimePicker.Builder builder =
            new MaterialTimePicker.Builder()
                .setHour(preference.getCalendar().get(Calendar.HOUR_OF_DAY))
                .setMinute(preference.getCalendar().get(Calendar.MINUTE))
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                .setTitleText(preference.getDialogTitle())
                .setPositiveButtonText(preference.getPositiveButtonText())
                .setNegativeButtonText(preference.getNegativeButtonText());

        if (preference.isForce12HourPicker()) {
            builder.setTimeFormat(TimeFormat.CLOCK_12H);
        } else if (preference.isForce24HourPicker()) {
            builder.setTimeFormat(TimeFormat.CLOCK_24H);
        }

        final MaterialTimePicker picker = builder.build();

        final Bundle arguments = picker.getArguments();
        assert arguments != null;
        arguments.putString(ChronoPreferenceFragment.ARG_PREFERENCE_KEY, preference.getKey());

        attachTimeDialogFragmentListeners(picker, preference);

        return picker;
    }

    public static void attachTimeDialogFragmentListeners(MaterialTimePicker timePicker, TimeDialogPreference preference) {
        Log.d(LOG_TAG, "attachTimeDialogFragmentListeners");

        timePicker.clearOnPositiveButtonClickListeners();
        timePicker.addOnPositiveButtonClickListener(dummy -> {
            final Calendar calendar = ChronoUtil.getUtcCalendar();
            calendar.setTimeInMillis(0);
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            calendar.set(Calendar.MINUTE, timePicker.getMinute());

            String value = ChronoUtil.TIME_FORMATTER.format(calendar.getTimeInMillis());
            if (preference.callChangeListener(value)) {
                preference.setSerializedValue(value);
            }
        });
    }
}
