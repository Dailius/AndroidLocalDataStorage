package com.dailiusprograming.localdatastorage.sqliteroom;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.dailiusprograming.localdatastorage.data.DataItem;

import java.util.List;

@Dao
public interface DataItemDao {
    @Insert
    void insertAll(List<DataItem> items);

    @Insert
    void insertAll(DataItem... items);

    @Query("SELECT COUNT(*) from DataItem")
    int countItems();

    @Query("SELECT * FROM DataItem ORDER BY photoName ")
    List<DataItem> getAll();

    @Query("SELECT * FROM DataItem WHERE category IN (:category)ORDER BY photoName ")
    List<DataItem> getCategory(String[] category);

    @Query("SELECT * FROM DataItem WHERE itemId = :itemId")
    DataItem findById(Long itemId);
//    List<DataItem> findById(Long itemId);


}
