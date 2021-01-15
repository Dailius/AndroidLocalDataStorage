package com.dailiusprograming.localdatastorage.event;

import com.dailiusprograming.localdatastorage.data.DataItem;

public class DataItemEvent {
    private DataItem dataItem;

    public DataItemEvent(DataItem dataItem) {
        this.dataItem = dataItem;
    }

    public DataItem getDataItem() {
        return dataItem;
    }

    public void setDataItem(DataItem dataItem) {
        this.dataItem = dataItem;
    }

}
