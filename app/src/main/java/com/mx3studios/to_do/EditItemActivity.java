package com.mx3studios.to_do;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

public class EditItemActivity extends AppCompatActivity {

    private AppCompatSpinner prioritySpinner;
    private AppCompatSpinner statusSpinner;
    private TextInputEditText titleEditText;
    private TextInputEditText descEditText;
    private TodoItem todoItem;
    private ImageButton saveButton;
    private ImageButton deleteButton;


    private int year;
    private int month;
    private int day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        titleEditText = (TextInputEditText)findViewById(R.id.edittext_task_title);
        descEditText = (TextInputEditText)findViewById(R.id.edittext_task_desc);
        prioritySpinner = (AppCompatSpinner)findViewById(R.id.spinner_priority);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.priority_array, android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(adapter);

        statusSpinner = (AppCompatSpinner)findViewById(R.id.spinner_status);
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,R.array.status_array, android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);

        saveButton = (ImageButton)findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTodoItem();
            }
        });

        deleteButton = (ImageButton)findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTodoItem();
            }
        });

        if(savedInstanceState != null) {
            todoItem = (TodoItem) savedInstanceState.get("TodoItem");
        } else {
            todoItem = new TodoItem();
        }

        if(todoItem != null) {
            setupItem();
        }
    }

    private void setupItem() {

    }

    private void saveTodoItem() {
        loadModelFromView();
        Intent output = new Intent();
        output.putExtra("saveTodo", todoItem);
        setResult(Activity.RESULT_OK, output);
        finish();
    }

    private void loadModelFromView() {
        todoItem.setTitle(titleEditText.getText().toString());
        todoItem.setDescription(descEditText.getText().toString());
        todoItem.setStatus((String) statusSpinner.getSelectedItem());
        todoItem.setCompletionDate(month + "/" + day + "/" + year);
        todoItem.setLevel((String)prioritySpinner.getSelectedItem());
    }

    private void loadViewFromModel() {
        titleEditText.setText(todoItem.getTitle());
        descEditText.setText(todoItem.getDescription());
        statusSpinner.setSelection(todoItem.getStatusIndex());
        prioritySpinner.setSelection(todoItem.getLevelIndex());
        TextView tv = (TextView)findViewById(R.id.textview_datechooser);
        tv.setText("Due Date: " + todoItem.getCompletionDate());
    }

    private void deleteTodoItem() {
        displayConfirmationDialog();
    }

    private void displayConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Warning!");
        builder.setMessage("Are you sure you want to delete this item?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteItem();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //continue
                    }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteItem() {
        loadModelFromView();
        Intent output = new Intent();
        output.putExtra("saveTodo", todoItem);
        setResult(MainActivity.RESULT_CANCELED, output);
        finish();
    }

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

        public DatePickerFragment() {

        }

        public void onDateSet(DatePicker view, int y, int m, int d) {
            // Do something with the date chosen by the user
            this.year = y;
            this.month = m;
            this.day = d;
            setDateTextView(y,m,d);
        }

    }

    public void setDateTextView(int y, int m, int d) {
        year = y;
        month = m;
        day = d;
        TextView tv = (TextView)findViewById(R.id.textview_datechooser);
        tv.setText("Due Date: " + month + "/" + day + "/" + year);
    }



    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

}
