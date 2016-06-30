package com.mx3studios.to_do;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public int year;
    public int month;
    public int day;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

            private TextView dateView;
    public DatePickerFragment() {
    }

    public void setDateTextView(TextView dateView) {
        this.dateView = dateView;
    }

    public void onDateSet(DatePicker view, int y, int m, int d) {
        // Do something with the date chosen by the user
        this.year = y;
        this.month = m;
        this.day = d;
        setDateTextView();
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    private void setDateTextView() {
        if(year == 0 && month == 0 && day == 0) {
            dateView.setText("Due Date: -");
        } else {
            dateView.setText("Due Date: " + month + "/" + day + "/" + year);
        }
    }
}
