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

public class DateDialogPreference extends ChronoDialogPreference {
    private static final String DEFAULT_DATE = "1970-01-01";
    private String mMaxDate;
    private String mMinDate;

    public DateDialogPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public DateDialogPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DateDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DateDialogPreference(Context context) {
        super(context);
    }

    public String getMinDate() {
        return mMinDate;
    }

    public String getMaxDate() {
        return mMaxDate;
    }

    @Override
    protected TypedArray getStyledAttributes(Context context, AttributeSet attrs) {
        TypedArray styledAttributes = context.obtainStyledAttributes(
            attrs, R.styleable.Dialog_Preference_DatePicker, 0, 0
        );

        mMinDate = styledAttributes.getString(R.styleable.Dialog_Preference_DatePicker_minDate);
        mMaxDate = styledAttributes.getString(R.styleable.Dialog_Preference_DatePicker_maxDate);
        return styledAttributes;
    }

    @Override
    protected String getCustomSummaryFormat(TypedArray styledAttributes) {
        return styledAttributes.getString(R.styleable.Dialog_Preference_DatePicker_customSummaryFormat);
    }

    @Override
    protected int getDateUtilsFormat() {
        return DateUtils.FORMAT_SHOW_DATE;
    }

    @Override
    protected SimpleDateFormat getCalendarFormatter() {
        return ChronoUtil.DATE_FORMATTER;
    }

    @Override
    protected String getDefaultSerializedValue() {
        return DEFAULT_DATE;
    }
}
