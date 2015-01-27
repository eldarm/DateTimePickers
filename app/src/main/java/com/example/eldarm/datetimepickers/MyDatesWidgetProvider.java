package com.example.eldarm.datetimepickers;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

/**
 * Created by eldarm on 1/27/15.
 */
public class MyDatesWidgetProvider extends AppWidgetProvider {

    public static Intent createUpdateIntent(Context context) {
        Intent intent = new Intent(context, MyDatesWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int ids[] = AppWidgetManager.getInstance(context)
                .getAppWidgetIds(new ComponentName(context, MyDatesWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        return intent;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        ComponentName thisWidget = new ComponentName(context,
                MyDatesWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        // Iterate over all widgets of this type.
        for (int widgetId : allWidgetIds) {
            // Get the RemoteViews
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.my_dates_widget);

            // Register an "onClickListener" to start the SelectMemeActivity.
            Intent intent = new Intent(context, ListDatesActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            remoteViews.setOnClickPendingIntent(R.id.listView, pendingIntent);  // ???????????????????

            // Get the data.
            ItemAdapter adapter = new ItemAdapter(context);
            adapter.loadDates();
            // Set the text.
            int count = Math.min(adapter.getCount(), 4);
            remoteViews.setTextViewText(R.id.textView1, count > 0 ? adapter.getItem(0).toString() : "");
            remoteViews.setTextViewText(R.id.textView2, count > 1 ? adapter.getItem(1).toString() : "");
            remoteViews.setTextViewText(R.id.textView3, count > 2 ? adapter.getItem(2).toString() : "");
            remoteViews.setTextViewText(R.id.textView4, count > 3 ? adapter.getItem(3).toString() : "");

            // Finally update the widgets. This performs the changes done above.
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

}
