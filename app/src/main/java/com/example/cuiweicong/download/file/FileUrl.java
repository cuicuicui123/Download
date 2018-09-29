package com.example.cuiweicong.download.file;

import java.util.ArrayList;
import java.util.List;

public class FileUrl {
    public static List<String> picList = new ArrayList<>();

    static {
        for (int i = 1; i < 20; i++) {
            picList.add("http://d.5857.com/fjkp_170213/00" + i + ".jpg");
        }
    }
}
