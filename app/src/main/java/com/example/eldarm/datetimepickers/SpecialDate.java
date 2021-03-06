package com.example.eldarm.datetimepickers;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

/**
 * Created by eldarm on 10/24/14.
 */
class SpecialDate {
    private static final String LOG_TAG = SpecialDate.class.getCanonicalName();
    private final long secInMin = 60;
    private final long minInHour = 60;
    private final long hoursInDay = 24;
    private final long daysInYear = 365;
    private final long secInYear = secInMin * minInHour * hoursInDay * daysInYear;
    private static final boolean showUntilAnniversary = false;

    public SpecialDate(String label, String dateString) {
        this.label = label;
        Date date;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            // Don't trash the app but better have a look in the trace.
            e.printStackTrace();
            date = new Date();
        }
        now = new GregorianCalendar();
        cal = new GregorianCalendar();
        cal.setTime(date);
        anniversary = new GregorianCalendar(now.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                cal.get(Calendar.SECOND));
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return getLabel() + ": "
               + (showUntilAnniversary ? timeTillAnniversary(true) : timeSince(true));
    }

    public String getDate() {
        return format.format(cal.getTime());
    }

    public String timeSince() {
        return timeSince(false);
    }

    public String timeSince(boolean compact) {
        long years = now.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
        long shift = anniversaryShiftSec();
        // System.out.println("Shift: " + shift);
        if (shift > 0) {
            years--;
            shift = secInYear - shift;
        } else {
            shift = -shift;
        }
        String yearsString = years == 0 ? "" :
                             String.format("%d", years) + (compact ? "y " : " years ");
        return yearsString + formatShift(shift, compact);
    }

    public String timeTillAnniversary() {
        return timeTillAnniversary(false);
    }

    public String timeTillAnniversary(boolean compact) {
        long shift = anniversaryShiftSec();
        // System.out.println("Shift: " + shift);
        if (shift < 0) {
            shift = secInYear + shift; // Actually, minus, since it's < 0.
        }
        return formatShift(shift, compact);
    }

    public static Vector<SpecialDate> readDatesList(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        Vector<SpecialDate> dates = new Vector<SpecialDate>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length != 2) {
                    Log.e(LOG_TAG, "Incorrect string format: '" + line + "'");
                    continue;  // Wrong format.
                }
                dates.add(new SpecialDate(parts[0], parts[1]));
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error reading the dates", e);
        }
        return dates;
    }

    private String formatShift(long shift, boolean compact) {
        long seconds = shift % secInMin;
        long minutes = shift / secInMin % minInHour;
        long hours = shift / (secInMin * minInHour) % hoursInDay;
        long days = shift / (secInMin * minInHour * hoursInDay);
        return compact ? String.format("%dd %dh", days, hours) :
            String.format("%d days %d hours %d minutes %d seconds", days, hours, minutes, seconds);
    }

    private long anniversaryShiftSec() {
        now = new GregorianCalendar();
        return (anniversary.getTime().getTime() - now.getTime().getTime()) / 1000;
    }

    protected SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    protected GregorianCalendar now;
    private GregorianCalendar cal;
    private GregorianCalendar anniversary;
    private String label;
} // class SpecialDate
