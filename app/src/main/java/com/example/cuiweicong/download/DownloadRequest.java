package com.example.cuiweicong.download;

import okhttp3.Call;

public class DownloadRequest implements Runnable {
    private Builder builder;
    private Call call;
    private long contentLength;
    private volatile int downloadPercent;
    private volatile boolean done;

    public DownloadRequest(Builder builder) {
        this.builder = builder;
    }

    @Override
    public void run() {
        HttpManager.getInstance().request(builder.url, this);
    }

    public void cancel() {
        call.cancel();
    }

    public void setCall(Call call) {
        this.call = call;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public int getDownloadPercent() {
        return downloadPercent;
    }

    public void setDownloadPercent(int downloadPercent) {
        this.downloadPercent = downloadPercent;
    }

    public String getUrl() {
        return builder.url;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }

    public static class Builder {
        private String url;

        public String getUrl() {
            return url;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public DownloadRequest build() {
            return new DownloadRequest(this);
        }
    }

}
