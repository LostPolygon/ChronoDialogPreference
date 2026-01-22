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

public abstract class ChronoDialogPreference extends DialogPreference {
    private final String mCustomSummaryFormat;
    private final SimpleDateFormat mCustomSummarySimpleDateFormat;
    private Calendar mCalendar = ChronoUtil.getUtcCalendar();

    public ChronoDialogPreference(
        @NonNull Context context,
        @Nullable AttributeSet attrs,
        int defStyleAttr,
        int defStyleRes
    ) {
        super(context, attrs, defStyleAttr, defStyleRes);

        final TypedArray styledAttributes = getStyledAttributes(context, attrs);
        mCustomSummaryFormat = getCustomSummaryFormat(styledAttributes);
        if (mCustomSummaryFormat != null && !mCustomSummaryFormat.isEmpty()) {
            mCustomSummarySimpleDateFormat = new SimpleDateFormat(mCustomSummaryFormat, Locale.ROOT);
            mCustomSummarySimpleDateFormat.setTimeZone(ChronoUtil.UTC_TIMEZONE);
        } else {
            mCustomSummarySimpleDateFormat = null;
        }
    }

    public ChronoDialogPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ChronoDialogPreference(Context context, AttributeSet attrs) {
        this(context, attrs, androidx.preference.R.attr.dialogPreferenceStyle);
    }

    public ChronoDialogPreference(Context context) {
        this(context, null);
    }

    public Calendar getCalendar() {
        return mCalendar;
    }

    public String getCustomSummaryFormat() {
        return mCustomSummaryFormat;
    }


    @Override
    public CharSequence getSummary() {
        if (mCustomSummarySimpleDateFormat != null)
            return mCustomSummarySimpleDateFormat.format(getCalendar().getTimeInMillis());

        return ChronoUtil.formatDateTimeUtc(
            getContext(),
            getCalendar().getTimeInMillis(),
            getDateUtilsFormat()
        );
    }

    public String getSerializedValue() {
        return getCalendarFormatter().format(getCalendar().getTimeInMillis());
    }

    public void setSerializedValue(@NonNull final String serializedTime) {
        try {
            mCalendar = ChronoUtil.dateToCalendar(getCalendarFormatter().parse(serializedTime));
        } catch (ParseException e) {
            throw new AssertionError(e);
        }

        final boolean wasBlocking = shouldDisableDependents();

        persistString(serializedTime);

        final boolean isBlocking = shouldDisableDependents();
        if (isBlocking != wasBlocking) {
            notifyDependencyChange(isBlocking);
        }

        setSummary(getSummary());
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getString(index);
    }

    @Override
    protected void onSetInitialValue(Object defaultValue) {
        if (defaultValue == null) {
            setSerializedValue(getPersistedString(getDefaultSerializedValue()));
        } else {
            setSerializedValue((String) defaultValue);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        if (isPersistent()) {
            // No need to save instance state since it's persistent
            return superState;
        }

        final SavedState myState = new SavedState(superState);
        myState.text = getSerializedValue();
        return myState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state == null || !state.getClass().equals(SavedState.class)) {
            // Didn't save state for us in onSaveInstanceState
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState myState = (SavedState) state;
        super.onRestoreInstanceState(myState.getSuperState());
        setSerializedValue(myState.text);
    }

    protected abstract TypedArray getStyledAttributes(Context context, AttributeSet attrs);
    protected abstract String getCustomSummaryFormat(TypedArray styledAttributes);
    protected abstract int getDateUtilsFormat();
    protected abstract SimpleDateFormat getCalendarFormatter();
    protected abstract String getDefaultSerializedValue();
}
