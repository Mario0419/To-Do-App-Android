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
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    private FloatingActionButton mAddButton;
    private FeedReaderDBHelper mDbHelper;

    private ArrayList<TodoItem> list = null;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private TodoItemDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = TodoItemSQLiteDao.getInstance(getApplicationContext());
        mAddButton = (FloatingActionButton)findViewById(R.id.button_add);

        mRecyclerView = (RecyclerView)findViewById(R.id.list_todo);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mDbHelper = new FeedReaderDBHelper(getApplicationContext());
        list = dao.retreieveAll();
        Collections.sort(list);
        mAdapter = new TodoRecyclerAdapter(list);
        mRecyclerView.setAdapter(mAdapter);

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
        mAdapter = new TodoRecyclerAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
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


    public class TodoRecyclerAdapter extends RecyclerView.Adapter<TodoRecyclerAdapter.ViewHolder> {
        private ArrayList<TodoItem> mDataset;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public View mView;
            public ViewHolder(View v) {
                super(v);
                mView = v;
            }
        }

        public TodoRecyclerAdapter(ArrayList<TodoItem> myDataset) {
            mDataset = myDataset;
        }

        @Override
        public TodoRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_item, parent, false);
            // set the view's size, margins, paddings and layout parameters

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position){
            CardView cardView = (CardView)holder.mView.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openEditActivity(mDataset.get(position));
                }
            });
            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.DeleteDialogStyle);
                    final TodoItem item =  mDataset.get(position);
                    builder.setMessage("Are you sure you want to delete this item?");
                    builder.setTitle("To-Do Item");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int pos) {
                            mDataset.remove(position);
                            mAdapter.notifyDataSetChanged();
                            dao.delete(item.getId());
                        }
                    });
                    builder.setNegativeButton("No", null);
                    builder.show();
                    return true;
                }
            });
            TextView title = (TextView) holder.mView.findViewById(R.id.textview_list_title);
            title.setText(mDataset.get(position).getTitle());
            switch(mDataset.get(position).getLevelIndex()) {
                case 0:
                    title.setBackgroundResource(R.color.greenMaterial);
                    break;
                case 1:
                    title.setBackgroundResource(R.color.indigoMaterial);
                    break;
                case 2:
                    title.setBackgroundResource(R.color.redMaterial);
                    break;
                default:
                    title.setBackgroundResource(R.color.greenMaterial);
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }


}


