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
import java.util.Date;
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

        public SpecialDate(String label, String date) {
            this.label = label;
            try {
                this.date = format.parse(date);
            } catch (ParseException e) {
                // Don't trash the app but better have a look in the trace.
                e.printStackTrace();
            }
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return format.format(date);
        }

        private long secondsSince() {
            return ((new Date()).getTime() - date.getTime()) / 1000;
        }

        public String timeSinceFormat(long seconds) {
            long minutes = seconds / secInMin;
            long hours = minutes / minInHour;
            long days = hours / hoursInDay;
            long years = days / daysInYear;

            seconds -= minutes * secInMin;
            minutes -= hours * minInHour;
            hours -= days * hoursInDay;
            days -= years * daysInYear;
            // Yes, the result for large intervals will give wrong number of years.
            // We'll improve it later.
            String yearsString = years == 0 ? "" : String.format("%d years ", years);
            //return String.format("%s: %s%d days %d hours %d minutes %d seconds",
            //        label, yearsString, days, hours, minutes, seconds);
            return String.format("%s%d days %d hours %d minutes",
                                 yearsString, days, hours, minutes);
        }

        public String timeSince() {
            return timeSinceFormat(secondsSince());
        }

        public String timeTillAniversary() {
            final long secondsInYear = secInMin * minInHour * hoursInDay * daysInYear;
            final long seconds = secondsSince();
            final long years = seconds / secondsInYear;
            return timeSinceFormat(secondsInYear - seconds + years * secondsInYear);
        }

        private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        private Date date;
        private String label;
    }

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
