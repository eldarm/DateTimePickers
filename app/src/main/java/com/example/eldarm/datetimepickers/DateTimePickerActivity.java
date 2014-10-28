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


public class DateTimePickerActivity extends ActionBarActivity {
    private DatePicker datePicker;
    private TimePicker timePicker;
    private TextView textLabel;
    private TextView textDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_picker);
    }

    @Override
    protected void onResume() {
        super.onResume();
        textLabel = (TextView)findViewById(R.id.textLabel);
        datePicker = (DatePicker)findViewById(R.id.datePicker);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        // datePicker.getCalendarView().getDate();


        final Button button = (Button) findViewById(R.id.DoStuffButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String dateString =
                        String.format("%04d/%02d/%02d %02d:%02d:00",
                                //DateFormat.getDateTimeInstance().format(datePicker.getCalendarView().getDate()),
                                // Supported only from API 12, we need at least 9.
                                datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(), timePicker.getCurrentMinute()
                        );
                SpecialDate date = new SpecialDate(textLabel.getText().toString(), dateString);
                ItemAdapter adapter = new ItemAdapter(DateTimePickerActivity.this);
                adapter.addDate(date);
                finish();
            }
        });
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
