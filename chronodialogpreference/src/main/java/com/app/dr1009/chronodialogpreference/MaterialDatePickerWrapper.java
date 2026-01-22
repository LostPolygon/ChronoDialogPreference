package com.app.dr1009.chronodialogpreference;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.CompositeDateValidator;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;

import static com.app.dr1009.chronodialogpreference.ChronoPreferenceFragment.*;

public final class MaterialDatePickerWrapper {
    @NonNull
    public static MaterialDatePicker<Long> createDateDialogFragment(DateDialogPreference preference) {
        ArrayList<CalendarConstraints.DateValidator> dateValidators = new ArrayList<>();

        if (preference.getMinDate() != null) {
            Date date;
            try {
                date = ChronoUtil.DATE_FORMATTER.parse(preference.getMinDate());
                assert date != null;
                dateValidators.add(DateValidatorPointForward.from(date.getTime()));
            } catch (ParseException e) {
                throw new IllegalArgumentException("minDate is not in the correct format", e);
            }
        }

        if (preference.getMaxDate() != null) {
            Date date;
            try {
                date = ChronoUtil.DATE_FORMATTER.parse(preference.getMaxDate());
                assert date != null;
                dateValidators.add(DateValidatorPointBackward.before(date.getTime()));
            } catch (ParseException e) {
                throw new IllegalArgumentException("maxDate is not in the correct format", e);
            }
        }

        final CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setValidator(CompositeDateValidator.allOf(dateValidators));

        final MaterialDatePicker.Builder<Long> builder =
            MaterialDatePicker.Builder
                .datePicker()
                .setSelection(preference.getCalendar().getTimeInMillis())
                .setTitleText(preference.getDialogTitle())
                .setPositiveButtonText(preference.getPositiveButtonText())
                .setNegativeButtonText(preference.getNegativeButtonText())
                .setCalendarConstraints(constraintsBuilder.build());

        final MaterialDatePicker<Long> picker = builder.build();

        final Bundle arguments = picker.getArguments();
        assert arguments != null;
        arguments.putString(ARG_PREFERENCE_KEY, preference.getKey());

        attachDateDialogFragmentListeners(picker, preference);

        return picker;
    }

    public static void attachDateDialogFragmentListeners(MaterialDatePicker<Long> datePicker, DateDialogPreference preference) {
        Log.d(LOG_TAG, "attachDateDialogFragmentListeners");

        datePicker.clearOnPositiveButtonClickListeners();
        datePicker.addOnPositiveButtonClickListener(selection -> {
            String value = ChronoUtil.DATE_FORMATTER.format(selection);
            if (preference.callChangeListener(value)) {
                preference.setSerializedValue(value);
            }
        });
    }
}
