package com.dailiusprograming.localdatastorage.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.dailiusprograming.localdatastorage.data.DataItem;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDataSource {

    private final Context mContext;
    SQLiteOpenHelper mDbHelper;
    private SQLiteDatabase mDatabase;

    public SQLiteDataSource(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(mContext);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void open() {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }


    public DataItem createItem(DataItem item) {
        ContentValues values = item.toValues();
        mDatabase.insert(ItemsTable.TABLE_ITEMS, null, values);
        return item;
    }

    public long getDataItemsCount() {
        return DatabaseUtils.queryNumEntries(mDatabase, ItemsTable.TABLE_ITEMS);
    }

    public void seedDatabase(List<DataItem> dataItemList) {
        long numItems = getDataItemsCount();
        if (numItems == 0) {
            try {
                mDatabase.beginTransaction();
                for (DataItem item : dataItemList) {
                    try {
                        createItem(item);
                    } catch (SQLiteException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("DataSource", "transaction executed");
                Toast.makeText(mContext, "Sample Data inserted into SQLite database.", Toast.LENGTH_SHORT).show();
                mDatabase.setTransactionSuccessful();
                mDatabase.endTransaction();
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("DataSource", "seedDatabase: " + e.getMessage());
                mDatabase.endTransaction();
            }
        }
    }

    public List<DataItem> getAllItems(String category) {
        List<DataItem> dataItems = new ArrayList<>();
        String orderBy = ItemsTable.COLUMN_PHOTOGRAPHER + " , " + ItemsTable.COLUMN_PHOTONAME;

        Cursor cursor;
        if (category == null) {
            cursor = mDatabase.query(ItemsTable.TABLE_ITEMS, ItemsTable.ALL_COLUMNS,
                    null, null, null, null, orderBy);
        } else {
            String[] categories = {category};
            cursor = mDatabase.query(ItemsTable.TABLE_ITEMS, ItemsTable.ALL_COLUMNS,
                    ItemsTable.COLUMN_CATEGORY + "=?", categories, null, null, ItemsTable.COLUMN_PHOTOGRAPHER);
        }

        while (cursor.moveToNext()) {
            DataItem item = new DataItem();
            item.setItemId(cursor.getLong(
                    cursor.getColumnIndex(ItemsTable.COLUMN_ID)));
            item.setPhotographer(cursor.getString(
                    cursor.getColumnIndex(ItemsTable.COLUMN_PHOTOGRAPHER)));
            item.setPhotoName(cursor.getString(
                    cursor.getColumnIndex(ItemsTable.COLUMN_PHOTONAME)));
            item.setCategory(cursor.getString(
                    cursor.getColumnIndex(ItemsTable.COLUMN_CATEGORY)));
            item.setSortPosition(cursor.getInt(
                    cursor.getColumnIndex(ItemsTable.COLUMN_POSITION)));
            item.setImage(cursor.getString(
                    cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE)));
            dataItems.add(item);
        }
        cursor.close();
        return dataItems;
    }

}
