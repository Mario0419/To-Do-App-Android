public class TodoItemSQLiteDao implements TodoItemDao {

    private FeedReaderDBHelper dbManager;
    private SQLiteDatabase db;

    private static TodoItemSQLiteDao instance;

    public static TodoItemSQLiteDao getInstance(Context c) {

        if(instance == null) {

            instance = new TodoItemSQLiteDao(c);
        }

        return instance;
    }

    public TodoItemSQLiteDao(Context context) {

        dbManager = new FeedReaderDBHelper(context);
        db = dbManager.getWriteableDatabase();
    }

    public TodoItem retreieveAll() {

        ArrayList<TodoItem> results = new ArrayList<>();

        Cursor cursor = db.query(FeedReaderContract.FeedEntry.TABLE_NAME,
            FeedReaderContract.COLUMNS,
            null, null, null, null, null);

        while(cursor != null && cursor.moveToNext()) {
            resultList.add(new TodoItem(cursor));
        }

        return results;
    }

    public boolean delete(int id) {

        int rows = db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, 
            "entryid = ? ", 
            new String[]{String.valueOf(id)});

        return rows != 0;
    }

    public TodoItem save(TodoItem item) {

        long row = db.replace(FeedReaderContract.FeedEntry.TABLE_NAME,
                FeedReaderContract.FeedEntry.COLUMN_NAME_NULLABLE, 
                toTableRow(item));

        item.setId(row);

        return item;
    }

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