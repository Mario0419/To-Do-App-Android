package com.mx3studios.to_do;

import android.provider.BaseColumns;

/**
* Created by mario on 6/22/2016.
*/
public final class FeedReaderContract {

    public FeedReaderContract() {}

    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "TODO";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESC = "desc";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_COMPLETION_DATE = "date";
        public static final String COLUMN_NAME_PRIORITY_LEVEL = "priority";
        public static final String COLUMN_NAME_NULLABLE = "NULL";
    }

    private static final String TEXT_TYPE = " TEXT";

    private static final String COMMA_SEP = " , ";

    public static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
            FeedEntry._ID + " INTEGER PRIMARY KEY," +
            FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
            FeedEntry.COLUMN_NAME_DESC + TEXT_TYPE + COMMA_SEP +
            FeedEntry.COLUMN_NAME_STATUS + TEXT_TYPE + COMMA_SEP +
            FeedEntry.COLUMN_NAME_COMPLETION_DATE + TEXT_TYPE + COMMA_SEP +
            FeedEntry.COLUMN_NAME_PRIORITY_LEVEL + TEXT_TYPE +
        " )";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    public static final String[] COLUMNS = {
        FeedEntry.COLUMN_NAME_TITLE,
        FeedEntry.COLUMN_NAME_DESC,
        FeedEntry.COLUMN_NAME_STATUS,
        FeedEntry.COLUMN_NAME_COMPLETION_DATE,
        FeedEntry.COLUMN_NAME_PRIORITY_LEVEL,
        FeedEntry.COLUMN_NAME_ENTRY_ID
    };
}
