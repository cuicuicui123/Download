package com.example.cuiweicong.download.download;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class DownloadService extends Service {
    private static final String EXTRA_URL = "url";

    public static void launch(Context context, String url){
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(EXTRA_URL, url);
        context.startService(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String url = intent.getStringExtra(EXTRA_URL);
        DownloadManager.getInstance().startDownload(url);
        return START_STICKY;
    }

}
