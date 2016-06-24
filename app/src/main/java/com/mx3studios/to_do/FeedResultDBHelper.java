package com.mx3studios.to_do;

import android.content.ContentValues;

/**
 * Created by mario on 6/23/2016.
 */
public class FeedResultDBHelper {

    public static ContentValues toTableRow(TodoItem result) {
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, result.getTitle());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESC, result.getDescription());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_STATUS, result.getStatus());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_COMPLETION_DATE, result.getCompletionDate());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PRIORITY_LEVEL, result.getLevel());
        return values;
    }
}
