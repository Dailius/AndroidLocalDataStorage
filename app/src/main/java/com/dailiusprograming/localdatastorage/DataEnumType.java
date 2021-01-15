package com.dailiusprograming.localdatastorage;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DataEnumType {

    public static final int SIMPLE = 1;
    public static final int JSON = 2;
    public static final int SQLITE = 3;
    public static final int SQLITEROOM = 4;
    private int type;

    @DataType
    public int getType() {
        return type;
    }

    public void setType(@DataType int type) {
        this.type = type;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SIMPLE, JSON, SQLITE, SQLITEROOM})
    @interface DataType {
    }
}
