package com.example.cuiweicong.download;

import java.util.Locale;

public class DownloadProgressListener implements ProgressListener {
    private boolean firstUpdate = true;
    @Override
    public void update(long bytesRead, long contentLength, boolean done) {
        if (done) {
            LogUtils.logI("complete");
        } else {
            if (firstUpdate) {
                firstUpdate = false;
                if (contentLength == 1) {
                    LogUtils.logI("content-length: unknown");
                } else {
                    LogUtils.logI("content-length: " + contentLength);
                }
            }
            LogUtils.logI(String.valueOf(bytesRead));
            if (contentLength != 1) {
                LogUtils.logI(String.format(Locale.CHINA, "%d%% done\n", (100 * bytesRead) / contentLength));
            }
        }
    }
}
