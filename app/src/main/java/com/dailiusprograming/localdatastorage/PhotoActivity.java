package com.dailiusprograming.localdatastorage;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dailiusprograming.localdatastorage.data.DataItem;
import com.dailiusprograming.localdatastorage.data.SampleData;
import com.dailiusprograming.localdatastorage.databinding.Binding_PhotoActivity;
import com.dailiusprograming.localdatastorage.event.DataItemEvent;
import com.dailiusprograming.localdatastorage.sqlite.SQLiteDataSource;
import com.dailiusprograming.localdatastorage.sqliteroom.AppDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PhotoActivity extends AppCompatActivity {

    TextView txtCategory, txtPhotographer, txtPhotoName;
    ImageView imageView;
    DataItem item;
    String imageFile;
    InputStream inputStream = null;
    SQLiteDataSource mSQLiteDataSource;
    List<DataItem> dataItemsList = SampleData.dataItemList;
    List<DataItem> dataJsonItems, dataSQLiteItems;
    DataItem dataSQLRoomItem;
    private int dataType;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Binding_PhotoActivity mBinding_PhotoActivity
                = DataBindingUtil.setContentView(this, R.layout.activity_photo);

        imageView = mBinding_PhotoActivity.ivwPhoto;
        txtCategory = mBinding_PhotoActivity.txtCategoryName;
        txtPhotographer = mBinding_PhotoActivity.txtPhotographer;
        txtPhotoName = mBinding_PhotoActivity.txtPhotoName;

        SharedPreferences prefs =
                getSharedPreferences(PhotosListActivity.APP_PREFS, MODE_PRIVATE);
        dataType = prefs.getInt(PhotosListActivity.dataType, 0);

        switch (dataType) {
            case DataEnumType.SIMPLE:
                item = getSortID();
                dataItemHandler(item);
                return;
            case DataEnumType.JSON:
            case DataEnumType.SQLITE:
                item = parcelableIntentValue();
                dataItemHandler(item);
                return;
            case DataEnumType.SQLITEROOM:
                long itemId = getItemID();
                appDatabase = AppDatabase.getInstance(this);
                Executor executor = Executors.newSingleThreadExecutor();

                executor.execute(() -> {
                    int itemCount = appDatabase.dataItemDao().countItems();
                    if (itemCount != 0) {
                        DataItem dataSQLRoomItem = appDatabase.dataItemDao().findById(itemId);
                        EventBus.getDefault().post(new DataItemEvent(dataSQLRoomItem));
                    }
                });
                EventBus.getDefault().register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DataItemEvent event) {
        dataSQLRoomItem = event.getDataItem();
        dataItemHandler(dataSQLRoomItem);
    }

    private void dataItemHandler(DataItem dataItem) {
        txtCategory.setText(dataItem.getCategory());
        txtPhotographer.setText(dataItem.getPhotographer());
        txtPhotoName.setText(dataItem.getPhotoName());

        imageFile = dataItem.getImage();
        try {
            inputStream = getAssets().open(imageFile);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DataItem parcelableIntentValue() {
        return getIntent().getExtras().getParcelable(RecyclerViewAdapter.ITEM_KEY);
    }

    private Long getItemID() {
        return getIntent().getExtras().getLong(RecyclerViewAdapter.ITEM_KEY);
    }

    private DataItem getSortID() {
        int sortID = getIntent().getExtras().getInt(RecyclerViewAdapter.ITEM_KEY);
        return SampleData.dataItemMap.get(sortID);
    }

    @Override
    protected void onDestroy() {
        if (dataType == DataEnumType.SQLITEROOM) {
            AppDatabase.destroyInstance();
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        }
        super.onDestroy();
    }
}