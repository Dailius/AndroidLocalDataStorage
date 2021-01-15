package com.dailiusprograming.localdatastorage.sqliteroom;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.dailiusprograming.localdatastorage.data.DataItem;
import com.dailiusprograming.localdatastorage.data.SampleData;
import com.dailiusprograming.localdatastorage.event.DataItemsEvent;
import com.dailiusprograming.localdatastorage.json.JSONHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HandlerSQLiteRoom extends AppCompatActivity {
    private final Executor executor = Executors.newSingleThreadExecutor();
    List<DataItem> dataItemsList = SampleData.dataItemList;
    List<DataItem> dataSQLRoomItems;
    Context mContext;
    private AppDatabase appDatabase;

    public HandlerSQLiteRoom(Context mContext) {
        this.mContext = mContext;
    }

    public void runSQLiteRoomDataBase() {
        appDatabase = AppDatabase.getInstance(mContext);
        executor.execute(() -> {
            int itemCount = appDatabase.dataItemDao().countItems();
            if (itemCount == 0) {
                boolean resultForSQLRoom =
                        JSONHelper.exportToJSON(mContext, dataItemsList);
                if (resultForSQLRoom) {
                    List<DataItem> itemDataList =
                            JSONHelper.importFromJSON(mContext);
                    appDatabase.dataItemDao().insertAll(itemDataList);

//                    Toast.makeText
//                            (HandlerSQLiteRoom.this, getString(R.string.inserted_sqlite_room), Toast.LENGTH_SHORT)
//                            .show();
                }
            }
        });
    }

    public void displaySQLiteRoomDataItems_All() {
        executor.execute(() -> {
            dataSQLRoomItems = appDatabase.dataItemDao().getAll();
            EventBus.getDefault().post(new DataItemsEvent(dataSQLRoomItems));
        });
    }

    public void displaySQLiteRoomDataItems_Category(String[] category) {
        executor.execute(() -> {
            dataSQLRoomItems = appDatabase.dataItemDao().getCategory(category);
            EventBus.getDefault().post(new DataItemsEvent(dataSQLRoomItems));
        });
    }
}
