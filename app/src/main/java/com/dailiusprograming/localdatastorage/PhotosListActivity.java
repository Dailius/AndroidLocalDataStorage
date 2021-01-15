package com.dailiusprograming.localdatastorage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dailiusprograming.localdatastorage.data.DataItem;
import com.dailiusprograming.localdatastorage.data.SampleData;
import com.dailiusprograming.localdatastorage.event.DataItemsEvent;
import com.dailiusprograming.localdatastorage.json.JSONHelper;
import com.dailiusprograming.localdatastorage.prefs.PrefsActivity;
import com.dailiusprograming.localdatastorage.sqlite.SQLiteDataSource;
import com.dailiusprograming.localdatastorage.sqliteroom.AppDatabase;
import com.dailiusprograming.localdatastorage.sqliteroom.HandlerSQLiteRoom;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class PhotosListActivity extends AppCompatActivity {


    public static final String dataType = "data_type";
    public static final String APP_PREFS = "app_prefs";
    public static final String drawerCategory = "drawer_category";
    public static final String drawerCategory_All = "All";
    private final List<DataItem> dataItemsList = SampleData.dataItemList;
    public DataEnumType dataEnumType;
    List<DataItem> dataJsonItems, dataSQLiteItems, dataSQLRoomItems;
    private SQLiteDataSource mSQLiteDataSource;
    private DrawerLayout mDrawerLayout;
    private String[] mCategories;
    private ListView mDrawerList;
    private int type;
    private HandlerSQLiteRoom handlerSQLiteRoom;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        dataEnumType = new DataEnumType();
        getIntentExtrasAndSetDataEnumType();

        switch (dataEnumType.getType()) {
            case DataEnumType.SIMPLE:
                this.setTitle(R.string.from_sample_data);
                RecyclerViewHandler(dataItemsList);
                return;

            case DataEnumType.JSON:
                this.setTitle(R.string.from_json_data);
                boolean result = JSONHelper.exportToJSON(this, dataItemsList);
                if (result) {
                    dataJsonItems = JSONHelper.importFromJSON(this);
                }
                return;
            case DataEnumType.SQLITE:
                this.setTitle(R.string.from_sqlite_data);

                mSQLiteDataSource = new SQLiteDataSource(this);
                mSQLiteDataSource.open();
                mSQLiteDataSource.seedDatabase(dataItemsList);

                String category = SharedPreferences_GetCategory();
                SQLiteDataItems_DisplayHandler(category);

                return;
            case DataEnumType.SQLITEROOM:
                this.setTitle(R.string.from_sqlite_room);
                handlerSQLiteRoom = new HandlerSQLiteRoom(this);
                handlerSQLiteRoom.runSQLiteRoomDataBase();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventGetItemList(DataItemsEvent event) {
        dataSQLRoomItems = event.getItemList();
        RecyclerViewHandler(dataSQLRoomItems);
    }

    private void displaySQLiteDataItems(String category) {
        dataSQLiteItems = mSQLiteDataSource.getAllItems(category);
        RecyclerViewHandler(dataSQLiteItems);
    }

    private void getIntentExtrasAndSetDataEnumType() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            type = bundle.getInt(dataType);
        } else if (type == 0) {
            sharedPreferences = getSharedPreferences(APP_PREFS, MODE_PRIVATE);
            type = sharedPreferences.getInt(dataType, DataEnumType.SIMPLE);
        }
        dataEnumType.setType(type);
    }

    private void RecyclerViewHandler(List<DataItem> dataList) {

        RecyclerViewAdapter recyclerViewAdapter =
                new RecyclerViewAdapter(dataList, this, dataEnumType.getType());

        RecyclerView recyclerView = findViewById(R.id.rvwItems);

        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        boolean gridLayout = sharedPreferences.getBoolean(getString(R.string.swch_pref_grid_display), false);

        if (gridLayout) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void NavigationDrawer() {

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mCategories = getResources().getStringArray(R.array.categories);
        mDrawerList = findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, mCategories));

        mDrawerList.setOnItemClickListener((parent, view, position, id) -> {
            String category = mCategories[position];
            mDrawerLayout.closeDrawer(mDrawerList);

            if (dataEnumType.getType() == DataEnumType.SQLITE) {
                displaySQLiteDataItems(category);

            } else if (dataEnumType.getType() == DataEnumType.SQLITEROOM) {
                SQLiteRoomDataItems_DisplayHandler(category);
            }
            NavigationDrawer_SaveCategory(category);
        });
    }

    private void NavigationDrawer_SaveCategory(String category) {
        SharedPreferences.Editor prefEditor = getSharedPreferences(PhotosListActivity.APP_PREFS, MODE_PRIVATE).edit();
        prefEditor.putString(drawerCategory, category);
        prefEditor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        if (dataEnumType.getType() == DataEnumType.SQLITE || dataEnumType.getType() == DataEnumType.SQLITEROOM) {
            menu.findItem(R.id.action_all_items).setEnabled(true);
            menu.findItem(R.id.action_choose_category).setEnabled(true);

            NavigationDrawer();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, PrefsActivity.class);
            startActivity(settingsIntent);
        } else if (item.getItemId() == R.id.action_all_items) {

            if (dataEnumType.getType() == DataEnumType.SQLITE) {
                displaySQLiteDataItems(null);

            } else if (dataEnumType.getType() == DataEnumType.SQLITEROOM) {
                SQLiteRoomDataItems_DisplayHandler(drawerCategory_All);
            }

            NavigationDrawer_SaveCategory(drawerCategory_All);

        } else if (item.getItemId() == R.id.action_choose_category) {
            mDrawerLayout.openDrawer(mDrawerList);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (dataEnumType.getType()) {
            case DataEnumType.SIMPLE:
                RecyclerViewHandler(dataItemsList);
                return;
            case DataEnumType.JSON:
                RecyclerViewHandler(dataJsonItems);
                return;
            case DataEnumType.SQLITE:
                mSQLiteDataSource.open();
                RecyclerViewHandler(dataSQLiteItems);
                return;
            case DataEnumType.SQLITEROOM:
                String category = SharedPreferences_GetCategory();
                SQLiteRoomDataItems_DisplayHandler(category);
        }
    }

    private String SharedPreferences_GetCategory() {
        sharedPreferences = getSharedPreferences(APP_PREFS, MODE_PRIVATE);
        return sharedPreferences.getString(drawerCategory, drawerCategory_All);
    }

    private void SQLiteRoomDataItems_DisplayHandler(String category) {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        if (category.equals(drawerCategory_All)) {
            handlerSQLiteRoom.displaySQLiteRoomDataItems_All();
        } else {
            String[] aCategory = {category};
            handlerSQLiteRoom.displaySQLiteRoomDataItems_Category(aCategory);
        }
        EventBus.getDefault().register(this);
    }

    private void SQLiteDataItems_DisplayHandler(String category) {
        if (category.equals(drawerCategory_All)) {
            displaySQLiteDataItems(null);
        } else {
            displaySQLiteDataItems(category);
        }
    }

    @Override
    protected void onDestroy() {
        if (dataEnumType.getType() == DataEnumType.SQLITE) {
            mSQLiteDataSource.close();
        } else if (dataEnumType.getType() == DataEnumType.SQLITEROOM) {
            AppDatabase.destroyInstance();
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        }
        super.onDestroy();
    }
}