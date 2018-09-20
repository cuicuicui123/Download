package com.example.cuiweicong.download;

import java.util.Locale;

public class DownloadProgressListener implements ProgressListener {
    private boolean firstUpdate = true;
    @Override
    public void update(long bytesRead, long contentLength, boolean done, DownloadRequest downloadRequest) {
        if (done) {
            LogUtils.logI("complete");
            if (downloadRequest != null) {
                downloadRequest.setDone(done);
            }
        } else {
            if (firstUpdate) {
                firstUpdate = false;
                if (contentLength == 1) {
                    LogUtils.logI("content-length: unknown");
                } else {
                    LogUtils.logI("content-length: " + contentLength);
                    if (downloadRequest != null) {
                        downloadRequest.setContentLength(contentLength);
                    }
                }
            }
            LogUtils.logI(String.valueOf(bytesRead));
            if (contentLength != 1) {
                int percent = (int) ((100 * bytesRead) / contentLength);
                if (downloadRequest != null) {
                    downloadRequest.setDownloadPercent(percent);
                    downloadRequest.setDownLength(bytesRead);
                }
                LogUtils.logI(String.format(Locale.CHINA, "%d%% done\n", percent));
            }
        }
    }
}
