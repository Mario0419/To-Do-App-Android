package com.mx3studios.to_do;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private FloatingActionButton mAddButton;
    private FeedReaderDBHelper mDbHelper;

    private ArrayList<TodoItem> list = null;
    private TodoAdapter arrayAdapter = null;

    private TodoItemDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = TodoItemSQLiteDao.getInstance(getApplicationContext());
        mListView = (ListView)findViewById(R.id.list_todo);
        mAddButton = (FloatingActionButton)findViewById(R.id.button_add);

        mDbHelper = new FeedReaderDBHelper(getApplicationContext());
        list = dao.retreieveAll();
        Collections.sort(list);
        arrayAdapter = new TodoAdapter(this.getApplicationContext(), list);
        mListView.setAdapter(arrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openEditActivity((TodoItem) mListView.getItemAtPosition(i));
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.DeleteDialogStyle);
                TodoItem item = (TodoItem)mListView.getItemAtPosition(i);
                final int position = i;
                builder.setMessage("Are you sure you want to delete this item?");
                builder.setTitle("To-Do Item");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int pos) {
                        list.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
                return true;
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditActivity(null);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        list = dao.retreieveAll();
        Collections.sort(list);
        arrayAdapter = new TodoAdapter(this.getApplicationContext(), list);
        mListView.setAdapter(arrayAdapter);
    }

    /**
     * Opens EditItemActivity class, if TodoItem item is null
     * then we open up a blank activity to edit
     * @param item
     */
    private void openEditActivity(TodoItem item) {
        Intent itemInfo = new Intent(this, EditItemActivity.class);
        itemInfo.putExtra("TodoItem", item);
        startActivityForResult(itemInfo, Activity.RESULT_OK);
    }

    public class TodoAdapter extends ArrayAdapter<TodoItem> {
        public TodoAdapter(Context context, ArrayList<TodoItem> list) {
            super(context, 0, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final TodoItem item = getItem(position);

            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_list_item, parent, false);
            }

            TextView title = (TextView) convertView.findViewById(R.id.textview_list_title);
            TextView priority = (TextView) convertView.findViewById(R.id.textview_list_priority);

            title.setText(item.getTitle());
            priority.setText(item.getLevel());
            switch(item.getLevelIndex()) {
                case 0:
                    priority.setTextColor(Color.GREEN);
                    break;
                case 1:
                    priority.setTextColor(Color.BLUE);
                    break;
                case 2:
                    priority.setTextColor(Color.RED);
                    break;
                default:
                    priority.setTextColor(Color.GREEN);
                    break;
            }
            return convertView;
        }
    }
}
