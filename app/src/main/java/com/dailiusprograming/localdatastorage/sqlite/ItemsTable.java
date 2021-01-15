package com.dailiusprograming.localdatastorage.sqlite;

public class ItemsTable {
    public static final String TABLE_ITEMS = "items";
    public static final String COLUMN_ID = "itemId";
    public static final String COLUMN_PHOTOGRAPHER = "photographer";
    public static final String COLUMN_PHOTONAME = "photoName";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_POSITION = "sortPosition";
    public static final String COLUMN_IMAGE = "image";

    public static final String[] ALL_COLUMNS =
            {COLUMN_ID, COLUMN_PHOTOGRAPHER, COLUMN_PHOTONAME, COLUMN_CATEGORY,
                    COLUMN_POSITION, COLUMN_IMAGE};

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_ITEMS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
//                    COLUMN_ID + " TEXT PRIMARY KEY," +
                    COLUMN_PHOTOGRAPHER + " TEXT," +
                    COLUMN_PHOTONAME + " TEXT," +
                    COLUMN_CATEGORY + " TEXT," +
                    COLUMN_POSITION + " INTEGER," +
                    COLUMN_IMAGE + " TEXT" + ");";

    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_ITEMS;
}
