package com.dailiusprograming.localdatastorage.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleData {
    public static List<DataItem> dataItemList;
    public static Map<Integer, DataItem> dataItemMap;

    static {
        dataItemList = new ArrayList<>();
        dataItemMap = new HashMap<>();

        addItem(new DataItem(null, "Luis del Río",
                "Person Waqlking Between Green Forest Trees", "Nature",
                1, "pexels_luis_del_río_15286.jpg"));
        addItem(new DataItem(null, "Paul Ijsendoorn",
                "Antelope Canyon", "Landscape",
                2, "pexels_paul_ijsendoorn_33041.jpg"));
        addItem(new DataItem(null, "Kasuma",
                "Mountains Covered With Snow", "Landscape",
                3, "pexels_kasuma_1785493.jpg"));
        addItem(new DataItem(null, "Jeremy Bishop",
                "Dolphins in Water", "Nature",
                4, "pexels_jeremy_bishop_2922672.jpg"));
        addItem(new DataItem(null, "Trace Hudson",
                "Snow Covered Mountain", "Landscape",
                5, "pexels_trace_hudson_2365457.jpg"));
        addItem(new DataItem(null, "Valdemaras D.",
                "Close-Up Photo of Small Jellyfish", "Nature",
                6, "pexels_valdemaras_d_1687678.jpg"));
        addItem(new DataItem(null, "Max Ravier",
                "Areal Photo of Sea Wave", "Landscape",
                7, "pexel_max_ravier_3331094.jpg"));
        addItem(new DataItem(null, "Clive Kim",
                "Volcano Erupting at Night Under Starry Sky", "Landscape",
                8, "pexels_clive_kim_4220967.jpg"));
        addItem(new DataItem(null, "Anne McCarthy",
                "arches national park", "Landscape",
                9, "pexels_anne_mccarthy_3900437.jpg"));
        addItem(new DataItem(null, "Ruvim Miksanskiy",
                "Aerial Photography of Snow Covered Trees", "Landscape",
                10, "pexels_ruvim_miksanskiy_1438761.jpg"));
        addItem(new DataItem(null, "Pixabay",
                "Red Leaf Trees Near the Road", "Landscape",
                11, "pexels_pixabay_33109.jpg"));
        addItem(new DataItem(null, "Marting Damboldt",
                "Gray Bridge and Trees", "Landscape",
                12, "pexels_martin_damboldt_814499.jpg"));
        addItem(new DataItem(null, "James Wheeler",
                "Lake and Mountain", "Landscape",
                13, "pexels_james_wheeler_417074.jpg"));
        addItem(new DataItem(null, "Simon Migaj",
                "Person on a Bridge Near a Lake", "Landscape",
                14, "pexels_simon_migaj_747964.jpg"));
        addItem(new DataItem(null, "Bri Schneiter",
                "Calm Body of Lake Between Mountains", "Landscape",
                15, "pexels_bri_schneiter_346529.jpg"));
        addItem(new DataItem(null, "Aleksandar Pasaric",
                "View of Cityscape", "City",
                16, "pexels_aleksandar_pasaric_325185.jpg"));
        addItem(new DataItem(null, "Quang Nguyen Vinh",
                "2 People On The Boat", "Landscape",
                17, "pexels_quang_nguyen_vinh-2166711.jpg"));
        addItem(new DataItem(null, "Thanhhoa Tran",
                "View of Rice Terraces", "Landscape",
                18, "pexels_thanhhoa_tran_1447092.jpg"));
        addItem(new DataItem(null, "Kelly Lacy",
                "Road Between Trees", "Landscape",
                19, "pexels_kelly_lacy_2519392.jpg"));
        addItem(new DataItem(null, "Johannes Plenio",
                "Forest Trees at Daytime", "Nature",
                20, "pexels_johannes_plenio_1133491.jpg"));
        addItem(new DataItem(null, "Valiphotos",
                "Brown Leaf Trees on Forest", "Nature",
                21, "pexels_valiphotos_589816.jpg"));
        addItem(new DataItem(null, "Roberto Nickson",
                "White Clouds", "Sky",
                22, "pexels-roberto-nickson-2775196.jpg"));
        addItem(new DataItem(null, "Philippe Donn",
                "Stars", "Sky",
                23, "pexels-philippe-donn-1257860.jpg"));
        addItem(new DataItem(null, "Miguel Á. Padriñán",
                "White Clouds on Blue Sky", "Sky",
                24, "pexels-miguel-á-padriñán-19670.jpg"));
        addItem(new DataItem(null, "Adam Kontor",
                "Low-angle Photography Gray Sky", "Sky",
                25, "pexels-adam-kontor-325117.jpg"));
        addItem(new DataItem(null, "Pixabay",
                "Silhouette of Tree Near Body of Water during Golden Hour", "Sky",
                26, "pexels-pixabay-36717.jpg"));
        addItem(new DataItem(null, "Nuno Obey",
                "White Row Boat on Body of Water", "Sky",
                27, "pexels-nuno-obey-127160.jpg"));
        addItem(new DataItem(null, "Daria Shevtsova",
                "Black Steel Lamp Post", "City",
                28, "pexels-daria-shevtsova-1070945.jpg"));
        addItem(new DataItem(null, "Taryn Elliott",
                "Photo Of City During Daytime", "City",
                29, "pexels-taryn-elliott-3889856.jpg"));
        addItem(new DataItem(null, "Pixabay",
                "Skyline City", "City",
                30, "pexels-pixabay-366283.jpg"));
        addItem(new DataItem(null, "Daniel Frank",
                "Dog on Concrete Road", "City",
                31, "pexels-daniel-frank-688835.jpg"));
        addItem(new DataItem(null, "Daria Shevtsova",
                "White Concrete Building and Body of Water", "Beach",
                32, "pexels-daria-shevtsova-1831236.jpg"));
        addItem(new DataItem(null, "Quang Nguyen Vinh",
                "Two Brown Wooden Armchairs Beside Umbrella Near Seashore", "Beach",
                33, "pexels-quang-nguyen-vinh-3355732.jpg"));
        addItem(new DataItem(null, "Elle Hughes",
                "Group of People Carrying Surfboards", "Beach",
                34, "pexels-elle-hughes-1549196.jpg"));
        addItem(new DataItem(null, "Asad Photo Maldives",
                "Landscape Photography of Trees on Shoreline", "Beach",
                35, "pexels-asad-photo-maldives-457882.jpg"));
        addItem(new DataItem(null, "Pixabay",
                "Green Hill Near Body of Water", "Beach",
                36, "pexels-pixabay-462162.jpg"));
        addItem(new DataItem(null, "Ishan",
                "Aerial Photography of Island With Cottages Surrounded With Water", "Beach",
                37, "pexels-ishan-678725.jpg"));

    }

    private static void addItem(DataItem item) {
        dataItemList.add(item);
        dataItemMap.put(item.getSortPosition(), item);
    }


}
