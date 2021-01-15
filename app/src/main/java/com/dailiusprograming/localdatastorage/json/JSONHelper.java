package com.dailiusprograming.localdatastorage.json;

import android.content.Context;

import com.dailiusprograming.localdatastorage.data.DataItem;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class JSONHelper {

    public static final String FILE_NAME = "jsondata.json";

    public static boolean exportToJSON(Context context, List<DataItem> dataItemList) {

        File file = new File(context.getFilesDir(), FILE_NAME);

        if (!file.exists()) {
            DataItems jsonData = new DataItems();
            jsonData.setDataItems(dataItemList);

            Gson gson = new Gson();
            String jsonString = gson.toJson(jsonData);

            FileOutputStream fileOutputStream = null;

            try {
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(jsonString.getBytes());
//                Toast.makeText(context, "Data successfully exported to Json database.",
//                        Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
//                Toast.makeText(context, "Export to Json database failed.",
//                        Toast.LENGTH_SHORT).show();
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return file.exists();
    }

    public static List<DataItem> importFromJSON(Context context) {

        FileReader reader = null;

        try {
            File file = new File(context.getFilesDir(), FILE_NAME);
            reader = new FileReader(file);
            Gson gson = new Gson();
            DataItems dataItems = gson.fromJson(reader, DataItems.class);

            return dataItems.getDataItems();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }


    static class DataItems {
        List<DataItem> dataItems;

        public List<DataItem> getDataItems() {
            return dataItems;
        }

        public void setDataItems(List<DataItem> dataItems) {
            this.dataItems = dataItems;
        }
    }


}
