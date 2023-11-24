package com.app.dr1009.chronodialogpreference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.text.format.DateUtils;
import android.util.AttributeSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.DialogPreference;

public class TimeDialogPreference extends ChronoDialogPreference {
    private static final String DEFAULT_TIME = "00:00";
    private boolean mIsForce12HourModePicker;
    private boolean mIsForce24HourModePicker;

    public TimeDialogPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TimeDialogPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TimeDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeDialogPreference(Context context) {
        super(context);
    }

    public boolean isForce12HourPicker() {
        return mIsForce12HourModePicker;
    }

    public boolean isForce24HourPicker() {
        return mIsForce24HourModePicker;
    }

    @Override
    protected TypedArray getStyledAttributes(Context context, AttributeSet attrs) {
        TypedArray styledAttributes = context.obtainStyledAttributes(
            attrs, R.styleable.Dialog_Preference_TimePicker, 0, 0
        );

        mIsForce12HourModePicker = styledAttributes.getBoolean(R.styleable.Dialog_Preference_TimePicker_force12HourModePicker, false);
        mIsForce24HourModePicker = styledAttributes.getBoolean(R.styleable.Dialog_Preference_TimePicker_force24HourModePicker, false);

        return styledAttributes;
    }

    @Override
    protected String getCustomSummaryFormat(TypedArray styledAttributes) {
        return styledAttributes.getString(R.styleable.Dialog_Preference_TimePicker_customSummaryFormat);
    }

    @Override
    protected int getDateUtilsFormat() {
        return DateUtils.FORMAT_SHOW_TIME;
    }

    @Override
    protected SimpleDateFormat getCalendarFormatter() {
        return ChronoUtil.TIME_FORMATTER;
    }

    @Override
    protected String getDefaultSerializedValue() {
        return DEFAULT_TIME;
    }
}
