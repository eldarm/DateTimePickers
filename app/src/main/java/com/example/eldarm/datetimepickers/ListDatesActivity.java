package com.example.eldarm.datetimepickers;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * List with the selectexd dates - the main screen of the application.
 */
public class ListDatesActivity extends ActionBarActivity {
    private static final String LOG_TAG = ListDatesActivity.class.getCanonicalName();
    private static final String DATES_URL =
        "https://sites.google.com/site/gayauniverse/kniga-2-lar/cernoviki/kniga-3-dezurstvo-po-mirozdaniu-fragment/dates.txt";
    private Timer timer;
    private final Handler timeHandler = new Handler();
    private ItemAdapter itemAdapter;

    class DownloadDatesAsyncTask extends AsyncTask<String, Void, Vector<SpecialDate>> {
        private String imageUrl;

        @Override
        protected Vector<SpecialDate> doInBackground(String... params) {
            imageUrl = params[0];
            try {
                URL datesUrl = new URL(imageUrl);
                InputStream is = (InputStream) datesUrl.getContent();
                return SpecialDate.readDatesList(is);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error reading the dates", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Vector<SpecialDate> result) {
            if (result != null) {
                itemAdapter.setDates(result);
                itemAdapter.saveDates();  // Because setDates() does not saves them.
            }
        }
    }

    //Fragment that displays the meme.
    public static class ListDatesFragment extends Fragment {
        @Override
        public View onCreateView(
                LayoutInflater inflater,
                ViewGroup container,
                Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_list_dates, container, false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dates);

        ListView listView = (ListView) findViewById(R.id.listView);
        if (listView == null) {
            Log.d(LOG_TAG, "listView is NULL");
        }
        itemAdapter = new ItemAdapter(this);
        listView.setAdapter(itemAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemAdapter.loadDates();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // itemAdapter.notifyDataSetChanged();
                timeHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        itemAdapter.notifyDataSetChanged();
                    }
                });
            }
        }, 500, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_dates, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_load_dates) {
            Toast.makeText(this, "Adding the dates from Internet...", Toast.LENGTH_LONG).show();
            new DownloadDatesAsyncTask().execute(DATES_URL);
            return true;
        }
        if (id == R.id.action_add_date) {
            Intent intent = new Intent(ListDatesActivity.this, DateTimePickerActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
