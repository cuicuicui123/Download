package com.example.cuiweicong.download;

import android.util.Log;

public class LogUtils {
    public static final String TAG_NAME = "downloadX";

    public static void logI(String msg){
        Log.i(TAG_NAME, msg);
    }
    public static void logE(String msg){
        Log.e(TAG_NAME, msg);
    }
}
