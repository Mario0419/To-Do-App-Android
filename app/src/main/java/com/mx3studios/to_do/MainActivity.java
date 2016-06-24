package com.mx3studios.to_do;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private FloatingActionButton mAddButton;
    private FeedReaderDBHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView)findViewById(R.id.list_todo);
        mAddButton = (FloatingActionButton)findViewById(R.id.button_add);

        mDbHelper = new FeedReaderDBHelper(getApplicationContext());

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openEditActivity((TodoItem) mListView.getItemAtPosition(i));
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditActivity(null);
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && data != null) {
            TodoItem item = (TodoItem)data.getExtras().get("saveTodo");
            mDbHelper.addTodoItem(item);
        } else if (resultCode == RESULT_CANCELED) {
            TodoItem item = (TodoItem)data.getExtras().get("saveTodo");
            mDbHelper.deleteTodoItem(item.getId());
        }
    }
}
