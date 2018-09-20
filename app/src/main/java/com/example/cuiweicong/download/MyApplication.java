package com.example.cuiweicong.download;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    public static Application context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext(){
        return context;
    }
}
