package com.example.eldarm.datetimepickers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

/**
 * An adapter to provide data to the list.
 */
public class ItemAdapter extends BaseAdapter {
    private static final String LOG_TAG = ItemAdapter.class.getCanonicalName();
    private final Context context;
    private final LayoutInflater layoutInflater;

    private Vector<SpecialDate> data;
    private final String dataFileName = "data_my_dates.txt";

    public ItemAdapter(Context c) {
        data = new Vector<SpecialDate>();  // Temp, until loaded.
        context = c;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setDates(Vector<SpecialDate> dates) {
        if (dates != null) {
            data = dates;
            notifyDataSetChanged();
        }
    }

    public void addDate(SpecialDate date) {
        loadDates();
        data.add(date);
        saveDates();
    }

    public void saveDates() {
        StringBuffer result = new StringBuffer();
        for (SpecialDate date : data) {
            result.append(date.getLabel());
            result.append(":");
            result.append(date.getDate());
            result.append("\n");
        }
        try {
            context.deleteFile(dataFileName);
            FileOutputStream outputStream =
                    context.openFileOutput(dataFileName, Context.MODE_APPEND);
            outputStream.write(result.toString().getBytes());
            outputStream.close();
            context.sendBroadcast(MyDatesWidgetProvider.createUpdateIntent(context));
        } catch (Exception e) {
            Log.d(LOG_TAG, String.format("Error writing the file: %s", dataFileName));
            e.printStackTrace();
        }
    }

    public void loadDates() {
        try {
            FileInputStream is = context.openFileInput(dataFileName);
            setDates(SpecialDate.readDatesList(is));
            is.close();
        } catch (Exception e) {
            Log.d(LOG_TAG, String.format("Error reading the file: %s", dataFileName));
            e.printStackTrace();
        }
        return;  // dummy
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        //Log.d(LOG_TAG, String.format("getItem() for item %d", i));
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        //Log.d(LOG_TAG, String.format("getItemId() for item %d", i));
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        // Log.d(LOG_TAG, String.format("getView() for item %d", i));
        LinearLayout itemView;
        if (view == null) { // Create a new view if no recycled view is available
            itemView = (LinearLayout) layoutInflater.inflate(
                    R.layout.list_item, parent, false /* attachToRoot */);
        } else {
            itemView = (LinearLayout) view;
        }
        TextView textView = (TextView)itemView.findViewById(R.id.textItemLabelView);
        textView.setText(data.elementAt(i).getLabel() + ": " + data.elementAt(i).getDate());
        textView = (TextView)itemView.findViewById(R.id.textItemValueView);
        // textView.setText(String.format(parent.getResources().getString(R.string.ItemSampleText), i));
        textView.setText(data.elementAt(i).timeSince());

        return itemView;
    }
}
