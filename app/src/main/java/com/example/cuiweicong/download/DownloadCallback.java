package com.example.cuiweicong.download;

public interface DownloadCallback {
    void beforeDownload();
    void onDownloading();
    void onPublishProgress(int length, int totalLength);
    void downloadComplete();
    void downloadFail();
}
