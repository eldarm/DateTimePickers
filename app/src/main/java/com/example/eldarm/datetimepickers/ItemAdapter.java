package com.example.eldarm.datetimepickers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

/**
 * An adapter to provide data to the list.
 */
public class ItemAdapter extends BaseAdapter {
    private static final String LOG_TAG = ItemAdapter.class.getCanonicalName();
    private final Context context;
    private final LayoutInflater layoutInflater;

    private static class SpecialDate {
        private final long secInMin = 60;
        private final long minInHour = 60;
        private final long hoursInDay = 24;
        private final long daysInYear = 365;
        private final long secInYear = secInMin * minInHour * hoursInDay * daysInYear;

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
            aniversary = new GregorianCalendar(now.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    cal.get(Calendar.SECOND));
            System.out.println("The date       is " + format.format(cal.getTime()));
            System.out.println("The aniversary is " + format.format(aniversary.getTime()));
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return format.format(cal.getTime());
        }

        public String timeSince() {
            long years = now.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
            long shift = aniversaryShiftSec();
            // System.out.println("Shift: " + shift);
            if (shift > 0) {
                years--;
                shift = secInYear - shift;
            } else {
                shift = -shift;
            }
            String yearsString = years == 0 ? "" : String.format("%d years ", years);
            return yearsString + formatShift(shift);
        }

        public String timeTillAniversary() {
            long shift = aniversaryShiftSec();
            // System.out.println("Shift: " + shift);
            if (shift < 0) {
                shift = secInYear + shift; // Actually, minus, since it's < 0.
            }
            return formatShift(shift);
        }

        private String formatShift(long shift) {
            long seconds = shift % secInMin;
            long minutes = shift / secInMin % minInHour;
            long hours = shift / (secInMin * minInHour) % hoursInDay;
            long days = shift / (secInMin * minInHour * hoursInDay);
            return String.format("%d days %d hours %d minutes %d seconds", days, hours, minutes, seconds);
        }

        private long aniversaryShiftSec() {
            return (aniversary.getTime().getTime() - now.getTime().getTime()) / 1000;
        }

        protected SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        protected GregorianCalendar now;
        private GregorianCalendar cal;
        private GregorianCalendar aniversary;
        private String label;
    } // class SpecialDate

    private Vector<SpecialDate> data;

    public ItemAdapter(Context c) {
        data = new Vector<SpecialDate>();
        // Fixed list for now, we will learn to load and save it later.
        // Feel free to put your own dates here.
        data.add(new SpecialDate("G", "2010/09/27 09:00:00"));
        data.add(new SpecialDate("Z", "2012/10/01 10:00:00"));
        data.add(new SpecialDate("M", "2000/04/28 08:00:00"));

        context = c;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        Log.d(LOG_TAG, String.format("getItem() for item %d", i));
        return null;
    }

    @Override
    public long getItemId(int i) {
        Log.d(LOG_TAG, String.format("getItemId() for item %d", i));
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        Log.d(LOG_TAG, String.format("getView() for item %d", i));
        LinearLayout itemView;
        if (view == null) { // Create a new view if no recycled view is available
            itemView = (LinearLayout) layoutInflater.inflate(
                    R.layout.list_item, parent, false /* attachToRoot */);
        } else {
            itemView = (LinearLayout) view;
        }
        TextView textView = (TextView)itemView.findViewById(R.id.textItemLabelView);
        textView.setText(data.elementAt(i).getLabel() + ": " + data.elementAt(i).toString());
        textView = (TextView)itemView.findViewById(R.id.textItemValueView);
        // textView.setText(String.format(parent.getResources().getString(R.string.ItemSampleText), i));
        textView.setText(data.elementAt(i).timeSince());

        return itemView;
    }
}
