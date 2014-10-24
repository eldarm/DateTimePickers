package com.example.eldarm.datetimepickers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;

/**
 * An adapter to provide data to the list.
 */
public class ItemAdapter extends BaseAdapter {
    private static final String LOG_TAG = ItemAdapter.class.getCanonicalName();
    private final Context context;
    private final LayoutInflater layoutInflater;

    private Vector<SpecialDate> data;

    public ItemAdapter(Context c) {
        data = new Vector<SpecialDate>();
        // Fixed list for now, we will learn to load and save it later.
        // Feel free to put your own dates here.
        data.add(new SpecialDate("M", "2000/04/28 08:00:00"));

        context = c;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setDates(Vector<SpecialDate> dates) {
        data = dates;
        notifyDataSetChanged();
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
