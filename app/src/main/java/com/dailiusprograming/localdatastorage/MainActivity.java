package com.dailiusprograming.localdatastorage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dailiusprograming.localdatastorage.databinding.Binding_MainActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Binding_MainActivity mBinding_MainActivity
                = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Button btnSampleData, btnJsonData, btnSQLiteData, btnSQLiteRoom;

        btnSampleData = mBinding_MainActivity.btnSampleData;
        btnJsonData = mBinding_MainActivity.btnJsonData;
        btnSQLiteData = mBinding_MainActivity.btnSQLiteData;
        btnSQLiteRoom = mBinding_MainActivity.btnSQLiteRoom;

        btnSampleData.setOnClickListener(v -> handler(DataEnumType.SIMPLE));

        btnJsonData.setOnClickListener(v -> handler(DataEnumType.JSON));

        btnSQLiteData.setOnClickListener(v -> handler(DataEnumType.SQLITE));

        btnSQLiteRoom.setOnClickListener(v -> handler(DataEnumType.SQLITEROOM));
    }

    private void handler(int dataEnumType) {
        SharedPreferences.Editor editor =
                getSharedPreferences(PhotosListActivity.APP_PREFS, MODE_PRIVATE).edit();
        editor.putInt(PhotosListActivity.dataType, dataEnumType);
        editor.apply();

        Intent openDataActivity = new Intent(MainActivity.this, PhotosListActivity.class);
        openDataActivity.putExtra(PhotosListActivity.dataType, dataEnumType);
        startActivity(openDataActivity);
    }
}
