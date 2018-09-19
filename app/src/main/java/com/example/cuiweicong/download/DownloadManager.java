package com.example.cuiweicong.download;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadManager {
    private ExecutorService exec = Executors.newFixedThreadPool(10);
    private List<DownloadRequest> downloadRequests;

    private DownloadManager(){
        downloadRequests = Collections.synchronizedList(new ArrayList<DownloadRequest>());
    }

    private static class SingletonHolder {
        private volatile static DownloadManager instance = new DownloadManager();
    }

    public static DownloadManager getInstance() {
        return SingletonHolder.instance;
    }

    public void downLoad(final String url){
        DownloadRequest downloadRequest = new DownloadRequest.Builder()
                .setUrl(url)
                .build();
        downloadRequests.add(downloadRequest);
        exec.execute(downloadRequest);
    }

    public boolean removeDownloadRequest(DownloadRequest downloadRequest) {
        return downloadRequests.remove(downloadRequest);
    }

}
