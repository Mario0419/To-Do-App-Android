package com.mx3studios.to_do;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mx3studios.to_do.FeedReaderContract;

import java.util.ArrayList;

/**
 * Created by mario on 6/22/2016.
 */
public class FeedReaderDBHelper extends SQLiteOpenHelper{
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";
    public static final int SQLITE_ERROR = -1;

    public FeedReaderDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FeedReaderContract.SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(FeedReaderContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean addTodoItem(TodoItem result) {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues row = FeedResultDBHelper.toTableRow(result);
        return db.replace(FeedReaderContract.FeedEntry.TABLE_NAME,
                FeedReaderContract.FeedEntry.COLUMN_NAME_NULLABLE,
                row) != SQLITE_ERROR;
    }

    public ArrayList<TodoItem> getAllTodoItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<TodoItem> resultList = new ArrayList<>();
        Cursor cursor = db.query(FeedReaderContract.FeedEntry.TABLE_NAME,
                FeedReaderContract.COLUMNS,
                null, null, null, null, null);
        if (cursor == null) {
            return resultList;
        }
        while(cursor.moveToNext()) {
            resultList.add(new TodoItem(cursor));
        }
        return resultList;
    }

    public void deleteTodoItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, "entryid = ? ", new String[]{String.valueOf(id)});
    }
}
