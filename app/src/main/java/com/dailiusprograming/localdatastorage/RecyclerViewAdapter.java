package com.dailiusprograming.localdatastorage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dailiusprograming.localdatastorage.data.DataItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    public static final String ITEM_KEY = "item_key";
    private final List<DataItem> mItems;
    private final Context mContext;
    int layoutId;
    Intent intent;
    DataEnumType dataEnumType;
    String description;
    int mType;

    public RecyclerViewAdapter(List<DataItem> mItems, Context mContext, int mType) {
        this.mItems = mItems;
        this.mContext = mContext;
        this.mType = mType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        dataEnumType = new DataEnumType();
        dataEnumType.setType(mType);

        SharedPreferences settings =
                PreferenceManager.getDefaultSharedPreferences(mContext);

        boolean grid = settings.getBoolean(
                mContext.getString(R.string.swch_pref_grid_display), false);
        boolean layout = settings.getBoolean(
                mContext.getString(R.string.swch_pref_card_view), false);

        if (grid && layout) {
            layoutId = R.layout.item_grid_cardview;
        } else if (grid) {
            layoutId = R.layout.item_grid;
        } else if (layout) {
            layoutId = R.layout.item_list_cardview;
        } else {
            layoutId = R.layout.item_list;
        }

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(layoutId, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DataItem item = mItems.get(position);

        try {
            if (layoutId == R.layout.item_list) {
                description = item.getPhotoName() + " \nAuthor: " + item.getPhotographer();
                holder.tvwPhotoName.setText(description);
            } else if (layoutId == R.layout.item_list_cardview || layoutId == R.layout.item_grid) {
                holder.tvwPhotoName.setText(item.getPhotoName());
                holder.tvwPhotographer.setText(item.getPhotographer());
            } else if (layoutId == R.layout.item_grid_cardview) {
                holder.tvwPhotographer.setText(item.getPhotographer());
            }

            String imageFile = item.getImage();
            InputStream inputStream = mContext.getAssets().open(imageFile);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            holder.ivwPhoto.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.mView.setOnClickListener(v -> {

            switch (dataEnumType.getType()) {
                case DataEnumType.SIMPLE:
                    assignSimpleDataToPhotoActivity(item, false);
                    return;
                case DataEnumType.JSON:
                case DataEnumType.SQLITE:
                    assignSimpleDataToPhotoActivity(item, true);
                    return;
                case DataEnumType.SQLITEROOM:
                    Intent intent = new Intent(mContext, PhotoActivity.class);
                    intent.putExtra(ITEM_KEY, item.getItemId());
                    mContext.startActivity(intent);
            }
        });
    }

    private void assignSimpleDataToPhotoActivity(DataItem item, Boolean parcelableItem) {
        boolean transferParcelableItem = parcelableItem;

        if (transferParcelableItem) {
            //Transfer parcelable Item.
            intent = transferParcelableItem(item);
        } else {
            //Transfer unique Item ID.
            intent = transferSortID(item);
        }
        mContext.startActivity(intent);
    }

    private Intent transferParcelableItem(DataItem item) {
        Intent intent = new Intent(mContext, PhotoActivity.class);
        intent.putExtra(ITEM_KEY, item);
        return intent;
    }

    private Intent transferSortID(DataItem item) {
        int sortID = item.getSortPosition();
        Intent intent = new Intent(mContext, PhotoActivity.class);
        intent.putExtra(ITEM_KEY, sortID);
        return intent;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvwPhotoName;
        public TextView tvwPhotographer;
        public ImageView ivwPhoto;
        public View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvwPhotographer = itemView.findViewById(R.id.txtPhotographer);
            tvwPhotoName = itemView.findViewById(R.id.txtPhotoName);
            ivwPhoto = itemView.findViewById(R.id.ivwPhoto);
            mView = itemView;

        }
    }
}
