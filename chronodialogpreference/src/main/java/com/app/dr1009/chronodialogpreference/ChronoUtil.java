package com.app.dr1009.chronodialogpreference;

import android.content.Context;
import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.TimeZone;

class ChronoUtil {
    final static SimpleDateFormat TIME_FORMATTER;
    final static SimpleDateFormat DATE_FORMATTER;
    final static TimeZone UTC_TIMEZONE = TimeZone.getTimeZone("UTC");

    static {
        TIME_FORMATTER = new SimpleDateFormat("HH:mm", Locale.ROOT);
        TIME_FORMATTER.setTimeZone(UTC_TIMEZONE);

        DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);
        DATE_FORMATTER.setTimeZone(UTC_TIMEZONE);
    }

    static Calendar dateToCalendar(Date date) {
        final Calendar calendar = getUtcCalendar();
        calendar.setTime(date);
        return calendar;
    }

    static Calendar getUtcCalendar() {
        return Calendar.getInstance(UTC_TIMEZONE);
    }

    static String formatDateTimeUtc(Context context, long millis, int flags) {
        Formatter formatter = new Formatter(new StringBuilder(50), Locale.getDefault());
        formatter = DateUtils.formatDateRange(context, formatter, millis, millis, flags, UTC_TIMEZONE.getID());
        return formatter.toString();
    }
}
