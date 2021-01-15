package com.dailiusprograming.localdatastorage.data;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.dailiusprograming.localdatastorage.sqlite.ItemsTable;

@Entity

public class DataItem implements Parcelable {
    public static final Creator<DataItem> CREATOR = new Creator<DataItem>() {
        @Override
        public DataItem createFromParcel(Parcel in) {
            return new DataItem(in);
        }

        @Override
        public DataItem[] newArray(int size) {
            return new DataItem[size];
        }
    };
    @PrimaryKey(autoGenerate = true)
    private Long itemId;
    @ColumnInfo
    private String photographer;
    @ColumnInfo
    private String photoName;
    @ColumnInfo
    private String category;
    @ColumnInfo
    private int sortPosition;
    @ColumnInfo
    private String image;

    public DataItem() {
    }

    @Ignore
    public DataItem(Long itemId, String photographer, String photoName, String category, int sortPosition, String image) {

        if (itemId == null) {
            itemId = (long) sortPosition;
        }

        this.itemId = itemId;
        this.photographer = photographer;
        this.photoName = photoName;
        this.category = category;
        this.sortPosition = sortPosition;
        this.image = image;
    }

    protected DataItem(Parcel in) {
        itemId = in.readLong();
        photographer = in.readString();
        photoName = in.readString();
        category = in.readString();
        sortPosition = in.readInt();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(itemId);
        dest.writeString(photographer);
        dest.writeString(photoName);
        dest.writeString(category);
        dest.writeInt(sortPosition);
        dest.writeString(image);
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSortPosition() {
        return sortPosition;
    }

    public void setSortPosition(int sortPosition) {
        this.sortPosition = sortPosition;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ContentValues toValues() {
        // numeric value for the size of the object. 5 fields in this class because we
        // have 5 columns in the matching database table.
        ContentValues values = new ContentValues(5);

//        values.put(ItemsTable.COLUMN_ID, itemId); this field is disabled in order to generate primary key as source data base contains null value in this column
        values.put(ItemsTable.COLUMN_PHOTOGRAPHER, photographer);
        values.put(ItemsTable.COLUMN_PHOTONAME, photoName);
        values.put(ItemsTable.COLUMN_CATEGORY, category);
        values.put(ItemsTable.COLUMN_POSITION, sortPosition);
        values.put(ItemsTable.COLUMN_IMAGE, image);

        return values;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
