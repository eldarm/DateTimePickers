package com.example.eldarm.datetimepickers;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;


public class DateTimePickerActivity extends ActionBarActivity {
    private DatePicker datePicker;
    private TimePicker timePicker;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_picker);

        textView = (TextView)findViewById(R.id.textView);
        datePicker = (DatePicker)findViewById(R.id.datePicker);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        // datePicker.getCalendarView().getDate();

        final Button button = (Button) findViewById(R.id.DoStuffButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                textView.setText(
                  String.format("%04d-%02d-%02dT%02d:%02d",
                    //DateFormat.getDateTimeInstance().format(datePicker.getCalendarView().getDate()),
                    // Supported only from API 12, we need at least 9.
                    datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                    timePicker.getCurrentHour(), timePicker.getCurrentMinute()
                  )
                );
            }
        });

        //DatePicker myDatePicker = (DatePicker) findViewById(R.id.mydatepicker);
        //String selectedDate = DateFormat.getDateInstance().format(myDatePicker.getCalendarView().getDate());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.date_time_picker, menu);
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
        return super.onOptionsItemSelected(item);
    }
}
