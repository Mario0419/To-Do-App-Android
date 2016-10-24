package com.mx3studios.to_do;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class EditItemActivity extends AppCompatActivity {

    private TextInputEditText titleEditText;
    private TextInputEditText descEditText;
    private TodoItem todoItem;
    private ImageButton saveButton;
    private ImageButton deleteButton;

    private RadioButton lowButtonPriority;
    private RadioButton mediumButtonPriority;
    private RadioButton highButtonPriority;

    private RadioButton todoButtonStatus;
    private RadioButton holdButtonStatus;
    private RadioButton completedButtonStatus;

    private TodoItemDao dao;

    private int year= 0;
    private int month = 0;
    private int day = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        dao = TodoItemSQLiteDao.getInstance(getApplication());
        setSupportActionBar(toolbar);

        titleEditText = (TextInputEditText)findViewById(R.id.edittext_task_title);
        descEditText = (TextInputEditText)findViewById(R.id.edittext_task_desc);

        todoButtonStatus = (RadioButton)findViewById(R.id.radio_button_todo_status);
        holdButtonStatus = (RadioButton)findViewById(R.id.radio_button_hold_status);
        completedButtonStatus = (RadioButton)findViewById(R.id.radio_button_complete_status);

        TextView tv = (TextView)findViewById(R.id.textview_datechooser);
        tv.setText("Due Date: -");

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

        lowButtonPriority = (RadioButton)findViewById(R.id.radio_button_low);
        mediumButtonPriority = (RadioButton)findViewById(R.id.radio_button_med);
        highButtonPriority =  (RadioButton)findViewById(R.id.radio_button_high);

        Bundle bundle = getIntent().getExtras();

        if(bundle.get("TodoItem") != null) {
            todoItem = (TodoItem) bundle.get("TodoItem");
        } else {
            todoItem = new TodoItem();
        }

        loadViewFromModel();
    }

    private void setColor() {

    }

    private void saveTodoItem() {
        loadModelFromView();
        if(!validateFields()) {
            return;
        }
        todoItem = dao.save(todoItem);
        finish();
    }

    private boolean validateFields() {
        if(todoItem.getTitle() == null || todoItem.getTitle().trim().equals("")) {
            Toast.makeText(EditItemActivity.this, "Please fill in a task title.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void loadModelFromView() {
        todoItem.setTitle(titleEditText.getText().toString());
        todoItem.setDescription(descEditText.getText().toString());
        todoItem.setStatus(getSelectedStatusRadioButton());
        todoItem.setCompletionDate(getDateFromDateView());
        todoItem.setLevel(getSelectedPriorityRadioButton());
    }

    private String getDateFromDateView() {
        TextView dateView = (TextView)findViewById(R.id.textview_datechooser);

        String str  = dateView.getText().toString();
        String[] arr = str.split("/");
        if(arr.length != 1) {
            day = Integer.valueOf(arr[1]);
            year = Integer.valueOf(arr[2]);
            month = Integer.valueOf(arr[0].split(" ")[2]);
        }
        if (month == 0 && day == 0 && year == 0) {
            return "";
        }
        return month + "/" + day + "/" + year;
    }


    private String getSelectedPriorityRadioButton() {
        RadioGroup rg = (RadioGroup)findViewById(R.id.radio_group_priority);
        switch(rg.getCheckedRadioButtonId()) {
            case R.id.radio_button_low:
                return "LOW";
            case R.id.radio_button_med:
                return "MEDIUM";
            case R.id.radio_button_high:
                return "HIGH";
            default:
                return "LOW";
        }
    }

    private String getSelectedStatusRadioButton() {
        RadioGroup rg = (RadioGroup)findViewById(R.id.radio_group_status);
        switch(rg.getCheckedRadioButtonId()) {
            case R.id.radio_button_todo_status:
                return  "To do";
            case R.id.radio_button_hold_status:
                return "Hold";
            case R.id.radio_button_complete_status:
                return "Done";
            default:
                return "To do";
        }
    }

    private void loadViewFromModel() {
        titleEditText.setText(todoItem.getTitle());
        descEditText.setText(todoItem.getDescription());
        switch(todoItem.getStatusIndex()) {
            case 0:
                todoButtonStatus.setChecked(true);
                break;
            case 1:
                holdButtonStatus.setChecked(true);
                break;
            case 2:
                completedButtonStatus.setChecked(true);
                break;
            default:
                todoButtonStatus.setChecked(true);
                break;
        }
        switch(todoItem.getLevelIndex()) {
            case 0:
                lowButtonPriority.setChecked(true);
                break;
            case 1:
                mediumButtonPriority.setChecked(true);
                break;
            case 2:
                highButtonPriority.setChecked(true);
                break;
            default:
                lowButtonPriority.setChecked(true);
                break;
        }
        TextView tv = (TextView)findViewById(R.id.textview_datechooser);
        if(todoItem.getCompletionDate() != null && !todoItem.getCompletionDate().equals("")) {
            tv.setText("Due Date: " + todoItem.getCompletionDate());
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton)view).isChecked();

        switch(view.getId()) {
            case R.id.radio_button_low:
                if (checked) {

                }
                break;
            case R.id.radio_button_med:
                if (checked) {

                }
                break;
            case R.id.radio_button_high:
                if (checked) {

                }
                break;
            default:
                break;
        }

    }

    private void deleteTodoItem() {
        deleteItem();
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
        FeedReaderDBHelper mDbHelper = new FeedReaderDBHelper(getApplicationContext());
        if(todoItem.getId() != null) {
            dao.delete(todoItem.getId());
        }
        finish();
    }

    public void showDatePickerDialog(View v) {

        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setDateTextView((TextView)findViewById(R.id.textview_datechooser));
        newFragment.show(getFragmentManager(), "datePicker");
    }

}

