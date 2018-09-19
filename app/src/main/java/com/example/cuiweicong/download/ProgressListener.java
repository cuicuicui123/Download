package com.example.cuiweicong.download;

public interface ProgressListener {
    void update(long bytesRead, long contentLength, boolean done, DownloadRequest downloadRequest);
}
