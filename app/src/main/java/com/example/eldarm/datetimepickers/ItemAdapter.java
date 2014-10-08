package com.example.eldarm.datetimepickers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * An adapter to provide data to the list.
 */
public class ItemAdapter extends BaseAdapter {
    private static final String LOG_TAG = ItemAdapter.class.getCanonicalName();
    private final Context context;
    private final LayoutInflater layoutInflater;

    public ItemAdapter(Context c) {
        context = c;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 50;
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

        /*
        TextView textView;
        if (view == null) { // Create a new view if no recycled view is available
            textView = (TextView) layoutInflater.inflate(
                    R.layout.list_item, parent, false /* attachToRoot * /);
        } else {
            textView = (TextView) view;
        }
        textView.setText(R.string.ItemSampleText);
        //imageView.setImageResource(R.drawable.meme);
        //imageView.setContentDescription(context.getString(R.string.meme_description));
        return textView;
        */
        LinearLayout itemView;
        if (view == null) { // Create a new view if no recycled view is available
            itemView = (LinearLayout) layoutInflater.inflate(
                    R.layout.list_item, parent, false /* attachToRoot */);
        } else {
            itemView = (LinearLayout) view;
        }
        TextView textView = (TextView)itemView.findViewById(R.id.textItemView);
        textView.setText(String.format(parent.getResources().getString(R.string.ItemSampleText), i));

        return itemView;
    }
}
